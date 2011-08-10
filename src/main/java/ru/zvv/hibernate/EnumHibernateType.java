package ru.zvv.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.hibernate.util.ReflectHelper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Класс позволяет замапить Enum'ы, которые реализуют {@link IEnumType}
 * В hbm-мапинге нужно прописать следующее:
 *
 * <typedef class="niias.hibernate.type.EnumHibernateType" name="SomeName">
 * <param name="enumClass">enym.class.name</param>
 * </typedef>
 * <class ...>
 * ...
 * <property name="property" type="SomeName"  column="column" />
 * ...
 * </class>
 *
 * @author Vitaly Zubchevskiy
 */
public class EnumHibernateType implements UserType, ParameterizedType, Serializable
{
    public static final String ENUM = "enumClass";

    private static final int[] SQL_TYPES = {Types.INTEGER};
    private Class<? extends IEnumType> _EnumClass;

    public int[] sqlTypes()
    {
        return SQL_TYPES;
    }

    public boolean equals(final Object x, final Object y)
    {
        if (x == null)
            return y == null;
        return x.equals(y);
    }

    public Object deepCopy(final Object value)
    {
        return value;
    }

    public boolean isMutable()
    {
        return false;
    }

    @Override
    public void setParameterValues(final Properties parameters)
    {
        String enumClassName = parameters.getProperty(ENUM);
        try
        {
            _EnumClass = ReflectHelper.classForName(enumClassName, this.getClass()).asSubclass(IEnumType.class);
        }
        catch (ClassNotFoundException exception)
        {
            throw new HibernateException("Enum class not found", exception);
        }
    }

    @Override
    public Class returnedClass()
    {
        return _EnumClass;
    }

    Object valueOf(final int value)
    {
        for (Object o : returnedClass().getEnumConstants())
        {
            IEnumType type = (IEnumType) o;
            if (type.getDbValue() == value)
                return type;
        }
        return null;
    }

    public Object nullSafeGet(final ResultSet resultSet,
                              final String[] names,
                              final Object owner)
            throws SQLException
    {
        int value = resultSet.getInt(names[0]);
        return resultSet.wasNull() ? null : valueOf(value);
    }

    public void nullSafeSet(final PreparedStatement statement,
                            final Object value,
                            final int index)
            throws SQLException
    {
        if (value == null)
            statement.setNull(index, Types.INTEGER);

        else
            statement.setInt(index, ((IEnumType) value).getDbValue());

    }

    public int hashCode(final Object o)
    {
        return o.hashCode();
    }

    public Serializable disassemble(final Object o)
    {
        return (o != null) ? ((IEnumType) o).getDbValue() : null;
    }

    public Object assemble(final Serializable serializable, final Object o)
    {
        return (serializable != null) ? valueOf((Integer) serializable) : null;
    }

    /*
     *  During merge, replace the existing (target) value in the entity we are merging to with a new (original)
     *  value from the detached entity we are merging. For immutable objects, or null values, it is safe to simply
     *  return the first parameter. For mutable objects, it is safe to return a copy of the first parameter.
     *  For objects with component values, it might make sense to recursively replace component values.
     */

    public Object replace(final Object original,
                          final Object target,
                          final Object owner)
    {
        return original;
    }
}

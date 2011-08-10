package ru.zvv.hibernate;

/**
 *
 * Интерфейс предназначен для Enum-полей, сохраняемых в БД при помощи Hibernate-типа
 *
 * {@link }

 *
 * @author Vitaly Zubchevskiy
 */
public interface IEnumType
{
    /**
     * Определяет соответсвие между enum-константой и его числовым представлением в БД
     * @return числовое значение, сохраняемое в БД
     */
    int getDbValue();
}

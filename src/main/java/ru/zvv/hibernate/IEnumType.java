package ru.zvv.hibernate;

/**
 *
 * ��������� ������������ ��� Enum-�����, ����������� � �� ��� ������ Hibernate-����
 *
 * {@link }

 *
 * @author Vitaly Zubchevskiy
 */
public interface IEnumType
{
    /**
     * ���������� ����������� ����� enum-���������� � ��� �������� �������������� � ��
     * @return �������� ��������, ����������� � ��
     */
    int getDbValue();
}

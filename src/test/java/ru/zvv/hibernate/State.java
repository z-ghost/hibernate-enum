package ru.zvv.hibernate;

/**
 * @author Vitaly Zubchevskiy
 *         Created at 15:43 08.08.11
 */
public enum State implements IEnumType
{
   Draft(1, "Черновик"),

   Active(10, "Действующий");

   private final int dbValue;
   private final String displayName;

   State(final int dbValue, final String displayName)
   {
      this.dbValue = dbValue;
       this.displayName = displayName;
   }

   @Override
   public int getDbValue()
   {
       return dbValue;
   }

   public String getDisplayName()
   {
       return displayName;
   }
}

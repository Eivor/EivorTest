package database;

public interface DatabaseConstants {

	public static final String Column_Id = "_id";
	public static final String Column_Latitude = "Latitude";
	public static final String Column_Longitude = "Longitude";

	public static final String Column_Address = "Address";
	public static final String Column_Category = "Category";
	public static final String Table_Name = "Metro";

	public static final String CreateQuery = "CREATE TABLE " + Table_Name
			+ " (" + Column_Id + " INTEGER PRIMARY KEY , " + Column_Latitude
			+ " DOUBLE, " + Column_Longitude + " DOUBLE, "
			+ Column_Address + " TEXT NOT NULL , " + Column_Category
			+ " TEXT );";

	public static final String DropTable = "DROP TABLE IF EXISTS " + Table_Name;

}

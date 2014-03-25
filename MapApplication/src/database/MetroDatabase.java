package database;

import java.util.ArrayList;
import java.util.Collections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mapapplication.MainActivity;
import com.google.android.gms.maps.model.LatLng;

public class MetroDatabase implements DatabaseConstants {

	private static final String Database_Name = "MyMetroDatabase.db";
	private static final int Database_Version = 1;

	private DbHelper mHelper;
	private Context myContext;
	private SQLiteDatabase mySQLiteDatabase;
	private boolean isDatabaseCreated = false;

	private class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, Database_Name, null, Database_Version);
			// copyDataBase();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(CreateQuery);

			isDatabaseCreated = false;
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			db.execSQL(DropTable);
			onCreate(db);

		}
	}

	public MetroDatabase(MainActivity activity) {

		myContext = activity;

	}

	public MetroDatabase open() {

		mHelper = new DbHelper(myContext);
		mySQLiteDatabase = mHelper.getWritableDatabase();

		return this;
	}

	public void Close() {

		mHelper.close();
	}

	public void insertData(Integer id, Double latitude, Double longitude,
			String address) {

		ContentValues values = new ContentValues();

		values.put(Column_Id, id);
		values.put(Column_Latitude, latitude);
		values.put(Column_Longitude, longitude);
		values.put(Column_Address, address);
		Log.e("Values ", values.getAsString(Column_Latitude));
		Log.e("Values ", values.getAsString(Column_Longitude));
		Long insertQuery = mySQLiteDatabase.insert(Table_Name, null, values);
		Log.e("insert", "" + insertQuery);
	}

	public ArrayList<LatLng> getLatLng(LatLng latLng) {

		Double lat = latLng.latitude;
		Double lon = latLng.longitude;

		ArrayList<LatLng> al1 = new ArrayList<LatLng>();
		ArrayList<LatLng> al2 = new ArrayList<LatLng>();
		ArrayList<LatLng> al3 = new ArrayList<LatLng>();

		String result = "";
		String loc = "select * from " + Table_Name + " where "
				+ Column_Latitude + " >= " + lat + " order by  "
				+ Column_Latitude + " asc ;";

		Cursor cursor = mySQLiteDatabase.rawQuery(loc, null);
		Log.e("cursor", "" + cursor);

		int iID = cursor.getColumnIndex(Column_Id);
		int ilat = cursor.getColumnIndex(Column_Latitude);
		int ilon = cursor.getColumnIndex(Column_Longitude);
		int iadd = cursor.getColumnIndex(Column_Address);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			result = cursor.getString(iID) + " " + cursor.getDouble(ilat) + " "
					+ cursor.getDouble(ilon) + " " + cursor.getString(iadd)
					+ "\n";
			LatLng l1 = new LatLng(cursor.getDouble(ilat),
					cursor.getDouble(ilon));

			Log.e("CURSOR", "" + l1.latitude);
			al1.add(l1);
		}

		String result1 = "";
		String loc1 = "select * from " + Table_Name + " where "
				+ Column_Latitude + " <= " + lat + " order by  "
				+ Column_Latitude + " asc ;";

		Cursor cursor1 = mySQLiteDatabase.rawQuery(loc1, null);
		Log.e("cursor1", "" + cursor1);

		int iID1 = cursor.getColumnIndex(Column_Id);
		int ilat1 = cursor.getColumnIndex(Column_Latitude);
		int ilon1 = cursor.getColumnIndex(Column_Longitude);
		int iadd1 = cursor.getColumnIndex(Column_Address);

		for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1
				.moveToNext()) {
			result = cursor1.getString(iID1) + " " + cursor1.getDouble(ilat1)
					+ " " + cursor1.getDouble(ilon1) + " "
					+ cursor1.getString(iadd1) + "\n";
			LatLng l2 = new LatLng(cursor1.getDouble(ilat1),
					cursor1.getDouble(ilon1));

			Log.e("Cursor2", "" + l2.latitude);
			al2.add(l2);
		}

		if (al1.size() != 0 || al2.size() != 0) {
			if (al1.size() != 0) {

				Log.e("AA", "OK");

				if (al1.size() >= 5) {

					Log.e("BB", "OK");
					for (int i = 0; i < 5; i++) {

						al3.add(al1.get(i));
					}
				} else {

					Log.e("CC", "OK");
					for (int i = 0; i < al1.size(); i++) {

						al3.add(al1.get(i));
					}
				}
			}
			if (al2.size() != 0) {
				Log.e("dd", "OK");

				if (al2.size() >= 5) {
					Log.e("ee", "OK");
					for (int i = 0; i < 5; i++) {

						al3.add(al2.get(i));
					}
				} else {

					for (int i = 0; i < al2.size(); i++) {
						Log.e("ff", "OK");
						al3.add(al2.get(i));
					}
				}

			} else {
				Log.e("arrayList", "arrayList is null");
			}

		}
		Log.e("al3", "" + al3.size());

		int i = 0;
		int j = 1;
		int k = 0;

		while (k < al3.size()) {

			if (al3.get(i).latitude > al3.get(j).latitude) {

				al3.add(i, al3.get(j));

			} else {
				j++;
			}

			i++;
			j++;

		}
		return al3;

	}

	/*
	 * private void copyDataBase() { try { if (!isDatabaseCreated) { InputStream
	 * databaseInputStream = myContext.getAssets().open( Database_Name); String
	 * databasePath = myContext.getDatabasePath(Database_Name) .getPath();
	 * OutputStream databaseOutputStream = new FileOutputStream( databasePath);
	 * 
	 * byte[] buffer = new byte[1024]; int length; while ((length =
	 * databaseInputStream.read(buffer)) > 0) {
	 * databaseOutputStream.write(buffer, 0, length);
	 * 
	 * Log.e("DATA", "" + databaseInputStream.read(buffer)); }
	 * 
	 * databaseOutputStream.flush(); databaseOutputStream.close();
	 * databaseInputStream.close();
	 * 
	 * Log.e("DATABASE", "CREATED");
	 * 
	 * } } catch (IOException e) { myContext.deleteDatabase(Database_Name); } }
	 */

}
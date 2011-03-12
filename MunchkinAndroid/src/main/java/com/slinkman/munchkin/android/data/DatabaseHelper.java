package com.slinkman.munchkin.android.data;

import com.google.inject.Inject;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "GearDataBase";

	public static final int DBVersion = 3;

	public static final String ARMOR = "ArmorType";

	public static final String BONUS = "Bonus";

	public static final String ID = "id";

	public static final String TABLENAME = "GearTable";

	public static final String createTable = "CREATE TABLE `" + TABLENAME
			+ "` ( `" + ID + "` integer primary key autoincrement, `" + ARMOR
			+ "` VARCHAR( 255 ) NOT NULL , `" + BONUS
			+ "` TINYINT UNSIGNED NOT NULL  );";

	public SQLiteDatabase readDB;

	public SQLiteDatabase writeDB;
	
	public Activity myAct;

	@Inject
	DatabaseHelper(Activity context) {
		super(context.getApplicationContext(), DBNAME, null, DBVersion);
		myAct = context;
		Log.i("DatabaseHelper", "Constructor Executed");
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		Log.i("DatabaseHelper", "DataBase Opened");
		super.onOpen(db);
	}

	@Override
	public synchronized void close() {
		Log.i("DatabaseHelper", "Closing Databases");
		super.close();
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		Log.i("DatabaseHelper", "Database Created");
		arg0.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i("DatabaseHelper", "Upgrade attempt");

	}

	private SQLiteDatabase getReadDB() {
		if (readDB == null)
			readDB = getReadableDatabase();
		return readDB;
	}

	private SQLiteDatabase getWriteDB() {
		if (writeDB == null)
			writeDB = getWritableDatabase();
		return writeDB;
	}

	public void addGear(String type, Integer bonus) {
		Log.i("DatabaseHelper", "Add Called");
		SQLiteDatabase database = getWriteDB();
		try {
			database.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(ARMOR, type);
			values.put(BONUS, bonus);
			database.insert(TABLENAME, null, values);
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public Integer[] getIDs() {
		SQLiteDatabase DB = getReadDB();
		Integer[] retVal;
		Cursor cursor = DB.query(TABLENAME, new String[] { ID }, null, null,
				null, null, ID + " ASC");
		myAct.startManagingCursor(cursor);
		if (cursor.getCount() == 0)
			return new Integer[] {};
		try {
			retVal = new Integer[cursor.getCount()];
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				retVal[i] = cursor.getInt(cursor.getColumnIndex(ID));
			}
		} finally {
			Log.i("DatabaseHelper", "closing GetID Cursor");
			cursor.close();
		}

		return retVal;
	}

	public void removeID(int id) {
		SQLiteDatabase DB = getWriteDB();
		String[] args = new String[] { Integer.toString(id) };
		try {
			Log.i("Database Helper", "Delete Attempt");
			DB.beginTransaction();
			DB.delete(TABLENAME, ID + "=?", args);
			DB.setTransactionSuccessful();
		} finally {
			DB.endTransaction();
		}
	}

	public void replace(int id, String type, int bonus) {
		Log.i("DatabaseHelper", "Replace Called");
		SQLiteDatabase DB = getWriteDB();
		try {
			DB.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(ID, id);
			values.put(ARMOR, type);
			values.put(BONUS, bonus);
			DB.replace(TABLENAME, null, values);
			DB.setTransactionSuccessful();
		} finally {
			DB.endTransaction();
		}
	}

	public String getArmorType(int getID) {
		String[] cols = { ARMOR };
		String[] args = { Integer.toString(getID) };
		SQLiteDatabase DB = getReadDB();
		String tempString;
		Cursor cursor = DB.query(TABLENAME, cols, ID + "=?", args, null, null,
				null);
		myAct.startManagingCursor(cursor);
		if (cursor.getCount() == 0)
			return "";
		try {
			cursor.moveToFirst();
			tempString = cursor.getString(cursor.getColumnIndex(ARMOR));
		} finally {
			cursor.close();
		}
		return tempString;
	}

	public Integer getBonus(int getID) {
		String[] cols = { BONUS };
		String[] args = { Integer.toString(getID) };
		Integer tempInt = 0;
		SQLiteDatabase DB = getReadDB();
		Cursor cursor = DB.query(TABLENAME, cols, ID + "=?", args, null, null,
				null);
		myAct.startManagingCursor(cursor);
		if (cursor.getCount() == 0)
			return 0;
		try {
			cursor.moveToFirst();
			tempInt = cursor.getInt(cursor.getColumnIndex(BONUS));
		} finally {
			cursor.close();
		}
		return tempInt;
	}
}

package com.slinkman.munchkin.android.temp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DBName = "TestDB";

	public static final int DBVersion = 3;

	ArrayList<Integer> idList;

	public static final String createTable = "CREATE TABLE `testTable` ( "
			+ "`id` integer primary key autoincrement, "
			+ "`name` VARCHAR( 255 ) NOT NULL , "
			+ "`age` TINYINT UNSIGNED NOT NULL  );";

	public DBHelper(Context context) {
		super(context, DBName, null, DBVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		Log.i("DBHelper", "Table create Attempt");
		arg0.execSQL(createTable);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i("DBHelper", "Table upgrade Attempt");
	}

	public void add(String name, Integer age) {
		SQLiteDatabase sqlite = this.getWritableDatabase();
		sqlite.beginTransaction();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("age", age);
		Log.i("RunClass", "database is " + (sqlite == null));
		Log.i("RunClass", sqlite.getPath() + " " + sqlite.toString());
		sqlite.insert("testTable", null, values);
		sqlite.setTransactionSuccessful();
		sqlite.endTransaction();
	}

	public List<Integer> getIDs() {
		SQLiteDatabase outDB = this.getReadableDatabase();
		Cursor crsr = outDB.query("testTable", new String[] { "id", "name",
				"age" }, null, null, null, null, null);
		// outDB.rawQuery(GET_Orders, null);
		idList = new ArrayList<Integer>();
		for (int i = 0; i < crsr.getCount(); i++) {
			crsr.moveToPosition(i);
			idList.add(crsr.getInt(crsr.getColumnIndex("id")));
			Log.i("RunClass",
					"ID is " + crsr.getString(crsr.getColumnIndex("id")));
			Log.i("RunClass",
					"Name is " + crsr.getString(crsr.getColumnIndex("name")));
			Log.i("RunClass",
					"Age is" + crsr.getInt(crsr.getColumnIndex("age")));
		}
		return idList;
	}

	public void removeID(int id) {
		SQLiteDatabase database = getWritableDatabase();
		try {
			Log.i("RunClass", "Delete attempt " + idList.get(0));
			database.beginTransaction();
			database.delete("testTable", "id=" + idList.get(0), null);
			idList.remove(0);
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public void replace(int id, String name, int age) {
		SQLiteDatabase database = getWritableDatabase();
		try {
			Log.i("RunClass", "Replace attempt " + idList.get(0));
			database.beginTransaction();
			ContentValues values = new ContentValues();
			values.put("id", id);
			values.put("name", name);
			values.put("age", age);
			database.replace("testTable", null, values);
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}
}

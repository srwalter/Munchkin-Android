package com.slinkman.munchkin.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.AlteredCharSequence;
import android.util.Log;

public class GearDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "gear.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE = "gear";

	private static final String GEAR_TYPE = "type";
	private static final String GEAR_BONUS = "bonus";

	public GearDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + "( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + GEAR_BONUS + " integer not null, "
				+ GEAR_TYPE+ " text not null);";
		Log.d("GearDatabase", "Gear Database creation");
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)
			return;
		String sql = null;
		if (oldVersion == 1)
			sql = "alter table " + TABLE + " add note text;";
		if (oldVersion == 2)
			sql = "";
		Log.d("GearDatabase", "onUpgrade " + sql );
		if (sql != null)
			db.execSQL(sql);
	}

}

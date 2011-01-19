package com.slinkman.munchkin.android.data;

import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.android.AndroidCentric;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearPresenter.GearData;

public class GearDataImpl extends BaseData implements GearData {

	Cursor currentCursor;
	GearDatabaseHelper gearHelper;
	Activity inContext;
	
	
	public GearDataImpl(AndroidCentric centric) {
		super(centric.getActivity());
		inContext = centric.getActivity();
		gearHelper = new GearDatabaseHelper(inContext);
	}
	
	@Override
	protected void finalize() throws Throwable {
		currentCursor.close();		
		super.finalize();
	}

	@Override
	public void getStringArray(int itemID, Listener<List<String>> inListener) throws WidgetError {
		if (currentCursor == null)
			new GetGearCursor().execute();
	}

	@Override
	public void setStringArray(int itemID, List<String> inArray)
			throws WidgetError {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getIntArray(int itemID, Listener<List<Integer>> inListener) throws WidgetError {
		// TODO Auto-generated method stub
	}

	@Override
	public void setIntArray(int itemID, List<Integer> inArray)
			throws WidgetError {
		// TODO Auto-generated method stub
		
	}

	class GetGearCursor extends AsyncTask<Void, Void, Cursor>{

		@Override
		protected Cursor doInBackground(Void... arg0) {
			SQLiteDatabase db = gearHelper.getReadableDatabase();
			Cursor tempCursor = db.query(GearDatabaseHelper.TABLE, null, null, null, null, null, null);
			inContext.startManagingCursor(tempCursor);
			return tempCursor;
		}
		@Override
		protected void onPostExecute(Cursor result) {
			currentCursor = result;
			super.onPostExecute(result);
		}
		
	}
}

package com.slinkman.munchkin.android.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.inject.Inject;
import com.slinkman.munchkin.android.BaseData;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.GearData;
import com.slinkman.munchkin.apis.data.GearItemData;

public class DatabaseData extends BaseData implements GearData {

	public static final String LOGID = "DatabaseData";
	DatabaseHelper helper;

	@Inject
	DatabaseData(Context inContext, DatabaseHelper inHelper) {
		super(inContext);
		helper = inHelper;
	}

	@Override
	public void getGearIds(final Listener<Integer[]> callback) {
		if (callback != null) {
			AsyncTask<Void, Void, Integer[]> tempTask = new AsyncTask<Void, Void, Integer[]>() {

				@Override
				protected Integer[] doInBackground(Void... arg0) {
					Integer[] temp = null;
					try {
						temp = helper.getIDs();
					} catch (Exception ex) {
						Log.i(LOGID, "Error in gear IDs");
					}
					return temp;
				}

				@Override
				protected void onPostExecute(Integer[] result) {
					if (result != null)
						callback.onAction(result);
				}

			};
			tempTask.execute((Void) null);
		}

	}

	@Override
	public void getArmorType(int id, final Listener<String> callback) {
		if (callback != null) {
			AsyncTask<Integer, Void, String> tempTask = new AsyncTask<Integer, Void, String>() {
				@Override
				protected String doInBackground(Integer... arg0) {
					String temp = "";
					try {
						temp = helper.getArmorType(arg0[0]);
					} catch (Exception ex) {
						Log.i(LOGID, "Error in ArmorType");
					}
					return temp;
				}

				@Override
				protected void onPostExecute(String result) {
					if (result != "")
						callback.onAction(result);
				}
			};
			tempTask.execute(id);
		}
	}

	@Override
	public void getBonus(int id, final Listener<Integer> callback) {
		if (callback != null) {
			AsyncTask<Integer, Void, Integer> tempTask = new AsyncTask<Integer, Void, Integer>() {
				@Override
				protected Integer doInBackground(Integer... arg0) {
					Integer temp = null;
					try {
						temp = helper.getBonus(arg0[0]);
					} catch (Exception ex) {
						Log.i(LOGID, "Error in Bonus");
					}
					return temp;
				}

				@Override
				protected void onPostExecute(Integer result) {
					callback.onAction(result);
				}
			};
			tempTask.execute(id);
		}
	}

	@Override
	public void addGearItem(GearItemData inItem, final Listener<Void> callback) {

		if (callback != null) {
			AsyncTask<GearItemData, Void, Void> tempTask = new AsyncTask<GearItemData, Void, Void>() {
				@Override
				protected Void doInBackground(GearItemData... arg0) {
					Log.i("DatabaseData",
							"Add Async in action. ID: " + arg0[0].getID());
					if (arg0[0].getID() == -1)
						helper.addGear(arg0[0].getArmorType(),
								arg0[0].getBonus());
					else
						helper.replace(arg0[0].getID(), arg0[0].getArmorType(),
								arg0[0].getBonus());
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					callback.onAction(null);
				}
			};
			tempTask.execute(inItem);
		}
	}

	@Override
	public void deleteGearItem(Integer id, final Listener<Void> callback) {
		if (callback != null) {
			AsyncTask<Integer, Void, Void> tempTask = new AsyncTask<Integer, Void, Void>() {
				@Override
				protected Void doInBackground(Integer... arg0) {
					helper.removeID(arg0[0]);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					callback.onAction(null);
				}
			};
			tempTask.execute(id);
		}
	}

	@Override
	public void flushData(final Listener<Void> callback) {
		if (callback != null) {
			AsyncTask<Void, Void, Void> tempTask = new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... arg0) {
					Integer[] idList = helper.getIDs();
					for (Integer id : idList)
						helper.removeID(id);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					callback.onAction(null);
				}
			};
			tempTask.execute((Void) null);
		}
	}

	@Override
	public void getTotalBonus(final Listener<Integer> callback) {

		if (callback != null) {
			AsyncTask<Void, Void, Integer> tempTask = new AsyncTask<Void, Void, Integer>() {
				@Override
				protected Integer doInBackground(Void... arg0) {
					Integer total = 0;
					try {
						Log.i("DatabaseData", "Get Total Bonus");
						Integer[] ids = helper.getIDs();
						for (Integer id : ids)
							total += helper.getBonus(id);
					} catch (Exception ex) {
						total = null;
						Log.i(LOGID, "Error in TotalBonus");
					}
					return total;
				}

				@Override
				protected void onPostExecute(Integer result) {
					if (result != null)
						callback.onAction(result);
				}
			};
			tempTask.execute((Void) null);
		}
	}

	@Override
	public void onDestroy() {
		try {
			helper.close();
		} catch (Exception ex) {
			Log.i(LOGID, "onDestroy Fail");
		}

	}

}

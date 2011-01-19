package com.slinkman.munchkin.android.data;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;

import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.android.widget.BaseActivity;

public class BaseData implements Persistance {

	Context activity;

	public BaseData(Context inActivity) {
		activity = inActivity;
	}

	public HashMap<String, Object> getSaveMap() {
		SharedPreferences mPref = activity.getSharedPreferences(
				BaseActivity.MUNCHKINFILE, Context.MODE_PRIVATE);
		return BaseActivity.pullPreferences(mPref);
	}

	public void saveMap(HashMap<String, Object> saveMap) {
		SharedPreferences inPref = activity.getSharedPreferences(
				BaseActivity.MUNCHKINFILE, Context.MODE_PRIVATE);
		BaseActivity.populatePreferences(inPref, saveMap);
	}

}

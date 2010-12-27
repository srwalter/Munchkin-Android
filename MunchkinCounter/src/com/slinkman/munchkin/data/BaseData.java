package com.slinkman.munchkin.data;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.widget.BaseActivity;

public class BaseData implements Persistance {

	Activity activity;

	public BaseData(Activity inActivity) {
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

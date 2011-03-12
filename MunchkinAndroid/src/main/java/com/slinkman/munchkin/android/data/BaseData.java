package com.slinkman.munchkin.android.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.Persistance;

public class BaseData implements Persistance {
	final static String DATANAME = "MunchkinData";
	SharedPreferences pref;
	public BaseData(Context inContext) {
		pref = inContext.getSharedPreferences(DATANAME, Activity.MODE_PRIVATE);
		
	}

	public void setVariable(String targetVar, String inVar) {
		SharedPreferences.Editor edit = pref.edit();
		edit.putString(targetVar, inVar);
		edit.commit();
	}

	public void setVariable(String targetVar, Integer inVar) {
		SharedPreferences.Editor edit = pref.edit();
		edit.putInt(targetVar, inVar);
		edit.commit();
	}

	public void getInt(String targetVar, Listener<Integer> handle) {
		handle.onAction(pref.getInt(targetVar, 0));
	}

	public void getString(String targetVar, Listener<String> handle) {
		handle.onAction(pref.getString(targetVar, null));
	}

}

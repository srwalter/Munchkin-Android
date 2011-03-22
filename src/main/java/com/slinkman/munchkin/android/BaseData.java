package com.slinkman.munchkin.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.Persistance;

public class BaseData implements Persistance {
	
	SharedPreferences pref;
	
	@Inject
	public BaseData(Context inContext) {
		pref = inContext.getSharedPreferences("Munchkin", Activity.MODE_PRIVATE);
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
		handle.onAction(pref.getInt(targetVar, -1));
	}

	public void getString(String targetVar, Listener<String> handle) {
		handle.onAction(pref.getString(targetVar, null));
	}

}

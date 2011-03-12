package com.slinkman.munchkin.android;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

/**
 * Base-most activity for Munchkin Android.
 * @author Chris Slinkman
 *
 */
public abstract class BaseActivity<T extends Presenter> extends Activity implements Persistance{
	
	SharedPreferences pref;
	T presenter;
	protected abstract View getMainView();
	protected abstract T  getPresenter();
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pref = getPreferences(MODE_PRIVATE);
	}

	/* (non-Javadoc)
	 * @see com.slinkman.munchkin.apis.data.Persistance#setVariable(java.lang.String, java.lang.String)
	 */
	public void setVariable(String targetVar, String inVar) {
		SharedPreferences.Editor edit = pref.edit();
		edit.putString(targetVar, inVar);
		edit.commit();
	}

	/* (non-Javadoc)
	 * @see com.slinkman.munchkin.apis.data.Persistance#setVariable(java.lang.String, java.lang.Integer)
	 */
	public void setVariable(String targetVar, Integer inVar) {
		SharedPreferences.Editor edit = pref.edit();
		edit.putInt(targetVar, inVar);
		edit.commit();
	}

	/* (non-Javadoc)
	 * @see com.slinkman.munchkin.apis.data.Persistance#getInt(java.lang.String, com.slinkman.munchkin.apis.Listener)
	 */
	public void getInt(String targetVar, Listener<Integer> handle) {
		handle.onAction(pref.getInt(targetVar, 0));
	}

	/* (non-Javadoc)
	 * @see com.slinkman.munchkin.apis.data.Persistance#getString(java.lang.String, com.slinkman.munchkin.apis.Listener)
	 */
	public void getString(String targetVar, Listener<String> handle) {
		handle.onAction(pref.getString(targetVar, null));
	}

	
}

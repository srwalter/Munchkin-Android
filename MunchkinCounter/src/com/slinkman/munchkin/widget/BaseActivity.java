package com.slinkman.munchkin.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.activity.Bottom;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.data.GearFileReader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public abstract class BaseActivity extends Activity {

	// Activity IDs
	public final static int ACTIVITY_COUNTER = 0x01;
	public final static int ACTIVITY_DICE = 0x02;
	public final static int ACTIVITY_POWER = 0x03;
	public final static int ACTIVITY_SETTING = 0x04;
	public final static int ACTIVITY_GEAR = 0x05;

	public static final String MUNCHKINFILE = "Munchkin";
	protected RelativeLayout overallLayout;

	protected LayoutInflater inflator;

	private View mainView;

	private View bottomView;

	private Presenter middlePresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		inflator = LayoutInflater.from(this);
		bottomView = getBottomView();
		mainView = getMainView();
		super.onCreate(savedInstanceState);
		overallLayout = new RelativeLayout(this);
		LayoutParams overallParms = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		overallLayout.setLayoutParams(overallParms);

		LayoutParams bottomParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		bottomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bottomView.setLayoutParams(bottomParams);
		overallLayout.addView(bottomView);

		LayoutParams mainParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mainParams.addRule(RelativeLayout.ABOVE, R.id.bottom_layout);
		mainView.setLayoutParams(mainParams);
		overallLayout.addView(mainView);
		setContentView(overallLayout);
	}

	@Override
	protected void onResume() {
		super.onResume();
		middlePresenter = bindMainView(mainView);
		bindBottomView(bottomView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (middlePresenter != null)
			middlePresenter.onPause();
	}

	protected View getBottomView() {
		View bottomView = inflator.inflate(R.layout.bottom_layout, null);
		return bottomView;
	}

	private void bindBottomView(View inView) {
		new Bottom(this);
	}

	public abstract int getActivityID();

	protected abstract View getMainView();

	protected abstract Presenter bindMainView(View inView);

	public static HashMap<String, Object> pullPreferences(
			SharedPreferences inPref) {
		HashMap<String, Object> returnObject = new HashMap<String, Object>();
		Map<String, ?> tempMap = inPref.getAll();
		for (String c : tempMap.keySet())
			returnObject.put(c, tempMap.get(c));
		return returnObject;
	}

	public static ArrayList<ArrayList<?>> getArrayLists(){
		ArrayList<ArrayList<?>> passer = new ArrayList<ArrayList<?>>();
		GearFileReader reader = new GearFileReader();
		passer.add(reader.getArmorList());
		passer.add(reader.getBonusList());
		return passer;
	}
	public static void populatePreferences(SharedPreferences inPref,
			HashMap<String, Object> inMap) {
		SharedPreferences.Editor mEdit = inPref.edit();
		String keys[] = new String[inMap.keySet().size()];
		int i = 0;
		for (String c : inMap.keySet())
			keys[i++] = c;

		for (String c : keys) {
			Log.i("BaseActivity", "Key: " + c);
			Object value = inMap.get(c);
			if (value.getClass() == String.class)
				mEdit.putString(c, (String) value);
			else if (value.getClass() == Integer.class)
				mEdit.putInt(c, (Integer) value);
		}
		mEdit.commit();
	}

}

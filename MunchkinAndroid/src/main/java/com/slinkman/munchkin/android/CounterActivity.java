package com.slinkman.munchkin.android;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity {

	String fontPath = "fonts/copse.ttf";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.summary);
		Button tempButton = (Button) findViewById(R.id.summary_fight_button);
		tempButton.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempButton = (Button) findViewById(R.id.summary_level_down);
		tempButton.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempButton = (Button) findViewById(R.id.summary_level_up);
		tempButton.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempButton = (Button) findViewById(R.id.summary_add);
		tempButton.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempButton = (Button) findViewById(R.id.summary_edit);
		tempButton.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		TextView tempText = (TextView) findViewById(R.id.summary_fight_score);
		tempText.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempText = (TextView) findViewById(R.id.summary_fight_title);
		tempText.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempText = (TextView) findViewById(R.id.summary_gear_score);
		tempText.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempText = (TextView) findViewById(R.id.summary_gear_title);
		tempText.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempText = (TextView) findViewById(R.id.summary_level_score);
		tempText.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
		tempText = (TextView) findViewById(R.id.summary_level_title);
		tempText.setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
	}

}

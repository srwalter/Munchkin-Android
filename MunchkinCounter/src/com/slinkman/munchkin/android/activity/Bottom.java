package com.slinkman.munchkin.android.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.android.widget.BaseActivity;

public class Bottom {
	public Bottom(final BaseActivity inActivity) {
		TextView travelText = (TextView) inActivity
				.findViewById(R.id.bottom_power);
		travelText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				inActivity.startActivity(generateBottonIntent(inActivity, Power.class));
			}
		});
		if (inActivity.getActivityID() == BaseActivity.ACTIVITY_POWER)
			travelText.setEnabled(false);

		travelText = (TextView) inActivity.findViewById(R.id.bottom_dice);
		travelText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				inActivity.startActivity(generateBottonIntent(inActivity, Dice.class));
			}
		});
		if (inActivity.getActivityID() == BaseActivity.ACTIVITY_DICE)
			travelText.setEnabled(false);
		travelText = (TextView) inActivity.findViewById(R.id.bottom_counter);
		travelText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				inActivity.startActivity(generateBottonIntent(inActivity, Counter.class));
			}
		});
		if (inActivity.getActivityID() == BaseActivity.ACTIVITY_COUNTER)
			travelText.setEnabled(false);
		
		travelText = (TextView) inActivity.findViewById(R.id.bottom_gear);
		travelText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inActivity.startActivity(generateBottonIntent(inActivity, Gear.class));
			}
		});
		if (inActivity.getActivityID() == BaseActivity.ACTIVITY_GEAR)
			travelText.setEnabled(false);
		
		travelText = (TextView) inActivity.findViewById(R.id.bottom_setting);
		travelText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				inActivity.startActivity(generateBottonIntent(inActivity, Settings.class));
			}
		});
		if (inActivity.getActivityID() == BaseActivity.ACTIVITY_SETTING)
			travelText.setEnabled(false);
	}
	
	public Intent generateBottonIntent(BaseActivity homeActivity,@SuppressWarnings("rawtypes") Class inClass){
		Intent intentHolder = new Intent (homeActivity, inClass);
		intentHolder.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		return intentHolder;
	}
}

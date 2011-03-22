package com.slinkman.munchkin.android.temp;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class RunClass extends Activity {

	DBHelper help;
	List<Integer> idList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		help = new DBHelper(this);

		LinearLayout totalLayout = new LinearLayout(getApplicationContext());
		// Add Button
		Button tempButton = new Button(getApplicationContext());
		tempButton.setText("Add");
		tempButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				help.add("Chris", 25);
			}
		});
		totalLayout.addView(tempButton);

		// Get IDs
		tempButton = new Button(getApplicationContext());
		tempButton.setText("GET");
		tempButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				idList = help.getIDs();

			}
		});
		totalLayout.addView(tempButton);

		// Delete Top
		tempButton = new Button(getApplicationContext());
		tempButton.setText("Delete Top");
		tempButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				help.removeID(idList.indexOf(0));
				idList = help.getIDs();
			}
		});
		totalLayout.addView(tempButton);
		// Replace Top
		tempButton = new Button(getApplicationContext());
		tempButton.setText("Replace Top");
		tempButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				help.replace(idList.get(0), "TestChange", 20);
			}
		});
		totalLayout.addView(tempButton);
		setContentView(totalLayout);

	}
}

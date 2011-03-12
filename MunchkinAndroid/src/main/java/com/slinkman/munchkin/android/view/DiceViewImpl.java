package com.slinkman.munchkin.android.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;
import com.slinkman.munchkin.android.R;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.view.DiceView;

public class DiceViewImpl implements DiceView<View> {

	View viewHandle;
	@Inject
	DiceViewImpl(LayoutInflater inflator) {
		viewHandle = inflator.inflate(R.layout.dice_layout, null);
	}
	
	public void setRoll(final Listener<Void> handle) {
		Button inView = (Button) viewHandle.findViewById(R.id.dice_roll_button);
		inView.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				handle.onAction(null);
			}
		});
	}

	public void setDice(int resource) {
		TextView diceDisplay = (TextView) viewHandle.findViewById(R.id.dice_roll_display);
		diceDisplay.setText(Integer.toString(resource));
	}

	public View getHandle() {
		return viewHandle;
	}

}

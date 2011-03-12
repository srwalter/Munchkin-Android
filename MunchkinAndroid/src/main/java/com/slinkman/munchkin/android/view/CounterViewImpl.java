package com.slinkman.munchkin.android.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.slinkman.munchkin.android.R;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.view.CounterView;

public class CounterViewImpl implements CounterView<View> {

	LayoutInflater inflator;
	View viewHandle;
	@Inject
	CounterViewImpl(LayoutInflater inflate) {
		inflator = inflate;
		viewHandle = inflator.inflate(R.layout.counter_layout, null);
	}

	public void setCounterText(String inString) {
		TextView inView = (TextView) viewHandle.findViewById(R.id.full_count);
		inView.setText(inString);
	}

	public void setUpListener(final Listener<Void> handle) {
		ImageView inView = (ImageView) viewHandle.findViewById(R.id.counter_up);
		inView.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				handle.onAction(null);

			}
		});
	}

	public void setDownListener(final Listener<Void> handle) {
		ImageView inView = (ImageView) viewHandle
				.findViewById(R.id.counter_down);
		inView.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				handle.onAction(null);

			}
		});
	}

	public void setUpEnabled(boolean enabled) {
		ImageView inView = (ImageView) viewHandle.findViewById(R.id.counter_up);
		inView.setEnabled(enabled);
		if (enabled)
			inView.setAlpha(255);
		else
			inView.setAlpha(100);
	}

	public void setDownEnabled(boolean enabled) {
		ImageView inView = (ImageView) viewHandle
				.findViewById(R.id.counter_down);
		inView.setEnabled(enabled);
		if (enabled)
			inView.setAlpha(255);
		else
			inView.setAlpha(100);
	}

	public View getHandle() {
		return viewHandle;
	}

	public void setGearValue(int inValue) {
		// TODO Auto-generated method stub
		
	}

}

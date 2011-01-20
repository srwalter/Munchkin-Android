package com.slinkman.munchkin.presenter;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;

public class CounterPresenter implements Presenter {
	// Listeners
	public final static int LISTENER_UP_BUTTON = 0x01;
	public final static int LISTENER_DOWN_BUTTON = 0x02;

	// Enabled
	public final static int ENABLED_UP = 0x01;
	public final static int ENABLED_DOWN = 0x02;

	// Text
	public final static int TEXT_COUNTER = 0x01;

	public interface CountView {

		public void setCounterText(String inString);

		public void setUpListener(Listener<Void> handle);

		public void setDownListener(Listener<Void> handle);

		public void setUpEnabled(boolean enabled);

		public void setDownEnabled(boolean enabled);

	};

	private CountView viewHandle;
	private int currentCounter = 0;
	private Persistance data;
	private int lastLevel = 0;

	public CounterPresenter(CountView inView, Persistance inData) {
		viewHandle = inView;
		data = inData;
		data.getInt(Persistance.VAR_PLAYER_LEVEL_LAST, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				if (inObject == null)
					currentCounter = 1;
				else
					currentCounter = inObject;
				viewHandle.setCounterText(Integer.toString(currentCounter));
				regulateLevel();
			}
		});
		data.getInt(Persistance.VAR_TOPLEVEL, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				lastLevel = inObject;
				regulateLevel();
			}
		});
		setupEnabled();
		viewHandle.setUpListener(new Listener<Void>() {
			@Override
			public void onAction(Void in) {
				viewHandle.setCounterText(Integer.toString(++currentCounter));
				setupEnabled();
			}
		});
		viewHandle.setDownListener(new Listener<Void>() {

			@Override
			public void onAction(Void in) {
				viewHandle.setCounterText(Integer.toString(--currentCounter));
				setupEnabled();
			}
		});
	}

	private void regulateLevel() {
		if (lastLevel == 0 || currentCounter == 0)
			return;
		if (lastLevel < currentCounter)
			currentCounter = lastLevel;
		if (currentCounter < 1)
			currentCounter = 1;
	}

	private void setupEnabled() {
		if (currentCounter == 1) {
			viewHandle.setDownEnabled(false);
			viewHandle.setUpEnabled(true);
		} else if (currentCounter == lastLevel) {
			viewHandle.setDownEnabled(true);
			viewHandle.setUpEnabled(false);
		} else {
			viewHandle.setDownEnabled(true);
			viewHandle.setUpEnabled(true);
		}
	}

	public void onPause() {
		data.setVariable(Persistance.VAR_PLAYER_LEVEL_LAST, currentCounter);
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

}

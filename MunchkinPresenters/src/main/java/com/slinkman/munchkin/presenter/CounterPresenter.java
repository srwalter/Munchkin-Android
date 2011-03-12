package com.slinkman.munchkin.presenter;

import com.google.inject.Inject;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.CounterView;

public class CounterPresenter implements Presenter {

	final static int UP_ENABLED = 0x01;
	final static int DOWN_ENABLED = 0x02;

	CounterView<?> view;
	Persistance data;

	Integer currentLevel;
	Integer topLevel;

	@Inject
	CounterPresenter(CounterView<?> inView, Persistance inData) {
		view = inView;
		data = inData;
	}

	public void bind() {
		data.getInt(Persistance.VAR_PLAYER_LEVEL_LAST, new Listener<Integer>() {
			
			public void onAction(Integer inObject) {
				currentLevel = inObject;
				if (currentLevel == -1)
					currentLevel = 1;
				checkEnabled();
				view.setCounterText(Integer.toString(currentLevel));
			}
		});

		data.getInt(Persistance.VAR_TOPLEVEL, new Listener<Integer>() {
			public void onAction(Integer inObject) {
				topLevel = inObject;
				if (topLevel == -1)
					topLevel = 10;
				checkEnabled();

			}
		});
		view.setUpListener(new Listener<Void>() {

			public void onAction(Void inObject) {
				view.setCounterText(Integer.toString(++currentLevel));
				checkEnabled();
			}
		});
		view.setDownListener(new Listener<Void>() {

			public void onAction(Void inObject) {
				view.setCounterText(Integer.toString(--currentLevel));
				checkEnabled();
			}
		});
	}

	private void checkEnabled() {
		if (currentLevel == null || topLevel == null)
			return;
		if (currentLevel == topLevel)
			setEnabled(DOWN_ENABLED);
		else if (currentLevel == 1)
			setEnabled(UP_ENABLED);
		else
			setEnabled(DOWN_ENABLED | UP_ENABLED);
	}

	private void setEnabled(int inOptions) {
		view.setUpEnabled((inOptions & UP_ENABLED) == UP_ENABLED);
		view.setDownEnabled((inOptions & DOWN_ENABLED) == DOWN_ENABLED);
	}

	public void onPause() {
		data.setVariable(Persistance.VAR_PLAYER_LEVEL_LAST, currentLevel);
		data.setVariable(Persistance.VAR_TOPLEVEL, topLevel);
	}

	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public<U> U getAppHandle() {
		return (U) view.getHandle();
	}

}

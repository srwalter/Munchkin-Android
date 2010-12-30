package com.slinkman.munchkin.presenter;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.error.WidgetError;

public class CounterPresenter implements Presenter {
	// Listeners
	public final static int LISTENER_UP_BUTTON = 0x01;
	public final static int LISTENER_DOWN_BUTTON = 0x02;
	public final static int LISTENER_SWIPE_UP = 0x03;
	public final static int LISTENER_SWIPE_DOWN = 0x04;

	// Enabled
	public final static int ENABLED_UP = 0x01;
	public final static int ENABLED_DOWN = 0x02;

	// Text
	public final static int TEXT_COUNTER = 0x01;



	public interface CountView {
		public void setWidgetEnabled(int objectID, boolean inEnabled)
		throws WidgetError;
		public void setListener(int objectID, Listener inListener) throws WidgetError;
		public void setWidgetText(int objectID, String inText) throws WidgetError;
	};

	private CountView viewHandle;
	private int currentCounter = 1;
	private Persistance data;
	private int lastLevel = 10;

	public CounterPresenter(CountView inView, Persistance inData) {

		try {
			viewHandle = inView;
			data = inData;
			if (data.getSaveMap().size() > 0){
				if (data.getSaveMap().containsKey(Persistance.VAR_PLAYER_LEVEL_LAST))
					currentCounter = (Integer) data.getSaveMap().get(Persistance.VAR_PLAYER_LEVEL_LAST);
				if (data.getSaveMap().containsKey(Persistance.VAR_TOPLEVEL))
					lastLevel = (Integer) data.getSaveMap().get(Persistance.VAR_TOPLEVEL);
			}
			if (lastLevel < currentCounter)
				currentCounter = lastLevel;
			if (currentCounter < 1)
				currentCounter = 1;
			viewHandle.setWidgetText(TEXT_COUNTER, Integer
					.toString(currentCounter));
			setupEnabled();
			viewHandle.setListener(LISTENER_UP_BUTTON, new Listener() {
				@Override
				public void onAction() {
					try {
						viewHandle.setWidgetText(TEXT_COUNTER, Integer
								.toString(++currentCounter));
						setupEnabled();
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
					;
				}
			});
			viewHandle.setListener(LISTENER_DOWN_BUTTON, new Listener() {

				@Override
				public void onAction() {
					try {
						viewHandle.setWidgetText(TEXT_COUNTER, Integer
								.toString(--currentCounter));
						setupEnabled();
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
					;
				}
			});
		} catch (WidgetError ex) {
			System.out.println("Widget doesn't exist");
		}
		;
	}

	private void setupEnabled() throws WidgetError {
		if (currentCounter == 1) {
			viewHandle.setWidgetEnabled(ENABLED_DOWN, false);
			viewHandle.setWidgetEnabled(ENABLED_UP, true);
		} else if (currentCounter == lastLevel) {
			viewHandle.setWidgetEnabled(ENABLED_DOWN, true);
			viewHandle.setWidgetEnabled(ENABLED_UP, false);
		} else {
			viewHandle.setWidgetEnabled(ENABLED_DOWN, true);
			viewHandle.setWidgetEnabled(ENABLED_UP, true);
		}
	}

	public void onPause() {
		HashMap<String, Object> saveInfo = new HashMap<String, Object>();
		saveInfo.put(Persistance.VAR_PLAYER_LEVEL_LAST, currentCounter);
		data.saveMap(saveInfo);
	}

}

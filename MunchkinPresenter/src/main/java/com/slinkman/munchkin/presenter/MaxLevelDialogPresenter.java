package com.slinkman.munchkin.presenter;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.EnabledInterface;
import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.ListenerInterface;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.ReturnListener;
import com.slinkman.munchkin.baseinterface.TextInterface;
import com.slinkman.munchkin.error.WidgetError;

public class MaxLevelDialogPresenter {

	// Enabled
	public static final int ENABLE_DOWN = 0x01;

	// Listener
	public static final int LISTENER_UP = 0x01;
	public static final int LISTENER_DOWN = 0x02;
	public static final int LISTENER_DONE = 0x03;

	// Text
	public static final int TEXT_TOP_LEVEL = 0x01;

	public interface MaxLevelDialogViewInterface {
		public void setListener(int objectID, Listener inListener)
				throws WidgetError;

		public void setWidgetText(int objectID, String inText)
				throws WidgetError;

		public void setWidgetEnabled(int objectID, boolean inEnabled)
				throws WidgetError;
	};

	private MaxLevelDialogViewInterface view;
	private Persistance data;
	private int currentLevel = 10;

	public MaxLevelDialogPresenter(MaxLevelDialogViewInterface inView,
			Persistance inData, final ReturnListener<Integer> inListener) {
		view = inView;
		data = inData;
		try {
			if (inData.getSaveMap().containsKey(Persistance.VAR_TOPLEVEL))
				currentLevel = (Integer) inData.getSaveMap().get(
						Persistance.VAR_TOPLEVEL);
			view.setWidgetText(TEXT_TOP_LEVEL, Integer.toString(currentLevel));
			view.setListener(LISTENER_DONE, new Listener() {

				public void onAction() {
					inListener.onAction(currentLevel);
				}
			});
			view.setListener(LISTENER_UP, new Listener() {

				public void onAction() {
					try {
						view.setWidgetText(TEXT_TOP_LEVEL,
								Integer.toString(++currentLevel));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_DOWN, new Listener() {

				public void onAction() {
					try {
						view.setWidgetText(TEXT_TOP_LEVEL,
								Integer.toString(--currentLevel));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	public void onPause() {
		HashMap<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put(Persistance.VAR_TOPLEVEL, currentLevel);
		data.saveMap(saveMap);
	}
}

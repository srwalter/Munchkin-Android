package com.slinkman.munchkin.presenter;

import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.error.WidgetError;

public class SettingPresenter implements Presenter {

	// Text
	public final static int TEXT_LEVEL_LIMIT = 0x01;

	// Listener
	public final static int LISTENER_MAX_ITEM = 0x01;

	// Return Listener
	public final static int RETURN_CHANGE_DIALOG = 0x01;

	public interface SettingView  {
		public void setDialogListener(int id, Listener<Integer> dialogListener) throws WidgetError;
		public void setWidgetText(int objectID, String inText) throws WidgetError;
		public void setListener(int objectID, Listener<Void> inListener) throws WidgetError;
	};

	SettingView view;
	Persistance data;

	int topLevel = 10;

	public SettingPresenter(SettingView inView, Persistance inData) {
		view = inView;
		data = inData;
		if (data.getSaveMap().containsKey(Persistance.VAR_TOPLEVEL))
			topLevel = (Integer) data.getSaveMap()
					.get(Persistance.VAR_TOPLEVEL);
		try {
			view.setWidgetText(TEXT_LEVEL_LIMIT, Integer.toString(topLevel));
			view.setListener(LISTENER_MAX_ITEM, new Listener<Void>() {

				@Override
				public void onAction(Void in) {
					try {
						view.setDialogListener(RETURN_CHANGE_DIALOG,
								dialogReturn);
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		HashMap<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put(Persistance.VAR_TOPLEVEL, topLevel);
		data.saveMap(saveMap);
	}

	Listener<Integer> dialogReturn = new Listener<Integer>() {
		public void onAction(Integer inObject) {
				try {
					topLevel = (Integer) inObject;
					view.setWidgetText(TEXT_LEVEL_LIMIT,
							Integer.toString((Integer) inObject));
				} catch (WidgetError ex) {
					ex.printStackTrace();
				}
		}
	};
}

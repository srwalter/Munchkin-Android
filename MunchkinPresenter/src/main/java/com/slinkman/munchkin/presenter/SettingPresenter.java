package com.slinkman.munchkin.presenter;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.ListenerInterface;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.baseinterface.ReturnListener;
import com.slinkman.munchkin.baseinterface.ReturnListenerInterface;
import com.slinkman.munchkin.baseinterface.TextInterface;
import com.slinkman.munchkin.error.WidgetError;

public class SettingPresenter implements Presenter {

	// Text
	public final static int TEXT_LEVEL_LIMIT = 0x01;

	// Listener
	public final static int LISTENER_MAX_ITEM = 0x01;

	// Return Listener
	public final static int RETURN_CHANGE_DIALOG = 0x01;

	public interface SettingView extends TextInterface, ListenerInterface,
			ReturnListenerInterface {
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
			view.setListener(LISTENER_MAX_ITEM, new Listener() {

				@Override
				public void onAction() {
					try {
						view.setListener(RETURN_CHANGE_DIALOG, dialogReturn);
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

	ReturnListener dialogReturn = new ReturnListener() {
		public void onAction(int idType, Object inObject) {
			if (idType == ReturnListener.VAR_INTEGER)
				try {
					topLevel = (Integer) inObject;
					view.setWidgetText(TEXT_LEVEL_LIMIT, Integer
							.toString((Integer) inObject));
				} catch (WidgetError ex) {
					ex.printStackTrace();
				} 
		}
	};
}

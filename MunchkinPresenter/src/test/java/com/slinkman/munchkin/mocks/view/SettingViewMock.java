package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.ReturnListener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.SettingPresenter;
import com.slinkman.munchkin.presenter.SettingPresenter.SettingView;

public class SettingViewMock implements SettingView {

	public HashMap<Integer, String> textMap = new HashMap<Integer, String>();
	public HashMap<Integer, Listener> listenerMap = new HashMap<Integer, Listener>();
	public HashMap<Integer, ReturnListener> returnMap = new HashMap<Integer, ReturnListener>();

	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID){
		case SettingPresenter.TEXT_LEVEL_LIMIT:
			textMap.put(objectID, inText);
			break;
		default:
			throw new WidgetError();
		}
	}

	public void setListener(int objectID, Listener inListener)
			throws WidgetError {
		switch(objectID){
		case SettingPresenter.LISTENER_MAX_ITEM:
			listenerMap.put(objectID, inListener);
			break;
		default:
			throw new WidgetError();
		}
	}

	public void setListener(int objectID, ReturnListener inListener)
			throws WidgetError {
		switch(objectID){
		case SettingPresenter.RETURN_CHANGE_DIALOG:
			returnMap.put(objectID, inListener);
			break;
		default:
			throw new WidgetError();
		}
	}
}

package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.CounterPresenter;
import com.slinkman.munchkin.presenter.CounterPresenter.CountView;

public class CounterViewMock implements CountView {
	public static final Integer TRUE = 0x01;
	public static final Integer FALSE = 0x02;

	public HashMap<Integer, Integer> enabledMap = new HashMap<Integer, Integer>();
	public HashMap<Integer, String> textMap = new HashMap<Integer, String>();
	public HashMap<Integer, Listener> listenerMap = new HashMap<Integer, Listener>();
	public HashMap<String, Object> saveMap = new HashMap<String, Object>();

	@Override
	public void setWidgetEnabled(int objectID, boolean inEnabled)
			throws WidgetError {
		switch (objectID) {
		case CounterPresenter.ENABLED_DOWN:
		case CounterPresenter.ENABLED_UP:
			enabledMap.put(objectID, (inEnabled) ? TRUE : FALSE);
			break;
		default:
			throw new WidgetError();
		}
	}

	@Override
	public void setListener(int objectID, Listener inListener)
			throws WidgetError {
		switch (objectID){
		case CounterPresenter.LISTENER_DOWN_BUTTON:
		case CounterPresenter.LISTENER_UP_BUTTON:
		case CounterPresenter.LISTENER_SWIPE_DOWN:
		case CounterPresenter.LISTENER_SWIPE_UP:
			listenerMap.put(objectID, inListener);
			break;
		default:
			throw new WidgetError();
		}
	}

	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID) {
		case CounterPresenter.TEXT_COUNTER:
			textMap.put(objectID, inText);
			break;
		default:
			throw new WidgetError();
		}
	}
}

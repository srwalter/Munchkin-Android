package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.PowerPresenter;
import com.slinkman.munchkin.presenter.PowerPresenter.PowerView;

public class PowerViewMock implements PowerView {
	public HashMap<Integer, Listener> listenerMap = new HashMap<Integer, Listener>();
	public HashMap<Integer, String> textMap = new HashMap<Integer, String>();
	public HashMap<Integer, Integer> stateMap = new HashMap<Integer, Integer>();

	public void setListener(int objectID, Listener inListener)
			throws WidgetError {
		switch(objectID){
		case PowerPresenter.LISTENER_DOWN_MONSTER:
		case PowerPresenter.LISTENER_DOWN_PLAYER:
		case PowerPresenter.LISTENER_MONSTER_RESET:
		case PowerPresenter.LISTENER_PLAYER_BASE:
		case PowerPresenter.LISTENER_UP_MONSTER:
		case PowerPresenter.LISTENER_UP_PLAYER:
			listenerMap.put(objectID, inListener);
			break;
			default:
				throw new WidgetError();
		}

	}

	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID) {
		case PowerPresenter.TEXT_MONSTER_LEVEL:
		case PowerPresenter.TEXT_PLAYER_LEVEL:
		case PowerPresenter.TEXT_PLAYER_DIFF:
			textMap.put(objectID, inText);
			break;
		default:
			throw new WidgetError();
		}
	}
}

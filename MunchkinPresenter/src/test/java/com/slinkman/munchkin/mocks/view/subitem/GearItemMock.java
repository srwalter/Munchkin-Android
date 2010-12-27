package com.slinkman.munchkin.mocks.view.subitem;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;

public class GearItemMock implements GearItemView {

	public HashMap<Integer, Listener> listenerMap = new HashMap<Integer, Listener>();
	public HashMap<Integer, String> textMap = new HashMap<Integer, String>();
	@Override
	public void setListener(int objectID, Listener inListener)
			throws WidgetError {
		switch (objectID){
		case GearPresenter.LIST_LISTENER_DELETE:
		case GearPresenter.LIST_LISTENER_EDIT:
			listenerMap.put(objectID, inListener);
			break;
		default:
			throw new WidgetError();
		}

	}

	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID){
		case GearPresenter.LIST_TEXT_ARMOR_TYPE:
		case GearPresenter.LIST_TEXT_BONUS:
			textMap.put(objectID, inText);
			break;
		default:
			throw new WidgetError();
		}

	}

}

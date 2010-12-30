package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.ParameterReturn;
import com.slinkman.munchkin.ReturnListener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.mocks.view.subitem.GearItemMock;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;
import com.slinkman.munchkin.presenter.GearPresenter.GearView;

public class GearViewMock implements GearView {

	public HashMap<Integer, Listener> listenerMap = new HashMap<Integer, Listener>();
	public HashMap<Integer, ReturnListener> returnMap = new HashMap<Integer, ReturnListener>();
	public HashMap<Integer, GearItemMock> viewMap = new HashMap<Integer, GearItemMock>();

	public ParameterReturn<GearItemView> populator;

	private int listCount;

	public void setListener(int objectID, Listener inListener)
			throws WidgetError {
		switch (objectID) {
		case GearPresenter.LISTENER_CLEAR_GEAR:
		case GearPresenter.LISTENER_NEW_GEAR:
			listenerMap.put(objectID, inListener);
			break;
		default:
			throw new WidgetError();
		}

	}

	public void setReturnListener(int objectID, ReturnListener inListener)
			throws WidgetError {
		switch (objectID) {
		case GearPresenter.RETURN_LISTENER_NEW_GEAR:
		case GearPresenter.RETURN_LISTENER_GEAR_ITEM:
			returnMap.put(objectID, inListener);
			break;
		default:
			throw new WidgetError();
		}

	}

	public ReturnListener<Integer> refreshList() {

		return new ReturnListener<Integer>() {

			@Override
			public void onAction(Integer inObject) {
				listCount = inObject;
				viewMap.clear();
				for (int i = 0; i < listCount; i++) {
					GearItemMock temp = new GearItemMock();
					populator.onAction(i, temp);
					viewMap.put(i, temp);
				}
			}
		};
	}

	public void setPopulator(ParameterReturn<GearItemView> inListener) {
		populator = inListener;
	}

	public ReturnListener<Integer> listCountReturn() {
		return new ReturnListener<Integer>() {
			public void onAction(Integer inObject) {
					listCount = inObject;
			}
		};
	}

}

package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.ParameterListener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.mocks.view.subitem.GearItemMock;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;
import com.slinkman.munchkin.presenter.GearPresenter.GearView;

public class GearViewMock implements GearView {

	public HashMap<Integer, Listener<Void>> listenerMap = new HashMap<Integer, Listener<Void>>();
	public HashMap<Integer, Listener<Object[]>> returnMap = new HashMap<Integer, Listener<Object[]>>();
	public HashMap<Integer, GearItemMock> viewMap = new HashMap<Integer, GearItemMock>();

	public ParameterListener<GearItemView> populator;

	private int listCount;

	public void setListener(int objectID, Listener<Void> inListener)
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

	public void setReturnListener(int objectID, Listener<Object[]> inListener)
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

	public Listener<Integer> getRefreshList() {

		return new Listener<Integer>() {

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

	public void setPopulator(ParameterListener<GearItemView> inListener) {
		populator = inListener;
	}

	public Listener<Integer> listCountReturn() {
		return new Listener<Integer>() {
			public void onAction(Integer inObject) {
					listCount = inObject;
			}
		};
	}

}

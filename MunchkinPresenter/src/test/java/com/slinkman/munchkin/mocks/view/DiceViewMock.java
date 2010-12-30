package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.DicePresenter;
import com.slinkman.munchkin.presenter.DicePresenter.DiceView;

public class DiceViewMock implements DiceView {

	public HashMap<Integer, Listener<Void>> listenerMap = new HashMap<Integer, Listener<Void>>();
	public HashMap<Integer, Integer> resourceMap = new HashMap<Integer, Integer>();

	public HashMap<Integer, Integer> clickCache = new HashMap<Integer, Integer>();
	@Override
	public void setListener(int objectID, final Listener<Void> inListener)
			throws WidgetError {
		switch (objectID) {
		case DicePresenter.LISTENER_ROLL_CLICK:
			Listener<Void> tempListener = new Listener<Void>() {
				
				@Override
				public void onAction(Void in) {
					inListener.onAction(null);
					int tempCache = clickCache.get(DicePresenter.LISTENER_ROLL_CLICK);
					clickCache.put(DicePresenter.LISTENER_ROLL_CLICK, ++tempCache);
				}
			};
			clickCache.put(DicePresenter.LISTENER_ROLL_CLICK, 0);
			listenerMap.put(objectID, tempListener);
			break;
		default:
			throw new WidgetError();
		}

	}

	@Override
	public void setWidgetResource(int objectID, int widgetState)
			throws WidgetError {
		switch (objectID) {
		case DicePresenter.RESOURCE_ONE:
		case DicePresenter.RESOURCE_TWO:
		case DicePresenter.RESOURCE_THREE:
		case DicePresenter.RESOURCE_FOUR:
		case DicePresenter.RESOURCE_FIVE:
		case DicePresenter.RESOURCE_SIX:
			resourceMap.put(objectID, widgetState);
			break;
		default:
			throw new WidgetError();
		}

	}

}

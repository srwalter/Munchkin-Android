package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearDialogPresenter.GearDialogViewInterface;

public class GearDialogMock implements GearDialogViewInterface {

	public HashMap<Integer, Listener<Void>> listenerMap = new HashMap<Integer, Listener<Void>>();
	public HashMap<Integer, String> stringMap = new HashMap<Integer, String>();
	public HashMap<Integer, Listener<String>> stringReturnMap = new HashMap<Integer, Listener<String>>();
	public HashMap<Integer, Listener<Integer>> integerReturnMap = new HashMap<Integer, Listener<Integer>>();
	@Override
	public void setListener(int objectID, Listener<Void> inListener)
			throws WidgetError {
		listenerMap.put(objectID, inListener);

	}

	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		stringMap.put(objectID, inText);

	}

	@Override
	public void setStringReturnListener(int objectID,
			Listener<String> inListener) throws WidgetError {
		stringReturnMap.put(objectID, inListener);

	}

	@Override
	public void setIntegerReturnListener(int objectID,
			Listener<Integer> inListener) throws WidgetError {
		integerReturnMap.put(objectID, inListener);
	}

}

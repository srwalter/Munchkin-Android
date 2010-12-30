package com.slinkman.munchkin.mocks.view;

import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.ReturnListener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearDialogPresenter.GearDialogViewInterface;

public class GearDialogMock implements GearDialogViewInterface {

	public HashMap<Integer, Listener> listenerMap = new HashMap<Integer, Listener>();
	public HashMap<Integer, String> stringMap = new HashMap<Integer, String>();
	public HashMap<Integer, ReturnListener<String>> stringReturnMap = new HashMap<Integer, ReturnListener<String>>();
	public HashMap<Integer, ReturnListener<Integer>> integerReturnMap = new HashMap<Integer, ReturnListener<Integer>>();
	@Override
	public void setListener(int objectID, Listener inListener)
			throws WidgetError {
		listenerMap.put(objectID, inListener);

	}

	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		stringMap.put(objectID, inText);

	}

	@Override
	public void setStringReturnListener(int objectID,
			ReturnListener<String> inListener) throws WidgetError {
		stringReturnMap.put(objectID, inListener);

	}

	@Override
	public void setIntegerReturnListener(int objectID,
			ReturnListener<Integer> inListener) throws WidgetError {
		integerReturnMap.put(objectID, inListener);
	}

}

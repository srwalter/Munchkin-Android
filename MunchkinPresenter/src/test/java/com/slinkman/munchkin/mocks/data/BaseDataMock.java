package com.slinkman.munchkin.mocks.data;

import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;

public class BaseDataMock implements Persistance {

	public HashMap<String, String> stringHash = new HashMap<String, String>();

	public HashMap<String, Integer> intHash = new HashMap<String, Integer>();

	@Override
	public void setVariable(String targetVar, String inVar) {
		stringHash.put(targetVar, inVar);

	}

	@Override
	public void setVariable(String targetVar, Integer inVar) {
		intHash.put(targetVar, inVar);

	}

	@Override
	public void getInt(String targetVar, Listener<Integer> handle) {
		handle.onAction(intHash.get(targetVar));

	}

	@Override
	public void getString(String targetVar, Listener<String> handle) {
		handle.onAction(stringHash.get(targetVar));

	}

}

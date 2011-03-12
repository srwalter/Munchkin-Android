package com.slinkman.munchkin.mocks.data;

import java.util.HashMap;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.Persistance;

public class BaseDataMock implements Persistance {

	public HashMap<String, String> stringHash = new HashMap<String, String>();

	public HashMap<String, Integer> intHash = new HashMap<String, Integer>();

	public BaseDataMock() {
		intHash.put(Persistance.VAR_TOPLEVEL, 10);
	}

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
		Integer returnInteger = intHash.get(targetVar);
		if (returnInteger == null)
			handle.onAction(-1);
		else
			handle.onAction(intHash.get(targetVar));

	}

	@Override
	public void getString(String targetVar, Listener<String> handle) {
		String returnString = stringHash.get(targetVar);
		if (returnString == null)
			handle.onAction("");
		else
			handle.onAction(stringHash.get(targetVar));

	}

}

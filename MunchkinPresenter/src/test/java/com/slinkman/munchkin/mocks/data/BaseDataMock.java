package com.slinkman.munchkin.mocks.data;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Persistance;

public class BaseDataMock implements Persistance {

	public HashMap<String, Object> dataHash = new HashMap<String, Object>();

	public HashMap<String, Object> getSaveMap() {
		return dataHash;
	}

	public void saveMap(HashMap<String, Object> saveMap) {
		for (String c: saveMap.keySet())
			dataHash.put(c, saveMap.get(c));
	}

}

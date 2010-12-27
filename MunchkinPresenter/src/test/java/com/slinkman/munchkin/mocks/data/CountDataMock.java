package com.slinkman.munchkin.mocks.data;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Persistance;

public class CountDataMock implements Persistance {

	public HashMap<String, Object> dataHash = new HashMap<String, Object>();
	@Override
	public HashMap<String, Object> getSaveMap() {
		return dataHash;
	}

	@Override
	public void saveMap(HashMap<String, Object> saveMap) {
		for (String c: saveMap.keySet())
			dataHash.put(c, saveMap.get(c));
	}

}

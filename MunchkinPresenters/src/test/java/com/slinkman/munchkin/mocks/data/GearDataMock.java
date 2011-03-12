package com.slinkman.munchkin.mocks.data;

import java.util.HashMap;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.GearData;
import com.slinkman.munchkin.apis.data.GearItemData;

public class GearDataMock extends BaseDataMock implements GearData {

	int nextID = 0;
	public HashMap<Integer, GearItemData> dataMap;

	public GearDataMock() {
		dataMap = new HashMap<Integer, GearItemData>();
	}

	@Override
	public void getGearIds(Listener<Integer[]> callback) {
		int counter = 0;
		Integer[] retArray = new Integer[dataMap.size()];
		for (Integer curID : dataMap.keySet()) {
			retArray[counter] = curID;
			counter++;
		}
		callback.onAction(retArray);
	}

	@Override
	public void getArmorType(int id, Listener<String> callback) {
		callback.onAction(dataMap.get(id).getArmorType());

	}

	@Override
	public void getBonus(int id, Listener<Integer> callback) {
		callback.onAction(dataMap.get(id).getBonus());
	}

	@Override
	public void addGearItem(GearItemData inItem, Listener<Void> callback) {
		if (inItem.getID() == -1)
			dataMap.put(nextID++, inItem);
		else
			dataMap.put(inItem.getID(), inItem);
		if (callback != null)
			callback.onAction(null);
	}

	@Override
	public void deleteGearItem(Integer id, Listener<Void> callback) {
		dataMap.remove(id);
		callback.onAction(null);
	}

	@Override
	public void flushData(Listener<Void> callback) {
		dataMap.clear();
		callback.onAction(null);
	}

	@Override
	public void getTotalBonus(Listener<Integer> callback) {
		int totalGear = 0;
		for (Integer I : dataMap.keySet())
			totalGear += dataMap.get(I).getBonus();
		callback.onAction(totalGear);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

}

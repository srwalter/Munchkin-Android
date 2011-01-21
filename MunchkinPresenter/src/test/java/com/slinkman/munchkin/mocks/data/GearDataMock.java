package com.slinkman.munchkin.mocks.data;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.model.GearItemData;
import com.slinkman.munchkin.presenter.GearPresenter.GearData;

public class GearDataMock extends BaseDataMock implements GearData{
	
	@Override
	public void getGearIds(Listener<Integer[]> callback) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getArmorType(int id, Listener<String> callback) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getBonus(int id, Listener<Integer> callback) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addGearItem(GearItemData inItem, Listener<Integer> callback) {
		// TODO Auto-generated method stub
		
	}
}

package com.slinkman.munchkin.mocks.data;

import java.util.HashMap;
import java.util.List;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.error.DataError;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearData;

public class GearDataMock extends BaseDataMock implements GearData{
	
	public HashMap<Integer, List<String>> stringMap = new HashMap<Integer, List<String>>();
	public HashMap<Integer, List<Integer>> intMap = new HashMap<Integer, List<Integer>>();



	@Override
	public void getGearIds(Listener<Integer[]> callback) throws WidgetError {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void getArmorType(int id, Listener<String> callback) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void getBonus(int id, Listener<Integer> callback) throws DataError {
		// TODO Auto-generated method stub
		
	}

}

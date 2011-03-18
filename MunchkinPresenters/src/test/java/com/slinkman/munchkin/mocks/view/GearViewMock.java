package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Populator;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.view.GearItemView;
import com.slinkman.munchkin.apis.view.GearView;

public class GearViewMock implements GearView<Void> {

	public Listener<GearItemData> displayHandle;
	
	public Listener<Void> clearHandle;
	
	public Listener<Populator<GearItemView<?>>> populator;
	
	public int passedId=-1;
	public int lastTotal = 0;
	
	public String passedArmor;
	public String passedBonus;

	public Listener<Integer> refreshHandle = new Listener<Integer>() {
		public void onAction(Integer inObject) {
			lastTotal = inObject;
		}
	};

	@Override
	public void setClearGear(Listener<Void> handle) {
		clearHandle = handle;

	}

	@Override
	public Listener<Integer> getRefreshList() {
		// TODO Auto-generated method stub
		return refreshHandle;
	}

	@Override
	public void setPopulator(Listener<Populator<GearItemView<?>>> handle) {
		populator = handle;

	}

	@Override
	public void setDisplayHandle(Listener<GearItemData> handle) {
		displayHandle = handle;

	}


	@Override
	public Void getHandle() {
		return null;
	}

	@Override
	public void displayGearWindow(int inItem, String bonusText, String armorText) {
		passedId = inItem;
		passedArmor = armorText;
		passedBonus = bonusText;
		
	}


}

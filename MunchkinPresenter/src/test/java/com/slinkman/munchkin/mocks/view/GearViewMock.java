package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.ParameterListener;
import com.slinkman.munchkin.model.GearItemData;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;
import com.slinkman.munchkin.presenter.GearPresenter.GearView;

public class GearViewMock implements GearView {

	public Listener<Void> clearGear;
	public Listener<GearItemData> addGear;
	public Listener<GearItemData> editGear;
	public ParameterListener<GearItemView> populator;
	public GearItemData inGearData;
	public int cacheAmount;
	
	public GearViewMock() {
		cacheAmount = 0;
	}
	
	@Override
	public void displayEditGear(GearItemData gearData,
			Listener<GearItemData> handle) {
		inGearData = gearData;
		editGear = handle;
	}

	@Override
	public void setClearGear(Listener<Void> handle) {
		clearGear = handle;
	}

	@Override
	public void setAddGear(Listener<GearItemData> handle) {
		addGear = handle;
	}

	@Override
	public Listener<Integer> getRefreshList() {
		
		return new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				cacheAmount++;
				
			}
		};
	}

	@Override
	public void setPopulator(ParameterListener<GearItemView> inListener) {
		populator = inListener;
		
	}


}

package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.view.GearItemView;

public class GearItemViewMock implements GearItemView<Void> {

	public String bonus;
	public String armor;
	public Listener<Void> edit;
	public Listener<Void> delete;
	
	@Override
	public void setBonusText(String inString) {
		bonus = inString;
	}

	@Override
	public void setArmorText(String inString) {
		armor = inString;
	}

	@Override
	public void setEditListner(Listener<Void> handle) {
		edit = handle;
	}

	@Override
	public void setDeleteListener(Listener<Void> handle) {
		delete = handle;
	}

	@Override
	public Void getHandle() {
		return null;
	}


}

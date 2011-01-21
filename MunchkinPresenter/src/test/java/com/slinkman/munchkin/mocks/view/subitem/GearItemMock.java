package com.slinkman.munchkin.mocks.view.subitem;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;

public class GearItemMock implements GearItemView {

	String bonusText;
	String armorText;
	Listener<Void> editListener;
	Listener<Void> deleteListener;

	@Override
	public void setBonusText(String inString) {
		bonusText = inString;

	}

	@Override
	public void setArmorText(String inString) {
		armorText = inString;

	}

	@Override
	public void setEditListner(Listener<Void> handle) {
		editListener = handle;

	}

	@Override
	public void setDeleteListener(Listener<Void> handle) {
		deleteListener = handle;

	}

}

package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.model.GearItemData;
import com.slinkman.munchkin.presenter.GearDialogPresenter.GearDialogViewInterface;

public class GearDialogMock implements GearDialogViewInterface {

	public Listener<GearItemData> addListener;
	public Listener<Void> cancelListener;
	public String armorText;
	public String bonusText;

	@Override
	public void setAddListner(Listener<GearItemData> handle) {
		addListener = handle;

	}

	@Override
	public void setCancelListener(Listener<Void> handle) {
		cancelListener = handle;

	}

	@Override
	public void setArmorText(String inText) {
		armorText = inText;

	}

	@Override
	public void setBonusText(Integer inValue) {
		bonusText = Integer.toString(inValue);

	}

}

package com.slinkman.munchkin.android.data;

import com.slinkman.munchkin.presenter.GearPresenter.GearItemData;

public class GearItemDataImpl implements GearItemData {
	String armorType;
	Integer bonus;
	
	@Override
	public String getArmorType() {
		return armorType;
	}
	
	@Override
	public Integer getBonus() {
		return bonus;
	}

	public void setArmorType(String armorType) {
		this.armorType = armorType;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
}
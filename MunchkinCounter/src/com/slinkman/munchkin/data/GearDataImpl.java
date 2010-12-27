package com.slinkman.munchkin.data;

import java.util.ArrayList;

import android.app.Activity;

import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearData;

public class GearDataImpl extends BaseData implements GearData {

	GearWriter writer;
	GearFileReader reader;

	public GearDataImpl(Activity inActivity) {
		super(inActivity);
	}

	@Override
	public Object getItem(int itemID, Object[] parms) {
		reader = new GearFileReader();
		reader.startReader();
		switch (itemID) {
		case GearPresenter.DATA_CAST_ARMOR_ARRAY:
			return reader.getArmorList();
		case GearPresenter.DATA_CAST_BONUS_ARRAY:
			return reader.getBonusList();
		}
		return null;
	}

	@Override
	public void setItem(int itemID, Object inObject) {
		switch (itemID) {
		case GearPresenter.DATA_CAST_ARMOR_ARRAY:
		case GearPresenter.DATA_CAST_BONUS_ARRAY:
			Object[] tempArray = (Object[]) inObject;
			ArrayList<Integer> bonus = (ArrayList<Integer>) tempArray[1];
			ArrayList<String> armor = (ArrayList<String>) tempArray[0];
			writer = new GearWriter(armor,
					bonus);
			writer.startWrite();
		}
	}
}

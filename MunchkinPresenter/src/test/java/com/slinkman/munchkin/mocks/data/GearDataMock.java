package com.slinkman.munchkin.mocks.data;

import java.util.HashMap;

import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearData;

public class GearDataMock extends BaseDataMock implements GearData{
	
	public HashMap<Integer, Object> objectMap = new HashMap<Integer, Object>();


	public Object getItem(int itemID, Object[] parms) throws WidgetError {
		switch (itemID){
		case GearPresenter.DATA_CAST_ARMOR_ARRAY:
		case GearPresenter.DATA_CAST_BONUS_ARRAY:
			return objectMap.get(itemID);
		default:
			throw new WidgetError();
		}
	}

	public void setItem(int itemID, Object inObject) throws WidgetError {
		switch (itemID){
		case GearPresenter.DATA_CAST_ARMOR_ARRAY:
		case GearPresenter.DATA_CAST_BONUS_ARRAY:
			Object[] temp = (Object[]) inObject;
			objectMap.put(GearPresenter.DATA_CAST_ARMOR_ARRAY, temp[0]);
			objectMap.put(GearPresenter.DATA_CAST_BONUS_ARRAY, temp[1]);
			break;
		default:
			throw new WidgetError();
		}
	}

}

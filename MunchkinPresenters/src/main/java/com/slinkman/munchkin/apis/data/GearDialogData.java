package com.slinkman.munchkin.apis.data;

import com.slinkman.munchkin.apis.Listener;


public interface GearDialogData {
	public void setInItem(GearItemData handle);

	public void setBackReturn(Listener<GearItemData> handle);

	public void getInItem(Listener<GearItemData> handle);

	public void getBackReturn(Listener<Listener<GearItemData>> handle);
}

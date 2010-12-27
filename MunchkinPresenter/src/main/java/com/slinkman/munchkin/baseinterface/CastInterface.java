package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface CastInterface {
	public Object getItem(int itemID, Object[] parms) throws WidgetError;
	public void setItem(int itemID, Object inObject) throws WidgetError;
}

package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface ListTextInterface {
	public void setWidgetText(int objectID,int listID, String inText) throws WidgetError;
}

package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface TextInterface {
	public void setWidgetText(int objectID, String inText) throws WidgetError;
}

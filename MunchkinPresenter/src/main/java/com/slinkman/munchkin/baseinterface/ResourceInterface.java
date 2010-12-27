package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface ResourceInterface {
	public void setWidgetResource(int objectID, int widgetState) throws WidgetError;
}

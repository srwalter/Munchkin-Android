package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface EnabledInterface {
	public void setWidgetEnabled(int objectID, boolean inEnabled)
			throws WidgetError;
}

package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface ReturnListenerInterface {
	public void setListener(int objectID, ReturnListener inListener) throws WidgetError;
}

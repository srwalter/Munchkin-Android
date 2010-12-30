package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface ReturnListenerInterface<T extends Object> {
	public void setReturnListener(int objectID, ReturnListener<T> inListener) throws WidgetError;
}

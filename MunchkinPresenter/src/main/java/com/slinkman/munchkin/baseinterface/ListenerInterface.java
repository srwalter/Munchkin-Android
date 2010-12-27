package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface ListenerInterface {
	public void setListener(int objectID, Listener inListener) throws WidgetError;
}

package com.slinkman.munchkin.baseinterface;

import com.slinkman.munchkin.error.WidgetError;

public interface ListListenerInterface {
	public void setListener(int objectID, int listID,  Listener inListener) throws WidgetError;
}

package com.slinkman.munchkin.baseinterface;

public interface ReturnListener {
	public final static int VAR_STRING = 0x01;
	public final static int VAR_INTEGER = 0x02;
	public final static int VAR_OBJECT_ARRAY = 0x03;
	public final static int VAR_LIST_TYPE = 0x04;
	void onAction(int idType, Object inObject);
}

package com.slinkman.munchkin.baseinterface;


public interface ListInterface<T> {
	public ReturnListener<Integer> refreshList();
	public void setPopulator (ParameterReturn<T> inListener);
}

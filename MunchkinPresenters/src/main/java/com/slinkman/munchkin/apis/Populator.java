package com.slinkman.munchkin.apis;

public interface Populator<T> {
	public T getView();

	public int getID();
}

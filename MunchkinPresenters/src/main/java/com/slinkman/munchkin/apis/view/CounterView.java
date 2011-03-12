package com.slinkman.munchkin.apis.view;

import com.slinkman.munchkin.apis.Listener;


public interface CounterView<T> extends BaseView<T>{

	/**
	 * Main Counter view of the screen
	 * @param inString Text that will be displayed
	 */
	public void setCounterText(String inString);

	/**
	 * The Up action on the screen
	 * @param handle
	 */
	public void setUpListener(Listener<Void> handle);

	/**
	 * The Down action on the screen
	 * @param handle
	 */
	public void setDownListener(Listener<Void> handle);

	/**
	 * Sets the up enabled property
	 * @param enabled
	 */
	public void setUpEnabled(boolean enabled);

	/**
	 * Sets the down enabled property
	 * @param enabled
	 */
	public void setDownEnabled(boolean enabled);
	
	/**
	 * Set gear value
	 * @param inValue Total value of all gear
	 */
	public void setGearValue(int inValue);
}

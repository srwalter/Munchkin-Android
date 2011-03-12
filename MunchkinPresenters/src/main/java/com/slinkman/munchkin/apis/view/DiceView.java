package com.slinkman.munchkin.apis.view;

import com.slinkman.munchkin.apis.Listener;


public interface DiceView<T> extends BaseView<T> {
	/**
	 * Set the action handler for the roll action
	 * 
	 * @param handle
	 */
	public void setRoll(Listener<Void> handle);

	/**
	 * Set the dice value in the display
	 * 
	 * @param resource
	 */
	public void setDice(int resource);
}

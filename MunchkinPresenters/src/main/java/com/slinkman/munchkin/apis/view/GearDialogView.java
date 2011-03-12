package com.slinkman.munchkin.apis.view;

import com.slinkman.munchkin.apis.Listener;

/**
 * The interface for the Gear Dialog edit or add view. Each method is used to
 * either display information or respond to an action taken.
 * 
 * @author chrisslinkman
 * 
 */
public interface GearDialogView<T> extends BaseView<T>{

	/**
	 * Responds to the user pressing the Add Button
	 * @param handle
	 */
	public void setAddListner(Listener<Void> handle);
	
	/**
	 * Responds to the user pressing the cancel button
	 * @param handle
	 */
	public void setCancelListener(Listener<Void> handle);

	/**
	 * Display the armor text passed in
	 * @param inText
	 */
	public void setArmorText(String inText);

	/**
	 * Displays the bonus value passed in
	 * @param inValue
	 */
	public void setBonusText(Integer inValue);
}

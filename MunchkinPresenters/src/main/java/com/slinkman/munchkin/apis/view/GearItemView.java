package com.slinkman.munchkin.apis.view;

import com.slinkman.munchkin.apis.Listener;


public interface GearItemView<T> extends BaseView<T>{
	/**
	 * Set the bonus text in the view
	 * 
	 * @param handle
	 */
	public void setBonusText(String inString);

	/**
	 * Set the armor text in a view
	 * 
	 * @param handle
	 */
	public void setArmorText(String inString);

	/**
	 * Set the action to edit the armor listener
	 * 
	 * @param handle
	 */
	public void setEditListner(Listener<Void> handle);

	/**
	 * Set the action to delete the gear
	 * 
	 * @param handle
	 */
	public void setDeleteListener(Listener<Void> handle);
	
};
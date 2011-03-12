package com.slinkman.munchkin.apis.view;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Populator;
import com.slinkman.munchkin.apis.data.GearItemData;

public interface GearView<T> extends BaseView<T>{
	/**
	 * Show the edit gear screen with gearData if not null. Otherwise display
	 * empty screen. When item is done call the handle to return the object to
	 * the presenter.
	 * 
	 * @param gearData
	 *            If not null the information will populate the window.
	 */
	public void displayGearWindow(int gearID);

	/**
	 * Set the handle for displaying the gear item.
	 * 
	 * @param handle
	 *            Handle to return the information to the presenter and Thrown
	 *            when targeted peice of the view doesn't exist
	 */
	public void setDisplayHandle(Listener<GearItemData> handle);

	/**
	 * Set the view handle for the action to clear the gear of a player
	 * 
	 * @param handle
	 *            Handle to inform the presenter that action must be taken
	 */
	public void setClearGear(Listener<Void> handle);

	/**
	 * Get a handle to the view to refresh the gear list
	 * 
	 * @return View handle to allow the presenter to update when the data has
	 *         changed
	 */
	public Listener<Integer> getRefreshList();

	/**
	 * What is being passed is a Listener which will send a {@link Populator}
	 * type back to the presenter. The Presenter will then process the
	 * {@link Populator} that is sent back to it and populate the passed view
	 * with the proper information.
	 * 
	 * @param inListener
	 */
	public void setPopulator(Listener<Populator<GearItemView<?>>> handle);

};
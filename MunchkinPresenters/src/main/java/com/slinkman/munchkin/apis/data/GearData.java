package com.slinkman.munchkin.apis.data;

import com.slinkman.munchkin.apis.Listener;

public interface GearData extends Persistance {

	/**
	 * Method is used to get the id's of all items in the database
	 * 
	 * @param callback
	 *            Called when the total is ready
	 */
	public void getGearIds(Listener<Integer[]> callback);

	/**
	 * Method is used to retrieve the armor of an item with the specified id
	 * 
	 * @param id
	 *            ID of the item requested
	 * @param callback
	 *            Called when the variable is ready to be returned
	 */
	public void getArmorType(int id, Listener<String> callback);

	/**
	 * Method is used to retrieve the bonus of an item with the specified id
	 * 
	 * @param id
	 *            Id of the item requested
	 * @param callback
	 *            Callback item used when information is ready to be returned
	 */
	public void getBonus(int id, Listener<Integer> callback);

	/**
	 * Method used to retrieve the total amount of item bonus
	 * 
	 * @param callback
	 */
	public void getTotalBonus(Listener<Integer> callback);

	/**
	 * Method to add a new gear item to the data backend. The listener will be
	 * called once the item is placed 
	 * 
	 * @param armorType
	 * @param bonusAmount
	 * @param callback
	 */
	public void addGearItem(GearItemData inItem, Listener<Void> callback);

	/**
	 * Method to delete specified gear id.
	 * 
	 * @param id
	 *            Gear ID to delete
	 */
	public void deleteGearItem(Integer id, Listener<Void> callback);

	/**
	 * Method to clear all gear item data
	 */
	public void flushData(Listener<Void> callback);
	
	/**
	 * Method to call when connection isn't needed anymore
	 */
	public void onDestroy();
}

package com.slinkman.munchkin.apis.view;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.GearItemData;

public interface SummaryView<T> extends BaseView<T> {

	/**
	 * Method to set the text of the current level
	 * @param inText
	 */
	public void setLevelText(String inText);
	
	/**
	 * Method to set the text of the current gear score
	 * @param inText
	 */
	public void setGearText(String inText);
	
	
	/**
	 * Method to set the text of the current Fight Score
	 * @param inText
	 */
	public void setFightScore(String inText);
	
	/**
	 * Set method when fight action is fired
	 * @param fightReturn
	 */
	public void setFightAction(Listener<Void> fightReturn);
	
	
	/**
	 * Set Method for when up is fired
	 * @param upReturn
	 */
	public void setUpLevel(Listener<Void> upReturn);
	
	/**
	 * Set Method for when down is fired
	 * @param downReturn
	 */
	public void setDownLevel(Listener<Void> downReturn);
	
	/** 
	 * Set Method for add is fired
	 * @param addReturn
	 */
	public void setAddHandle(Listener<Void> buttonReturn, Listener<GearItemData> dataHandle);
	
	/**
	 * Set Method for edit is fired
	 * @param editReturn
	 */
	public void setEditGear(Listener<Void> editReturn);
	
	/**
	 * Method to enable up button
	 * @param inEnabled
	 */
	public void setUpLevelEnabled(boolean inEnabled);
	
	/**
	 * Method to enable down button
	 * @param inEnabled
	 */
	public void setDownLevelEnabled(boolean inEnabled);
	
	/*
	 * Display add gear screen
	 */
	public void showAdd();
	
	/**
	 * Display the gear edit list
	 */
	public void showGearEdit();
	
	/**
	 * Display Fight Screen
	 */
	
	public void showFightScreen();
}

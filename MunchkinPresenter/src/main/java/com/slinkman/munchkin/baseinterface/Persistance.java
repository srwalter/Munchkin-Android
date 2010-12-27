package com.slinkman.munchkin.baseinterface;

import java.util.HashMap;

public interface Persistance {
	// Variable Persistence
	public final static String VAR_PLAYER_LEVEL_LAST = "Player Last Level";
	public final static String VAR_TOPLEVEL = "Player Top Level";
	public final static String VAR_PLAYER_POWER_LAST = "Player Last Power";
	public final static String VAR_MONSTER_LAST = "Monster Last Power";
	public final static String VAR_DICE_LAST = "Monster Last Power";
	public final static String VAR_ITEM_EDIT_ARMOR = "Gear Edit Armor";
	public final static String VAR_ITEM_EDIT_BONUS = "Gear Edit Bonus";
	public final static String VAR_TOTAL_GEAR = "Total Gear Score";
	
	public void saveMap(HashMap<String, Object> saveMap);
	public HashMap<String, Object> getSaveMap();
}

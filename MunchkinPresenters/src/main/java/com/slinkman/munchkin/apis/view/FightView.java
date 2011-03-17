package com.slinkman.munchkin.apis.view;

import com.slinkman.munchkin.apis.Listener;

public interface FightView<T> extends BaseView<T>{

	/* Buttons */
	
	public void setPlayerUp(Listener<Void> upHandle);
	
	public void setPlayerDown(Listener<Void> downHandle);
	
	public void setMonsterUp(Listener<Void> upHandle);
	
	public void setMonsterDown(Listener<Void> downHandle);

	public void setBackButton(Listener<Void> backHandle);
	
	public void setMonsterReset(Listener<Void> resetHandle);
	
	public void setPlayerReset(Listener<Void> resetHandle);
	
	/* Data handle */
	public void setMonsterHandle(Listener<Integer> monsterHandle);
	
	/* Display Text */
	
	public void setPlayerFight(String fightScore);
	
	public void setPlayerModifier(String modifier);
	
	public void setPlayerTotal(String total);
	
	public void setMonsterFight(String fightScore);
	
	public void setMonsterModifier(String modifier);
	
	public void setMonsterTotal(String total);
	
	public void setWinText(String winString);
	
}

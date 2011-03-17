package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.view.FightView;

public class FightViewMock implements FightView<Void> {
	
	public String playerfight="";
	public String playerModifier="";
	public String playerTotal="";
	public String monsterFight="";
	public String monsterModifier="";
	public String monsterTotal="";
	public String winText="";
	
	public Listener<Void> backHandle;
	public Listener<Void> playerDown;
	public Listener<Void> playerUp;
	public Listener<Void> monsterUp;
	public Listener<Void> monsterDown;
	
	public Listener<Integer> monsterSetHandle;
	public Listener<Void> monsterReset;
	public Listener<Void> playerReset;
	
	public boolean backHit=false;

	@Override
	public void setBackButton(final Listener<Void> backHandle) {
		this.backHandle = new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				backHit=true;
				backHandle.onAction(null);
			}
		};
	}

	@Override
	public void setMonsterDown(Listener<Void> downHandle) {
		this.monsterDown = downHandle;
		
	}

	@Override
	public void setMonsterFight(String fightScore) {
		this.monsterFight = fightScore;
		
	}

	@Override
	public void setMonsterModifier(String modifier) {
		this.monsterModifier = modifier;
		
	}

	@Override
	public void setMonsterTotal(String total) {
		this.monsterTotal = total;
		
	}

	@Override
	public void setMonsterUp(Listener<Void> upHandle) {
		this.monsterUp = upHandle;
		
	}

	@Override
	public void setPlayerDown(Listener<Void> downHandle) {
		this.playerDown = downHandle;
		
	}

	@Override
	public void setPlayerFight(String fightScore) {
		this.playerfight = fightScore;
		
	}

	@Override
	public void setPlayerModifier(String modifier) {
		this.playerModifier = modifier;
		
	}

	@Override
	public void setPlayerTotal(String total) {
		this.playerTotal = total;
		
	}

	@Override
	public void setPlayerUp(Listener<Void> upHandle) {
		this.playerUp = upHandle;
		
	}

	@Override
	public Void getHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWinText(String winString) {
		this.winText = winString;
	}

	@Override
	public void setMonsterHandle(Listener<Integer> monsterHandle) {
		this.monsterSetHandle = monsterHandle;
		
	}

	@Override
	public void setMonsterReset(Listener<Void> resetHandle) {
		this.monsterReset = resetHandle;
		
	}

	@Override
	public void setPlayerReset(Listener<Void> resetHandle) {
		this.playerReset = resetHandle;
		
	}

}

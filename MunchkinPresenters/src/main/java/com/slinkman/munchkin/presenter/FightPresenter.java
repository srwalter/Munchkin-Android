package com.slinkman.munchkin.presenter;

import com.google.inject.Inject;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.FightView;

public class FightPresenter implements Presenter {

	FightView<?> view;

	Persistance data;

	int curGear = 0;

	int curLevel = 1;

	int playerMod = 0;

	int playerFight = 1;

	int monsterMod = 0;

	int monsterFight = 0;

	@Inject
	FightPresenter(FightView<?> inView, Persistance inData) {
		this.view = inView;
		this.data = inData;
	}

	@Override
	public void bind() {
		// View setup
		view.setBackButton(new Listener<Void>() {

			@Override
			public void onAction(Void inObject) {

			}
		});
		view.setMonsterDown(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				view.setMonsterModifier(Integer.toString(--monsterMod));
				view.setMonsterTotal(Integer
						.toString(monsterFight + monsterMod));
				evaluateWin();
			}
		});
		view.setMonsterUp(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				view.setMonsterModifier(Integer.toString(++monsterMod));
				view.setMonsterTotal(Integer
						.toString(monsterFight + monsterMod));
				evaluateWin();
			}
		});
		view.setPlayerDown(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				view.setPlayerModifier(Integer.toString(--playerMod));
				view.setPlayerTotal(Integer.toString(playerFight + playerMod));
				evaluateWin();
			}
		});
		view.setPlayerUp(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				view.setPlayerModifier(Integer.toString(++playerMod));
				view.setPlayerTotal(Integer.toString(playerFight + playerMod));
				evaluateWin();
			}
		});
		view.setMonsterHandle(new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				if (inObject != null)
					monsterFight = inObject;
				else
					monsterFight = 0;
				view.setMonsterFight(Integer.toString(monsterFight));
				view.setMonsterTotal(Integer
						.toString(monsterFight + monsterMod));
				evaluateWin();
			}
		});

		view.setMonsterReset(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				monsterFight = 0;
				monsterMod = 0;
				view.setMonsterModifier("0");
				view.setMonsterFight("0");
				view.setMonsterTotal("0");
				evaluateWin();
			}
		});

		view.setPlayerReset(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				playerMod = 0;
				view.setPlayerModifier("0");
				view.setPlayerTotal(Integer.toString(playerFight));
				evaluateWin();
			}
		});
		// Assume that no previous monster existed
		view.setMonsterFight("0");
		view.setMonsterModifier("0");
		view.setMonsterTotal("0");

		// Setup the player with basic
		view.setPlayerModifier("0");
		view.setPlayerFight("1");
		view.setPlayerTotal("1");

		// Data Initial
		data.getInt(Persistance.VAR_TOTAL_GEAR, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				if (inObject != -1)
					curGear = inObject;
				else
					curGear = 0;
				redisplayPlayer();
			}
		});
		data.getInt(Persistance.VAR_PLAYER_LEVEL_LAST, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				if (inObject != -1)
					curLevel = inObject;
				else
					curLevel = 1;
				redisplayPlayer();
			}
		});
		data.getInt(Persistance.VAR_LAST_MONSTER_FIGHT,
				new Listener<Integer>() {
					@Override
					public void onAction(Integer inObject) {
						if (inObject == -1)
							return;

						monsterFight = inObject;
						view.setMonsterFight(Integer.toString(monsterFight));
						view.setMonsterTotal(Integer.toString(monsterFight+ monsterMod));
						evaluateWin();
					}
				});
		data.getInt(Persistance.VAR_LAST_MONSTER_MOD, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				if (inObject == -1)
					return;
				monsterMod = inObject;
				view.setMonsterTotal(Integer.toString(monsterFight+ monsterMod));
				view.setMonsterModifier(Integer.toString(monsterMod));
				evaluateWin();
			}
		});
		data.getInt(Persistance.VAR_LAST_PLAYER_MOD, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				if (inObject == -1)
					return;
				playerMod = inObject;
				view.setPlayerModifier(Integer.toString(playerMod));
				view.setPlayerTotal(Integer.toString(playerFight + playerMod));
				evaluateWin();
			}
		});
	}

	private void redisplayPlayer() {
		playerFight = curGear + curLevel;
		view.setPlayerFight(Integer.toString(playerFight));
		view.setPlayerTotal(Integer.toString(playerFight + playerMod));
		evaluateWin();
	}

	private void evaluateWin() {
		int playerTotal = playerFight + playerMod;
		int monsterTotal = monsterFight + monsterMod;
		if (playerTotal > monsterTotal)
			view.setWinText("Player Wins");
		else if (monsterTotal > playerTotal)
			view.setWinText("Monster Wins");
		else
			view.setWinText("Tied");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> U getAppHandle() {
		return (U) view.getHandle();
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause() {
		data.setVariable(Persistance.VAR_LAST_MONSTER_MOD, monsterMod);
		data.setVariable(Persistance.VAR_LAST_MONSTER_FIGHT, monsterFight);
		data.setVariable(Persistance.VAR_LAST_PLAYER_MOD, playerMod);
	}

}

package com.slinkman.munchkin.presenter;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.error.WidgetError;

public class PowerPresenter implements Presenter {
	// Listeners
	public final static int LISTENER_UP_PLAYER = 0x01;
	public final static int LISTENER_DOWN_PLAYER = 0x02;
	public final static int LISTENER_UP_MONSTER = 0x03;
	public final static int LISTENER_DOWN_MONSTER = 0x04;
	public final static int LISTENER_PLAYER_BASE = 0x05;
	public final static int LISTENER_MONSTER_RESET = 0x06;

	// Text
	public final static int TEXT_PLAYER_LEVEL = 0x01;
	public final static int TEXT_MONSTER_LEVEL = 0x02;
	public final static int TEXT_PLAYER_DIFF = 0x03;

	// Widget ID
	public final static int TEMP_ID = 0x01;

	public interface PowerView {
		public void upPlayer(Listener<Void> handle);

		public void downPlayer(Listener<Void> handle);

		public void upMonster(Listener<Void> handle);

		public void downMonster(Listener<Void> handle);

		public void playerReset(Listener<Void> handle);

		public void monsterReset(Listener<Void> handle);

		public void setPlayerLevel(Integer inValue);

		public void setMonsterLevel(Integer inValue);

		public void setPlayerDiff(Integer inValue);
	};

	private Persistance data;

	private PowerView view;

	private int playerPower = 0;

	private int monsterPower = 0;

	private int playerLevel = 0;

	public PowerPresenter(PowerView inView, Persistance inData) {
		view = inView;
		data = inData;
		try {
			data.getInt(Persistance.VAR_PLAYER_POWER_LAST,
					new Listener<Integer>() {
						@Override
						public void onAction(Integer inObject) {
							playerPower = inObject;

						}
					});
			data.getInt(Persistance.VAR_MONSTER_LAST, new Listener<Integer>() {
				@Override
				public void onAction(Integer inObject) {
					monsterPower = inObject;
				}
			});
			data.getInt(Persistance.VAR_TOTAL_GEAR, new Listener<Integer>() {
				@Override
				public void onAction(Integer inObject) {
					playerLevel += inObject;

				}
			});
			data.getInt(Persistance.VAR_PLAYER_LEVEL_LAST,
					new Listener<Integer>() {
						@Override
						public void onAction(Integer inObject) {
							playerLevel += inObject;

						}
					});

			view.setWidgetText(TEXT_PLAYER_DIFF, getPlayerText(playerPower));
			view.setWidgetText(TEXT_PLAYER_LEVEL, Integer.toString(playerPower));
			view.setWidgetText(TEXT_MONSTER_LEVEL,
					Integer.toString(monsterPower));
			view.setListener(LISTENER_UP_PLAYER, new Listener<Void>() {

				public void onAction(Void in) {
					try {
						view.setWidgetText(TEXT_PLAYER_DIFF,
								getPlayerText(++playerPower));
						view.setWidgetText(TEXT_PLAYER_LEVEL,
								Integer.toString(playerPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_DOWN_PLAYER, new Listener<Void>() {

				public void onAction(Void in) {
					try {
						view.setWidgetText(TEXT_PLAYER_DIFF,
								getPlayerText(--playerPower));
						view.setWidgetText(TEXT_PLAYER_LEVEL,
								Integer.toString(playerPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_DOWN_MONSTER, new Listener<Void>() {

				public void onAction(Void in) {
					try {
						view.setWidgetText(TEXT_MONSTER_LEVEL,
								Integer.toString(--monsterPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_UP_MONSTER, new Listener<Void>() {

				public void onAction(Void in) {
					try {
						view.setWidgetText(TEXT_MONSTER_LEVEL,
								Integer.toString(++monsterPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_MONSTER_RESET, new Listener<Void>() {

				public void onAction(Void in) {
					try {
						monsterPower = 1;
						view.setWidgetText(TEXT_MONSTER_LEVEL,
								Integer.toString(monsterPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_PLAYER_BASE, new Listener<Void>() {

				public void onAction(Void in) {
					playerPower = playerLevel;
					try {
						view.setWidgetText(TEXT_PLAYER_DIFF,
								getPlayerText(playerPower));
						view.setWidgetText(TEXT_PLAYER_LEVEL,
								Integer.toString(playerPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	private String getPlayerText(Integer currentPower) {
		if (playerPower - playerLevel > 0)
			return ("(+" + Integer.toString(playerPower - playerLevel) + ")");
		return ("(" + Integer.toString(playerPower - playerLevel) + ")");
	}

	@Override
	public void onPause() {
		data.setVariable(Persistance.VAR_PLAYER_POWER_LAST, playerPower);
		data.setVariable(Persistance.VAR_MONSTER_LAST, monsterPower);
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

}

package com.slinkman.munchkin.presenter;

import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.ListenerInterface;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.baseinterface.TextInterface;
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

	public interface PowerView extends ListenerInterface, TextInterface {
	};

	private Persistance data;

	private PowerView view;

	private int playerPower = 1;

	private int monsterPower = 1;

	private int playerLevel = 1;

	public PowerPresenter(PowerView inView, Persistance inData) {
		view = inView;
		data = inData;
		try {
			if (data.getSaveMap() != null) {
				if (data.getSaveMap().containsKey(
						Persistance.VAR_PLAYER_POWER_LAST))
					playerPower = (Integer) data.getSaveMap().get(
							Persistance.VAR_PLAYER_POWER_LAST);
				if (data.getSaveMap().containsKey(Persistance.VAR_MONSTER_LAST))
					monsterPower = (Integer) data.getSaveMap().get(
							Persistance.VAR_MONSTER_LAST);
				if (data.getSaveMap().containsKey(
						Persistance.VAR_PLAYER_LEVEL_LAST))
					playerLevel = (Integer) data.getSaveMap().get(
							Persistance.VAR_PLAYER_LEVEL_LAST);
				if (data.getSaveMap().containsKey(Persistance.VAR_TOTAL_GEAR))
					playerLevel += (Integer) data.getSaveMap().get(
							Persistance.VAR_TOTAL_GEAR);
			}

			view.setWidgetText(TEXT_PLAYER_DIFF, getPlayerText(playerPower));
			view
					.setWidgetText(TEXT_PLAYER_LEVEL, Integer
							.toString(playerPower));
			view.setWidgetText(TEXT_MONSTER_LEVEL, Integer
					.toString(monsterPower));
			view.setListener(LISTENER_UP_PLAYER, new Listener() {

				public void onAction() {
					try {
						view.setWidgetText(TEXT_PLAYER_DIFF,
								getPlayerText(++playerPower));
						view.setWidgetText(TEXT_PLAYER_LEVEL, Integer
								.toString(playerPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_DOWN_PLAYER, new Listener() {

				public void onAction() {
					try {
						view.setWidgetText(TEXT_PLAYER_DIFF,
								getPlayerText(--playerPower));
						view.setWidgetText(TEXT_PLAYER_LEVEL, Integer
								.toString(playerPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_DOWN_MONSTER, new Listener() {

				public void onAction() {
					try {
						view.setWidgetText(TEXT_MONSTER_LEVEL, Integer
								.toString(--monsterPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_UP_MONSTER, new Listener() {

				public void onAction() {
					try {
						view.setWidgetText(TEXT_MONSTER_LEVEL, Integer
								.toString(++monsterPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_MONSTER_RESET, new Listener() {

				public void onAction() {
					try {
						monsterPower = 1;
						view.setWidgetText(TEXT_MONSTER_LEVEL, Integer
								.toString(monsterPower));
					} catch (WidgetError ex) {
						ex.printStackTrace();
					}
				}
			});
			view.setListener(LISTENER_PLAYER_BASE, new Listener() {

				public void onAction() {
					playerPower = playerLevel;
					try {
						view.setWidgetText(TEXT_PLAYER_DIFF,
								getPlayerText(playerPower));
						view.setWidgetText(TEXT_PLAYER_LEVEL, Integer
								.toString(playerPower));
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
		HashMap<String, Object> saveInfo = new HashMap<String, Object>();
		saveInfo.put(Persistance.VAR_PLAYER_POWER_LAST, playerPower);
		saveInfo.put(Persistance.VAR_MONSTER_LAST, monsterPower);
		data.saveMap(saveInfo);
	}

}

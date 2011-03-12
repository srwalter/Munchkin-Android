package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.view.SummaryView;

public class SummaryViewMock implements SummaryView<Void> {

	public Listener<Void> add;
	public Listener<Void> down;
	public Listener<Void> edit;
	public Listener<Void> up;
	public Listener<Void> fight;
	public Listener<GearItemData> dataHandle;

	public Listener<GearItemData> newHandle;

	public String fightScore;
	public String gearScore;
	public String levelScore;

	public boolean upEnabled = false;
	public boolean downEnabled = false;

	public boolean gearShown = false;
	public boolean fightShown = false;
	public boolean addShown = false;

	@Override
	public void setDownLevel(Listener<Void> downReturn) {
		down = downReturn;

	}

	@Override
	public void setEditGear(Listener<Void> editReturn) {
		edit = editReturn;

	}

	@Override
	public void setFightAction(Listener<Void> fightReturn) {
		fight = fightReturn;

	}

	@Override
	public void setFightScore(String inText) {
		fightScore = inText;

	}

	@Override
	public void setGearText(String inText) {
		gearScore = inText;

	}

	@Override
	public void setLevelText(String inText) {
		levelScore = inText;

	}

	@Override
	public void setUpLevel(Listener<Void> upReturn) {
		up = upReturn;

	}

	@Override
	public Void getHandle() {
		return null;
	}

	@Override
	public void showGearEdit() {
		gearShown = true;

	}

	@Override
	public void showFightScreen() {
		fightShown = true;

	}

	@Override
	public void showAdd() {
		addShown = true;
	}

	@Override
	public void setAddHandle(Listener<Void> buttonReturn,
			Listener<GearItemData> dataHandle) {
		add = buttonReturn;
		this.dataHandle = dataHandle;

	}

	@Override
	public void setDownLevelEnabled(boolean inEnabled) {
		downEnabled = inEnabled;
	}

	@Override
	public void setUpLevelEnabled(boolean inEnabled) {
		upEnabled = inEnabled;
	}

}

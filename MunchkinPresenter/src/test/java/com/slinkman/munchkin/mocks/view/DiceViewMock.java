package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.presenter.DicePresenter.DiceView;

public class DiceViewMock implements DiceView {
	public Listener<Void> rollHandle;
	public int diceValue=0;
	
	@Override
	public void setRoll(Listener<Void> handle) {
		rollHandle = handle;
	}

	@Override
	public void setDice(int resource) {
		diceValue = resource;
	}
}

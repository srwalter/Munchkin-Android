package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.view.DiceView;


public class DiceViewMock implements DiceView<Void>{
	public Listener<Void> rollHandle;
	public int diceValue = 0;
	public int cache = 0;
	@Override
	public void setRoll(final Listener<Void> handle) {
		rollHandle = new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				handle.onAction(null);
				cache++;
			}
		};
		
	}
	@Override
	public void setDice(int resource) {
		diceValue = resource;
		
	}
	@Override
	public Void getHandle() {
		return null;
	}

	
}

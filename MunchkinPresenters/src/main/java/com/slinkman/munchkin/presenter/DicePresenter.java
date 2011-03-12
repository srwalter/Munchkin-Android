package com.slinkman.munchkin.presenter;

import java.util.Random;

import com.google.inject.Inject;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.DiceView;

public class DicePresenter implements Presenter{

	DiceView<?> view;

	Persistance data;
	
	int currentValue = 0;
	
	@Inject
	DicePresenter(DiceView<?> inView, Persistance inData) {
		view = inView;
		data = inData;

	}
	
	@Override
	public void bind() {
		data.getInt(Persistance.VAR_DICE_LAST, new Listener<Integer>() {
			
			@Override
			public void onAction(Integer inObject) {
				if (inObject == -1)
					currentValue = 1;
				else
					currentValue = inObject;
				view.setDice(currentValue);
			}
		});

		view.setRoll(new Listener<Void>() {
			public void onAction(Void in) {
				Random mRand = new Random(System.currentTimeMillis());
				view.setDice(mRand.nextInt(6) + 1);
			}
		});
		
	}

	@Override
	public void onPause() {
		data.setVariable(Persistance.VAR_DICE_LAST, currentValue);
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> U getAppHandle() {
		return (U)view.getHandle();
	}

}

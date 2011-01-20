package com.slinkman.munchkin.presenter;

import java.util.Random;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;

public class DicePresenter implements Presenter {

	public interface DiceView {
		public void setRoll(Listener<Void> handle);

		public void setDice(int resource);
	};

	private DiceView view;

	private Persistance data;

	private int currentValue = 0;

	public DicePresenter(DiceView inView, Persistance inData) {
		view = inView;
		data = inData;

		data.getInt(Persistance.VAR_DICE_LAST, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				currentValue = inObject;
				init();
			}
		});
	}

	private int init() {
		view.setRoll(new Listener<Void>() {
			public void onAction(Void in) {
				Random mRand = new Random(System.currentTimeMillis());
				view.setDice(mRand.nextInt(6) + 1);
			}
		});
		return 0;
	}

	public void onPause() {
		data.setVariable(Persistance.VAR_DICE_LAST, currentValue);
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

}

package com.slinkman.munchkin.presenter;

import java.util.HashMap;
import java.util.Random;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.error.WidgetError;

public class DicePresenter implements Presenter {

	// Listeners
	public final static int LISTENER_ROLL_CLICK = 0x01;

	// ResourceTarget
	public final static int RESOURCE_TARGET_DICE = 0x01;

	// Resources
	public final static int RESOURCE_ONE = 0x01;
	public final static int RESOURCE_TWO = 0x02;
	public final static int RESOURCE_THREE = 0x03;
	public final static int RESOURCE_FOUR = 0x04;
	public final static int RESOURCE_FIVE = 0x05;
	public final static int RESOURCE_SIX = 0x06;

	public interface DiceView {
		public void setListener(int objectID, Listener<Void> inListener) throws WidgetError;
		public void setWidgetResource(int objectID, int widgetState) throws WidgetError;
	};

	private DiceView view;

	private Persistance data;

	private int currentValue=1;

	public DicePresenter(DiceView inView, Persistance inData) {
		view = inView;
		data = inData;

		// Persistance Check
		if (inData.getSaveMap().containsKey(Persistance.VAR_DICE_LAST))
			currentValue = (Integer) inData.getSaveMap().get(
					Persistance.VAR_DICE_LAST);
		// Check for invalid resource
		if (getResourceForValue(currentValue) == 0)
			currentValue = 1;

		try {
			init();
		} catch (WidgetError e) {
			e.printStackTrace();
		}
	}

	private int init() throws WidgetError {
		view.setWidgetResource(RESOURCE_TARGET_DICE,
				getResourceForValue(currentValue));
		view.setListener(LISTENER_ROLL_CLICK, new Listener<Void>() {

			public void onAction(Void in) {
				Random mRand = new Random(System.currentTimeMillis());
				try {
					view.setWidgetResource(RESOURCE_TARGET_DICE, mRand
							.nextInt(6) + 1);
				} catch (WidgetError ex) {
					ex.printStackTrace();
				}
			}
		});
		return 0;
	}

	private int getResourceForValue(int inValue) {
		switch (inValue) {
		case 1:
			return RESOURCE_ONE;
		case 2:
			return RESOURCE_TWO;
		case 3:
			return RESOURCE_THREE;
		case 4:
			return RESOURCE_FOUR;
		case 5:
			return RESOURCE_FIVE;
		case 6:
			return RESOURCE_SIX;
		default:
			return 0;
		}
	}

	public void onPause() {
		HashMap<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put(Persistance.VAR_DICE_LAST, currentValue);
		data.saveMap(saveMap);
	}

}

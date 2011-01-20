package com.slinkman.munchkin.presenter;


import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;

public class MaxLevelDialogPresenter {

	// Enabled
	public static final int ENABLE_DOWN = 0x01;

	// Listener
	public static final int LISTENER_UP = 0x01;
	public static final int LISTENER_DOWN = 0x02;
	public static final int LISTENER_DONE = 0x03;

	// Text
	public static final int TEXT_TOP_LEVEL = 0x01;

	public interface MaxLevelDialogViewInterface {
		public void setUp(Listener<Void> handle);

		public void setDown(Listener<Void> handle);

		public void setDone(Listener<Void> handle);

		public void setListener(int objectID, Listener<Void> inListener);

		public void setTopText(String handle);

	};

	private MaxLevelDialogViewInterface view;
	private Persistance data;
	private int currentLevel = 10;

	public MaxLevelDialogPresenter(MaxLevelDialogViewInterface inView,
			Persistance inData, final Listener<Integer> inListener) {
		view = inView;
		data = inData;
		data.getInt(Persistance.VAR_TOPLEVEL, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				currentLevel = inObject;
			}
		});
		view.setTopText(Integer.toString(currentLevel));
		view.setListener(LISTENER_DONE, new Listener<Void>() {

			public void onAction(Void in) {
				inListener.onAction(currentLevel);
			}
		});
		view.setListener(LISTENER_UP, new Listener<Void>() {

			public void onAction(Void in) {
				view.setTopText(Integer.toString(++currentLevel));
			}
		});
		view.setListener(LISTENER_DOWN, new Listener<Void>() {

			public void onAction(Void in) {
				view.setTopText(Integer.toString(--currentLevel));
			}
		});
	}

	public void onPause() {
		data.setVariable(Persistance.VAR_TOPLEVEL, currentLevel);
	}
}

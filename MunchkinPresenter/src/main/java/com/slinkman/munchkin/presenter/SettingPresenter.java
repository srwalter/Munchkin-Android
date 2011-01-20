package com.slinkman.munchkin.presenter;


import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;

public class SettingPresenter implements Presenter {

	// Text
	public final static int TEXT_LEVEL_LIMIT = 0x01;

	// Listener
	public final static int LISTENER_MAX_ITEM = 0x01;

	// Return Listener
	public final static int RETURN_CHANGE_DIALOG = 0x01;

	public interface SettingView {
		public void setDialog(Listener<Integer> handle);
		
		public void setMaxText(String inString);
	};

	SettingView view;
	Persistance data;

	int topLevel = 10;

	public SettingPresenter(SettingView inView, Persistance inData) {
		view = inView;
		data = inData;
		data.getInt(Persistance.VAR_TOPLEVEL, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				topLevel = inObject;
				
			}
		});
		view.setMaxText(Integer.toString(topLevel));
		view.setDialog(new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				view.setMaxText(Integer.toString(topLevel));
			}
		});
	}

	@Override
	public void onPause() {
		data.setVariable(Persistance.VAR_TOPLEVEL, topLevel);
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}
}

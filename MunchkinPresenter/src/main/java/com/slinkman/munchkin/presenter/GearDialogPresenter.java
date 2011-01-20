package com.slinkman.munchkin.presenter;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.model.GearItemData;

public class GearDialogPresenter implements Presenter{

	public interface GearDialogViewInterface {
		public void setAddListner(Listener<GearItemData> handle);
		public void setCancelListener(Listener<Void> handle);
		public void setArmorText(String inText);
		public void setBonusText(Integer inValue);
	};
	
	
	private GearDialogViewInterface view;

	public GearDialogPresenter(GearDialogViewInterface inView,
			Persistance inData,GearItemData inItem, Listener<GearItemData> inListener) {
		view = inView;
		view.setAddListner(inListener);
		view.setCancelListener(new Listener<Void>(){
			@Override
			public void onAction(Void inObject) {
				//TODO: Clean up Presenter for dismiss
				
			}
		});
		
		if (inItem != null){
			view.setArmorText(inItem.getArmorType());
			view.setBonusText(inItem.getBonus());
		}
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}

}

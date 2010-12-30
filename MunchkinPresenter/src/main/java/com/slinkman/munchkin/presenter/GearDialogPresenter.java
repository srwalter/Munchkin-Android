package com.slinkman.munchkin.presenter;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.error.WidgetError;

public class GearDialogPresenter implements Presenter{

	// Return Listner
	public static final int RETURN_LISTENER_ARMORTYPE = 0x01;
	public static final int RETURN_LISTENER_BONUS = 0x02;

	// Text
	public static final int TEXT_BONUS = 0x01;
	public static final int TEXT_ARMOR = 0x02;

	// Listener
	public static final int LISTENER_ADD = 0x01;
	public static final int LISTENER_CANCEL = 0x02;

	// Object IDs
	public static final int OBJECT_BONUS = 0x00;
	public static final int OBJECT_ARMOR = 0x01;

	public interface GearDialogViewInterface {
		public void setListener(int objectID, Listener<Void> inListener) throws WidgetError;
		public void setWidgetText(int objectID, String inText) throws WidgetError;
		public void setStringReturnListener(int objectID, Listener<String> inListener) throws WidgetError;
		public void setIntegerReturnListener(int objectID, Listener<Integer> inListener) throws WidgetError;
	};
	
	
	private GearDialogViewInterface view;
	private int currentBonus = 0;
	private String armorType = "";

	public GearDialogPresenter(GearDialogViewInterface inView,
			Persistance inData, final Listener<Object[]> inListener) {
		view = inView;

		try {
			view.setListener(LISTENER_ADD, new Listener<Void>() {

				public void onAction(Void in) {
					inListener
							.onAction(new Object[] { currentBonus, armorType });
				}
			});
			view.setIntegerReturnListener(RETURN_LISTENER_BONUS,
					new Listener<Integer>() {
						public void onAction(Integer inObject) {
							currentBonus = inObject;
						}
					});
			view.setStringReturnListener(RETURN_LISTENER_ARMORTYPE,
					new Listener<String>() {
						public void onAction(String inObject) {
							armorType = inObject;
						}
					});
			view.setListener(LISTENER_CANCEL, new Listener<Void>() {

				@Override
				public void onAction(Void in) {
					// TODO Auto-generated method stub
				}
			});

		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	public void setArmor(String inText) {
		try {
			view.setWidgetText(TEXT_ARMOR, inText);
		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	public void setBonus(Integer inValue) {
		try {
			view.setWidgetText(TEXT_BONUS, Integer.toString(inValue));
		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

}

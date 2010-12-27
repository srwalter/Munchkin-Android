package com.slinkman.munchkin.presenter;

import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.ListenerInterface;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.ReturnListener;
import com.slinkman.munchkin.baseinterface.ReturnListenerInterface;
import com.slinkman.munchkin.baseinterface.TextInterface;
import com.slinkman.munchkin.error.WidgetError;

public class GearDialogPresenter {

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

	public interface GearDialogViewInterface extends ListenerInterface,
			ReturnListenerInterface, TextInterface {
	};

	private GearDialogViewInterface view;
	private Persistance data;
	private int currentBonus = 0;
	private String armorType = "";

	public GearDialogPresenter(GearDialogViewInterface inView,
			Persistance inData, final ReturnListener inListener) {
		view = inView;
		data = inData;

		try {
			view.setListener(LISTENER_ADD, new Listener() {

				public void onAction() {
					inListener.onAction(ReturnListener.VAR_OBJECT_ARRAY,
							new Object[] { currentBonus, armorType });
				}
			});
			view.setListener(RETURN_LISTENER_BONUS, new ReturnListener() {

				public void onAction(int idType, Object inObject) {
					if (idType == ReturnListener.VAR_INTEGER)
						currentBonus = (Integer) inObject;
				}
			});
			view.setListener(RETURN_LISTENER_ARMORTYPE, new ReturnListener() {

				public void onAction(int idType, Object inObject) {
					if (idType == ReturnListener.VAR_STRING)
						armorType = (String) inObject;
				}
			});
			view.setListener(LISTENER_CANCEL, new Listener() {

				@Override
				public void onAction() {
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

}

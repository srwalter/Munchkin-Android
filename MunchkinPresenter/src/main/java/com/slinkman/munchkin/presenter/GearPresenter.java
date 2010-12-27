package com.slinkman.munchkin.presenter;

import java.util.ArrayList;
import java.util.HashMap;

import com.slinkman.munchkin.baseinterface.CastInterface;
import com.slinkman.munchkin.baseinterface.ListInterface;
import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.ListenerInterface;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.baseinterface.ReturnListener;
import com.slinkman.munchkin.baseinterface.ReturnListenerInterface;
import com.slinkman.munchkin.baseinterface.TextInterface;
import com.slinkman.munchkin.error.WidgetError;

public class GearPresenter implements Presenter {

	// List Text
	public final static int LIST_TEXT_BONUS = 0x01;
	public final static int LIST_TEXT_ARMOR_TYPE = 0x02;

	// List Listener
	public final static int LIST_LISTENER_DELETE = 0x01;
	public final static int LIST_LISTENER_EDIT = 0x02;

	// Listener
	public final static int LISTENER_NEW_GEAR = 0x01;
	public final static int LISTENER_CLEAR_GEAR = 0x02;

	// Return Listener
	public final static int RETURN_LISTENER_NEW_GEAR = 0x01;
	public final static int RETURN_LISTENER_GEAR_ITEM = 0x02;

	// Object Return Array
	public final static int OBJECT_BONUS = 0x00;
	public final static int OBJECT_STRING = 0x01;
	public final static int OBJECT_LOCATION = 0x02;

	// Data Cast
	public final static int DATA_CAST_BONUS_ARRAY = 0x01;
	public final static int DATA_CAST_ARMOR_ARRAY = 0x02;

	public interface GearView extends ReturnListenerInterface,
			ListenerInterface, ListInterface {
	};

	public interface GearData extends Persistance, CastInterface {
	};

	public interface GearItemView extends ListenerInterface, TextInterface {
	};

	private GearView view;
	private GearData data;
	private int listCount = 0;

	ArrayList<String> listArmor;
	ArrayList<Integer> listBonus;

	@SuppressWarnings("unchecked")
	public GearPresenter(GearView inView, GearData inData) {
		view = inView;
		data = inData;

		try {
		listArmor = (ArrayList<String>) data.getItem(DATA_CAST_ARMOR_ARRAY,
				null);
		if (listArmor == null)
			listArmor = new ArrayList<String>();

		listBonus = (ArrayList<Integer>) data.getItem(DATA_CAST_BONUS_ARRAY,
				null);
		if (listBonus == null)
			listBonus = new ArrayList<Integer>();
		if (listArmor.size() >0)
			listCount = listArmor.size();
			view.setPopulator(new GearPopulator());
			view.setListener(LISTENER_NEW_GEAR, new NewGearListener());
			view.setListener(LISTENER_CLEAR_GEAR, new ClearGearListener());
			if (listCount > 0)
				view.refreshList().onAction(ReturnListener.VAR_INTEGER,
						(Integer) listCount);
		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		try{
		data.setItem(DATA_CAST_ARMOR_ARRAY,
				new Object[] { listArmor, listBonus });
		} catch (WidgetError ex)
		{
			ex.printStackTrace();
			return;
		}
		int total = 0;
		for(Integer c: listBonus)
			total += c;
		HashMap<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put(Persistance.VAR_TOTAL_GEAR, total);
		data.saveMap(saveMap);
	}

	private class ClearGearListener implements Listener {

		public void onAction() {
			listArmor.clear();
			listBonus.clear();
			view.refreshList().onAction(ReturnListener.VAR_INTEGER,
					listArmor.size());
		}
	}

	private class NewGearListener implements Listener {

		public void onAction() {
			try {
				view.setListener(RETURN_LISTENER_NEW_GEAR, new ReturnNewGear());
			} catch (WidgetError ex) {
				ex.printStackTrace();
			}
		}
	}

	private class ReturnNewGear implements ReturnListener {

		@Override
		public void onAction(int idType, Object inObject) {
			if (idType == ReturnListener.VAR_OBJECT_ARRAY) {
				Object[] tempArray = (Object[]) inObject;
				listArmor.add((String) tempArray[OBJECT_STRING]);
				listBonus.add((Integer) tempArray[OBJECT_BONUS]);
				view.refreshList().onAction(ReturnListener.VAR_INTEGER,
						listArmor.size());
			}
		}
	}

	private class EditGear implements Listener {

		private int myID;

		public EditGear(int id) {
			myID = id;
		}

		public void onAction() {
			try {
				HashMap<String, Object> saveMap = new HashMap<String, Object>();
				saveMap.put(Persistance.VAR_ITEM_EDIT_ARMOR, listArmor
						.get(myID));
				saveMap.put(Persistance.VAR_ITEM_EDIT_BONUS, listBonus
						.get(myID));
				data.saveMap(saveMap);
				view.setListener(RETURN_LISTENER_GEAR_ITEM, new ReturnEditGear(
						myID));
			} catch (WidgetError ex) {
				ex.printStackTrace();
			}
		}
	}

	private class ReturnEditGear implements ReturnListener {
		private int location;

		public ReturnEditGear(int id) {
			location = id;
		}

		public void onAction(int idType, Object inObject) {
			if (idType == ReturnListener.VAR_OBJECT_ARRAY) {
				Object[] tempArray = (Object[]) inObject;
				listArmor.remove(location);
				listBonus.remove(location);
				listArmor.add(location, (String) tempArray[OBJECT_STRING]);
				listBonus.add(location, (Integer) tempArray[OBJECT_BONUS]);
				view.refreshList().onAction(ReturnListener.VAR_INTEGER,
						listArmor.size());
			}
		}
	}

	private class GearPopulator implements ReturnListener {

		public void onAction(int id, Object inObject) {
			try {
				GearItemView itemView = (GearItemView) inObject;
				itemView.setListener(LIST_LISTENER_DELETE, new DeleteGear(
						id));
				itemView.setListener(LIST_LISTENER_EDIT, new EditGear(id));
				itemView.setWidgetText(LIST_TEXT_ARMOR_TYPE, listArmor.get(id));
				itemView.setWidgetText(LIST_TEXT_BONUS, Integer
						.toString(listBonus.get(id)));
			} catch (WidgetError ex) {
				ex.printStackTrace();
			}
		}
	}

	private class DeleteGear implements Listener {
		private int listID;

		public DeleteGear(int inListID) {
			listID = inListID;
		}

		public void onAction() {
			listArmor.remove(listID);
			listBonus.remove(listID);
			view.refreshList().onAction(ReturnListener.VAR_INTEGER,
					listArmor.size());
		}

	}

}

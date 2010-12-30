package com.slinkman.munchkin.presenter;

import java.util.ArrayList;
import java.util.HashMap;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.ParameterListener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
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

	public interface GearView {
		public void setReturnListener(int objectID, Listener<Object[]> inListener) throws WidgetError;
		public void setListener(int objectID, Listener<Void> inListener) throws WidgetError;
		//List Interface
		public Listener<Integer> refreshList();
		public void setPopulator (ParameterListener<GearItemView> inListener);
	};

	public interface GearData {
		// Persistence
		public void saveMap(HashMap<String, Object> saveMap);
		public HashMap<String, Object> getSaveMap();
		
		// Cast Interface
		public Object getItem(int itemID, Object[] parms) throws WidgetError;
		public void setItem(int itemID, Object inObject) throws WidgetError;
	};

	public interface GearItemView {
		public void setWidgetText(int objectID, String inText) throws WidgetError;
		public void setListener(int objectID, Listener<Void> inListener) throws WidgetError;
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

			listBonus = (ArrayList<Integer>) data.getItem(
					DATA_CAST_BONUS_ARRAY, null);
			if (listBonus == null)
				listBonus = new ArrayList<Integer>();
			if (listArmor.size() > 0)
				listCount = listArmor.size();
			view.setPopulator(new GearPopulator());
			view.setListener(LISTENER_NEW_GEAR, new NewGearListener());
			view.setListener(LISTENER_CLEAR_GEAR, new ClearGearListener());
			if (listCount > 0)
				view.refreshList().onAction(listCount);
		} catch (WidgetError ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		try {
			data.setItem(DATA_CAST_ARMOR_ARRAY, new Object[] { listArmor,
					listBonus });
		} catch (WidgetError ex) {
			ex.printStackTrace();
			return;
		}
		int total = 0;
		for (Integer c : listBonus)
			total += c;
		HashMap<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put(Persistance.VAR_TOTAL_GEAR, total);
		data.saveMap(saveMap);
	}

	private class ClearGearListener implements Listener<Void> {

		public void onAction(Void in) {
			listArmor.clear();
			listBonus.clear();
			view.refreshList().onAction(listArmor.size());
		}
	}

	private class NewGearListener implements Listener<Void> {

		public void onAction(Void in) {
			try {
				view.setReturnListener(RETURN_LISTENER_NEW_GEAR, new ReturnNewGear());
			} catch (WidgetError ex) {
				ex.printStackTrace();
			}
		}
	}

	private class ReturnNewGear implements Listener<Object[]> {

		@Override
		public void onAction(Object[] inObject) {
			Object[] tempArray = (Object[]) inObject;
			listArmor.add((String) tempArray[OBJECT_STRING]);
			listBonus.add((Integer) tempArray[OBJECT_BONUS]);
			view.refreshList().onAction(listArmor.size());
		}
	}

	private class EditGear implements Listener<Void> {

		private int myID;

		public EditGear(int id) {
			myID = id;
		}

		public void onAction(Void in) {
			try {
				HashMap<String, Object> saveMap = new HashMap<String, Object>();
				saveMap.put(Persistance.VAR_ITEM_EDIT_ARMOR,
						listArmor.get(myID));
				saveMap.put(Persistance.VAR_ITEM_EDIT_BONUS,
						listBonus.get(myID));
				data.saveMap(saveMap);
				view.setReturnListener(RETURN_LISTENER_GEAR_ITEM, new ReturnEditGear(
						myID));
			} catch (WidgetError ex) {
				ex.printStackTrace();
			}
		}
	}

	private class ReturnEditGear implements Listener<Object[]> {
		private int location;

		public ReturnEditGear(int id) {
			location = id;
		}

		public void onAction(Object[] inObject) {
			Object[] tempArray = (Object[]) inObject;
			listArmor.remove(location);
			listBonus.remove(location);
			listArmor.add(location, (String) tempArray[OBJECT_STRING]);
			listBonus.add(location, (Integer) tempArray[OBJECT_BONUS]);
			view.refreshList().onAction(listArmor.size());
		}
	}

	private class GearPopulator implements ParameterListener<GearItemView> {

		public void onAction(int id, GearItemView inObject) {
			try {
				inObject.setListener(LIST_LISTENER_DELETE, new DeleteGear(id));
				inObject.setListener(LIST_LISTENER_EDIT, new EditGear(id));
				inObject.setWidgetText(LIST_TEXT_ARMOR_TYPE, listArmor.get(id));
				inObject.setWidgetText(LIST_TEXT_BONUS,
						Integer.toString(listBonus.get(id)));
			} catch (WidgetError ex) {
				ex.printStackTrace();
			}
		}
	}

	private class DeleteGear implements Listener<Void> {
		private int listID;

		public DeleteGear(int inListID) {
			listID = inListID;
		}

		public void onAction(Void in) {
			listArmor.remove(listID);
			listBonus.remove(listID);
			view.refreshList().onAction(listArmor.size());
		}

	}

}

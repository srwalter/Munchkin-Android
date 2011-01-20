package com.slinkman.munchkin.presenter;

import java.util.HashMap;
import java.util.Map;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.ParameterListener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.model.GearItemData;

/**
 * Presenter to control the view and data needed for the Gear Presenter.
 * 
 * @author SlinkKay
 * 
 */
public class GearPresenter implements Presenter {

	public interface GearView {
		/**
		 * Show the edit gear screen with gearData if not null. Otherwise
		 * display empty screen. When item is done call the handle to return the
		 * object to the presenter.
		 * 
		 * @param gearData
		 *            If not null the information will populate the window.
		 * @param handle
		 *            Handle to return the information to the presenter @ Thrown
		 *            when targeted peice of the view doesn't exist
		 */
		public void displayEditGear(GearItemData gearData,
				Listener<GearItemData> handle);

		/**
		 * Set the view handle for the action to clear the gear of a player
		 * 
		 * @param handle
		 *            Handle to inform the presenter that action must be taken
		 */
		public void setClearGear(Listener<Void> handle);

		/**
		 * Set the view handle for the action to add a new gear to the player
		 * 
		 * @param handle
		 *            Handle to inform the presenter that action must be taken
		 */
		public void setAddGear(Listener<GearItemData> handle);

		/**
		 * Get a handle to the view to refresh the gear list
		 * 
		 * @return View handle to allow the presenter to update when the data
		 *         has changed
		 */
		public Listener<Integer> getRefreshList();

		/**
		 * 
		 * @param inListener
		 */
		public void setPopulator(ParameterListener<GearItemView> inListener);

	};

	/**
	 * @author SlinkKay
	 * 
	 */
	public interface GearData extends Persistance {

		/**
		 * Method is used to get the id's of all items in the database
		 * 
		 * @param callback
		 *            Called when the total is ready
		 */
		public void getGearIds(Listener<Integer[]> callback);

		/**
		 * Method is used to retrieve the armor of an item with the specified id
		 * 
		 * @param id
		 *            ID of the item requested
		 * @param callback
		 *            Called when the variable is ready to be returned
		 */
		public void getArmorType(int id, Listener<String> callback);

		/**
		 * Method is used to retrieve the bonus of an item with the specified id
		 * 
		 * @param id
		 *            Id of the item requested
		 * @param callback
		 *            Callback item used when information is ready to be
		 *            returned
		 */
		public void getBonus(int id, Listener<Integer> callback);

		/**
		 * Method to add a new gear item to the data backend. The listener will
		 * be called once the item is placed and has id to associate with the
		 * new values
		 * 
		 * @param armorType
		 * @param bonusAmount
		 * @param callback
		 */
		public void addGearItem(GearItemData inItem, Listener<Integer> callback);
	};

	public interface GearItemView {
		/**
		 * Set the bonus text in the view
		 * 
		 * @param handle
		 */
		public void setBonusText(String inString);

		/**
		 * Set the armor text in a view
		 * 
		 * @param handle
		 */
		public void setArmorText(String inString);

		/**
		 * Set the action to edit the armor listener
		 * 
		 * @param handle
		 */
		public void setEditListner(Listener<Void> handle);

		/**
		 * Set the action to delete the gear
		 * 
		 * @param handle
		 */
		public void setDeleteListener(Listener<Void> handle);
	};

	private GearView view;
	private GearData data;
	private Integer[] idList;

	Map<Integer, String> mapArmor;
	Map<Integer, Integer> mapBonus;

	public GearPresenter(GearView inView, GearData inData) {
		view = inView;
		data = inData;
		data.getGearIds(new Listener<Integer[]>() {
			@Override
			public void onAction(Integer[] inObject) {
				if (mapArmor == null)
					mapArmor = new HashMap<Integer, String>();

				if (mapBonus == null)
					mapBonus = new HashMap<Integer, Integer>();

				for (Integer i : inObject) {
					data.getArmorType(i, new AsyncGear<String>(i) {
						public void onAction(String inObject) {
							mapArmor.put(this.id, inObject);
						}
					});
					data.getBonus(i, new AsyncGear<Integer>(i) {
						public void onAction(Integer inObject) {
							mapBonus.put(this.id, inObject);
						}
					});
				}
				idList = inObject;
				testPopulator();
			}
		});
		view.setAddGear(new ReturnGear());
		view.setClearGear(new ClearGearListener());
	}

	private abstract class AsyncGear<T> implements Listener<T> {
		int id;

		public AsyncGear(int inId) {
			id = inId;
		}

		@Override
		public abstract void onAction(T inObject);
	}

	private void testPopulator() {
		view.setPopulator(new GearPopulator());
		if (idList.length > 0)
			view.getRefreshList().onAction(idList.length);
	}

	@Override
	public void onPause() {
		int total = 0;
		for (Integer c : mapBonus.keySet())
			total += mapBonus.get(c);
		data.setVariable(Persistance.VAR_TOTAL_GEAR, total);
	}

	private class ClearGearListener implements Listener<Void> {

		public void onAction(Void in) {
			if (mapArmor != null)
				mapArmor.clear();
			if (mapBonus != null)
				mapBonus.clear();
			view.getRefreshList().onAction(0);
		}
	}

	private class ReturnGear implements Listener<GearItemData> {

		@Override
		public void onAction(GearItemData inItem) {
			// TODO: Check to see if the gearID already exists. If it does
			// it's an edit. Otherwise it's an add
			data.addGearItem(inItem, new GearAddReturn());
			data.getGearIds(new Listener<Integer[]>() {
				@Override
				public void onAction(Integer[] inObject) {
					view.getRefreshList().onAction(inObject.length);
				}
			});
		}
	}

	private class GearAddReturn implements Listener<Integer> {
		@Override
		public void onAction(Integer inObject) {

		}
	}

	private class EditGear implements Listener<Void> {

		private int myID;

		public EditGear(int id) {
			myID = id;
		}

		public void onAction(Void in) {
			GearItemData gearItem = new GearItemData();
			gearItem.setArmorType(mapArmor.get(myID));
			gearItem.setBonus(mapBonus.get(myID));
			view.displayEditGear(gearItem, new ReturnGear());
		}
	}

	private class GearPopulator implements ParameterListener<GearItemView> {

		public void onAction(int id, GearItemView inObject) {
			inObject.setDeleteListener(new DeleteGear(id));
			inObject.setEditListner(new EditGear(id));
			inObject.setArmorText(mapArmor.get(id));
			inObject.setBonusText(Integer.toString(mapBonus.get(id)));
		}
	}

	private class DeleteGear implements Listener<Void> {
		private int listID;

		public DeleteGear(int inListID) {
			listID = inListID;
		}

		public void onAction(Void in) {
			mapArmor.remove(listID);
			mapBonus.remove(listID);
			view.getRefreshList().onAction(mapArmor.size());
		}

	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

}

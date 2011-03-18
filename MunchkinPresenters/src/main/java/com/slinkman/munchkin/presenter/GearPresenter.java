package com.slinkman.munchkin.presenter;

import com.google.inject.Inject;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Populator;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.GearData;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.view.GearItemView;
import com.slinkman.munchkin.apis.view.GearView;

public class GearPresenter implements Presenter {

	GearView<?> view;
	GearData data;
	Integer[] gearIDs;

	@Inject
	GearPresenter(GearView<?> inView, GearData inData) {
		view = inView;
		data = inData;
	}

	@Override
	public void bind() {
		view.setClearGear(new Listener<Void>() {
			public void onAction(Void inObject) {
				data.flushData(new Listener<Void>() {
					@Override
					public void onAction(Void inObject) {
						refreshList();
					}
				});
			}
		});
		view.setPopulator(new GearPopulator());
		view.setDisplayHandle(new DisplayHandle());
		refreshList();
	}

	private void refreshList() {
		data.getGearIds(new Listener<Integer[]>() {

			@Override
			public void onAction(Integer[] inObject) {
				gearIDs = inObject;
				view.getRefreshList().onAction(gearIDs.length);
			}
		});
	}

	private void setArmor(String armorText, GearListItem inView) {
		inView.viewHandle.setArmorText(armorText);
	}

	private void setBonus(int bonusAmount, GearListItem inView) {
		inView.viewHandle.setBonusText(Integer.toString(bonusAmount));
	}

	@Override
	public void onPause() {
		data.onDestroy();

	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

	private void setupItem(final GearListItem inItem) {
		data.getArmorType(inItem.id, new Listener<String>() {
			@Override
			public void onAction(String inObject) {
				setArmor(inObject, inItem);
			}
		});

		data.getBonus(inItem.id, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				setBonus(inObject, inItem);
			}
		});

		inItem.viewHandle.setDeleteListener(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				data.deleteGearItem(inItem.id, new Listener<Void>() {
					public void onAction(Void inObject) {
						refreshList();
					};
				});

			}
		});

		inItem.viewHandle.setEditListner(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				new DialogPasser(inItem.id);
			}
		});
	}

	class DialogPasser {
		String armorText=null;
		String bonusText=null;
		int id;

		public DialogPasser(int inId) {
			this.id = inId;
			System.out.println("Pulling information for ID: " + inId);
			data.getArmorType(id, new Listener<String>() {
				@Override
				public void onAction(String inObject) {
					armorText = inObject;
					if (bonusText != null)
						view.displayGearWindow(id, bonusText, armorText);
				}
			});
			data.getBonus(id, new Listener<Integer>() {
				@Override
				public void onAction(Integer inObject) {
					bonusText = Integer.toString(inObject);
					if (armorText != null)
						view.displayGearWindow(id, bonusText, armorText);
				}
			});
		}
	}

	class GearPopulator implements Listener<Populator<GearItemView<?>>> {

		@Override
		public void onAction(final Populator<GearItemView<?>> popObject) {
			final GearListItem gearItem = new GearListItem();
			gearItem.id = gearIDs[popObject.getID()];
			gearItem.viewHandle = popObject.getView();
			setupItem(gearItem);
		}

	}

	class DisplayHandle implements Listener<GearItemData> {

		@Override
		public void onAction(GearItemData inObject) {
			if (inObject != null)
				data.addGearItem(inObject, new Listener<Void>() {
					@Override
					public void onAction(Void inObject) {
						refreshList();
					}
				});
		}

	}

	class GearListItem {

		int id;
		GearItemView<?> viewHandle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> U getAppHandle() {
		return (U) view.getHandle();
	}

}

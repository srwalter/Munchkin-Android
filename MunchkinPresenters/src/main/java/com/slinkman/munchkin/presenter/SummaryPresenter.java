package com.slinkman.munchkin.presenter;

import com.google.inject.Inject;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.GearData;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.SummaryView;

public class SummaryPresenter implements Presenter {

	SummaryView<?> view;
	GearData data;
	int curLevel;
	int curGear;
	int topLevel;

	@Inject
	SummaryPresenter(SummaryView<?> inView, GearData inData) {
		view = inView;
		data = inData;
		curLevel = 1;
		curGear = 0;
		topLevel = 10;
	}

	@Override
	public void bind() {

		data.getInt(Persistance.VAR_PLAYER_LEVEL_LAST, new Listener<Integer>() {
			@Override
			public void onAction(Integer inObject) {
				if (inObject == -1)
					return;
				curLevel = inObject;
				view.setLevelText(Integer.toString(curLevel));
				if (topLevel == curLevel)
					view.setUpLevelEnabled(false);
				else if (curLevel == 1)
					view.setDownLevelEnabled(false);
			}
		});
		data.getInt(Persistance.VAR_TOTAL_GEAR, new Listener<Integer>() {

			@Override
			public void onAction(Integer inObject) {
				if (inObject == -1)
					return;
				curGear = inObject;
				view.setGearText(Integer.toString(curGear));
			}
		});

		data.getTotalBonus(new Listener<Integer>() {

			@Override
			public void onAction(Integer inObject) {
				curGear = inObject;
				view.setGearText(Integer.toString(curGear));
				view.setFightScore(Integer.toString(curGear + curLevel));

			}
		});

		view.setGearText(Integer.toString(curGear));

		view.setLevelText(Integer.toString(curLevel));

		view.setFightScore(Integer.toString(curGear + curLevel));

		view.setUpLevel(new Listener<Void>() {

			@Override
			public void onAction(Void inObject) {
				if (curLevel == topLevel)
					return;
				view.setLevelText(Integer.toString(++curLevel));
				view.setFightScore(Integer.toString(curGear + curLevel));
				view.setDownLevelEnabled(true);
				if (curLevel == topLevel)
					view.setUpLevelEnabled(false);
				
			}
		});
		view.setDownLevel(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				if (curLevel == 1)
					return;
				
				view.setLevelText(Integer.toString(--curLevel));
				view.setFightScore(Integer.toString(curGear + curLevel));
				view.setUpLevelEnabled(true);
				if (curLevel ==1)
					view.setDownLevelEnabled(false);
			}
		});
		view.setAddHandle(new Listener<Void>() {
			@Override
			public void onAction(Void inObject) {
				view.showAdd();

			}
		}, new Listener<GearItemData>() {
			@Override
			public void onAction(final GearItemData inObject) {
				data.addGearItem(inObject, new Listener<Void>() {
					@Override
					public void onAction(Void inObject2) {
						curGear += inObject.getBonus();
						view.setGearText(Integer.toString(curGear));
						view.setFightScore(Integer.toString(curGear + curLevel));
					}
				});
				
			}
		});
		view.setEditGear(new Listener<Void>() {

			@Override
			public void onAction(Void inObject) {
				view.showGearEdit();
			}
		});
		view.setFightAction(new Listener<Void>() {

			@Override
			public void onAction(Void inObject) {
				view.showFightScreen();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> U getAppHandle() {
		return (U) view.getHandle();
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause() {
		data.setVariable(Persistance.VAR_PLAYER_LEVEL_LAST, curLevel);
		data.setVariable(Persistance.VAR_TOTAL_GEAR, curGear);
		data.onDestroy();
	}

	class DataCallback implements Listener<GearItemData> {
		@Override
		public void onAction(GearItemData inObject) {
			data.addGearItem(inObject, new Listener<Void>() {
				@Override
				public void onAction(Void inObject) {
					new RefreshGearScore().onAction(null);

				}
			});

		}
	}

	class RefreshGearScore implements Listener<Void> {
		public void onAction(Void inObject) {
			data.getTotalBonus(new Listener<Integer>() {

				@Override
				public void onAction(Integer inObject) {
					curGear = inObject;
					view.setGearText(Integer.toString(inObject));
					view.setFightScore(Integer.toString(curGear + curLevel));
					data.setVariable(Persistance.VAR_TOTAL_GEAR, inObject);
				}
			});
		};
	}
}

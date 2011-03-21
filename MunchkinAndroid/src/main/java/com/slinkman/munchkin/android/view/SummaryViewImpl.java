package com.slinkman.munchkin.android.view;

import com.google.inject.Inject;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.android.Mover;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.view.SummaryView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SummaryViewImpl implements SummaryView<View> {

	View viewHandle;
	TextView gearScore;
	TextView fightScore;
	TextView levelScore;
	Button fightButton;
	Button levelUp;
	Button levelDown;
	Button gearAdd;
	Button gearEdit;

	Listener<GearItemData> dataHandle;

	Listener<Integer> topHandle;
	Listener<Integer> levelHandle;

	Mover mover;

	Context appContext;

	@Inject
	SummaryViewImpl(Context inContext, LayoutInflater inflator, Mover move) {
		viewHandle = inflator.inflate(R.layout.summary, null);
		String typePath = inContext.getString(R.string.typeface);
		Typeface typeface = Typeface.createFromAsset(inContext.getAssets(),
				typePath);
		appContext = inContext;
		mover = move;

		TextView temp = (TextView) viewHandle
				.findViewById(R.id.summary_fight_title);
		temp.setTypeface(typeface);

		temp = (TextView) viewHandle.findViewById(R.id.summary_gear_title);
		temp.setTypeface(typeface);

		temp = (TextView) viewHandle.findViewById(R.id.summary_level_title);
		temp.setTypeface(typeface);

		gearScore = (TextView) viewHandle.findViewById(R.id.summary_gear_score);
		gearScore.setTypeface(typeface);

		fightScore = (TextView) viewHandle
				.findViewById(R.id.summary_fight_score);
		fightScore.setTypeface(typeface);

		levelScore = (TextView) viewHandle
				.findViewById(R.id.summary_level_score);
		levelScore.setTypeface(typeface);

		fightButton = (Button) viewHandle
				.findViewById(R.id.summary_fight_button);
		fightButton.setTypeface(typeface);

		levelUp = (Button) viewHandle.findViewById(R.id.summary_level_up);
		levelUp.setTypeface(typeface);

		levelDown = (Button) viewHandle.findViewById(R.id.summary_level_down);
		levelDown.setTypeface(typeface);

		gearAdd = (Button) viewHandle.findViewById(R.id.summary_add);
		gearAdd.setTypeface(typeface);

		gearEdit = (Button) viewHandle.findViewById(R.id.summary_edit);
		gearEdit.setTypeface(typeface);
	}

	@Override
	public void setDownLevel(final Listener<Void> downReturn) {
		Button temp = (Button) viewHandle.findViewById(R.id.summary_level_down);
		temp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				downReturn.onAction(null);

			}
		});
	}

	@Override
	public void setEditGear(final Listener<Void> editReturn) {
		gearEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				editReturn.onAction(null);

			}
		});

	}

	@Override
	public void setFightAction(final Listener<Void> fightReturn) {
		fightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				fightReturn.onAction(null);
				mover.moveToFight();
			}
		});

	}

	@Override
	public void setFightScore(String inText) {
		fightScore.setText(inText);

	}

	@Override
	public void setGearText(String inText) {
		gearScore.setText(inText);

	}

	@Override
	public void setLevelText(String inText) {
		levelScore.setText(inText);

	}

	@Override
	public void setUpLevel(final Listener<Void> upReturn) {
		levelUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				upReturn.onAction(null);

			}
		});

	}

	@Override
	public void showFightScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showGearEdit() {
		mover.moveToGear();
	}

	@Override
	public View getHandle() {
		return viewHandle;
	}

	@Override
	public void setAddHandle(final Listener<Void> buttonReturn,
			final Listener<GearItemData> dataHandle) {
		gearAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				buttonReturn.onAction(null);
			}
		});
		this.dataHandle = dataHandle;

	}

	@Override
	public void showAdd() {
		GearDialog dialog = new GearDialog(appContext, dataHandle, -1, "", "");
		dialog.show();
	}

	@Override
	public void setDownLevelEnabled(boolean inEnabled) {
		levelDown.setEnabled(inEnabled);
	}

	@Override
	public void setUpLevelEnabled(boolean inEnabled) {
		levelUp.setEnabled(inEnabled);
	}

	@Override
	public void setLevelHandles(Listener<Integer> topHandle,
			Listener<Integer> levelHandle, final Listener<Void> actionHandle) {
		this.topHandle = topHandle;
		this.levelHandle = levelHandle;
		levelScore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				actionHandle.onAction(null);
			}
		});
	}

	@Override
	public void showLevel(int topLevel, int curLevel) {
		new LevelDialog(appContext, topLevel, curLevel, topHandle, levelHandle)
				.show();
	}

	@Override
	public void setLevelShow(final Listener<Void> levelShow) {
		levelScore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				levelShow.onAction(null);

			}
		});

	}

}

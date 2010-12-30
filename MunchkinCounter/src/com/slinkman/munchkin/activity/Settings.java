package com.slinkman.munchkin.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.data.BaseData;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.SettingPresenter;
import com.slinkman.munchkin.presenter.SettingPresenter.SettingView;
import com.slinkman.munchkin.widget.BaseActivity;
import com.slinkman.munchkin.widget.LevelDialog;

public class Settings extends BaseActivity implements SettingView {

	View itemSelection;
	TextView levelText;
	Persistance data;

	@Override
	protected Presenter bindMainView(View inView) {

		itemSelection = (View) findViewById(R.id.setting_max_line);
		levelText = (TextView) findViewById(R.id.setting_max_level);
		data = new BaseData(this);
		return new SettingPresenter(this, data);
	}

	@Override
	public int getActivityID() {
		return BaseActivity.ACTIVITY_SETTING;
	}

	@Override
	protected View getMainView() {
		return inflator.inflate(R.layout.setting_layout, null);
	}

	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID) {
		case SettingPresenter.TEXT_LEVEL_LIMIT:
			levelText.setText("Max Level: " + inText);
			break;
		default:
			throw new WidgetError();
		}
	}

	@Override
	public void setListener(int objectID, final Listener<Void> inListener)
			throws WidgetError {
		switch (objectID) {
		case SettingPresenter.LISTENER_MAX_ITEM:
			itemSelection.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		default:
			throw new WidgetError();
		}

	}

	@Override
	public void setDialogListener(int id, Listener<Integer> dialogListener)
			throws WidgetError {
		switch (id) {
		case SettingPresenter.RETURN_CHANGE_DIALOG:
			new LevelDialog(this, data, dialogListener);
			break;
		default:
			throw new WidgetError();
		}
		
	}

}

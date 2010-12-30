package com.slinkman.munchkin.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.data.BaseData;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.PowerPresenter;
import com.slinkman.munchkin.presenter.PowerPresenter.PowerView;
import com.slinkman.munchkin.widget.BaseActivity;

public class Power extends BaseActivity implements PowerView {

	private Persistance data;

	private ImageView playerUp;
	private ImageView playerDown;
	private ImageView monsterUp;
	private ImageView monsterDown;

	private Button monsterReset;
	private Button playerTemp;

	private TextView playerText;
	private TextView monsterText;
	
	private String playerLevel="1";
	private String playerDiff="0";

	@Override
	protected Presenter bindMainView(View inView) {
		playerUp = (ImageView) findViewById(R.id.level_player_up);
		playerDown = (ImageView) findViewById(R.id.level_player_down);
		monsterUp = (ImageView) findViewById(R.id.level_monster_up);
		monsterDown = (ImageView) findViewById(R.id.level_monster_down);

		monsterReset = (Button) findViewById(R.id.level_monster_reset);
		playerTemp = (Button) findViewById(R.id.level_player_temp);

		playerText = (TextView) findViewById(R.id.level_player_text);
		monsterText = (TextView) findViewById(R.id.level_monster_text);

		data = new BaseData(this);
		return new PowerPresenter(this, data);
	}

	@Override
	protected View getMainView() {
		return inflator.inflate(R.layout.power_layout, null);
	}

	public void setListener(int objectID, final Listener<Void> inListener)
			throws WidgetError {
		switch (objectID) {
		case PowerPresenter.LISTENER_DOWN_MONSTER:
			monsterDown.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case PowerPresenter.LISTENER_DOWN_PLAYER:
			playerDown.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case PowerPresenter.LISTENER_MONSTER_RESET:
			monsterReset.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case PowerPresenter.LISTENER_PLAYER_BASE:
			playerTemp.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case PowerPresenter.LISTENER_UP_MONSTER:
			monsterUp.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;

		case PowerPresenter.LISTENER_UP_PLAYER:
			playerUp.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		}
	}

	public void setWidgetText(int objectID, final String inText)
			throws WidgetError {
		switch (objectID) {
		case PowerPresenter.TEXT_MONSTER_LEVEL:
			monsterText.setText(inText);
			break;
		case PowerPresenter.TEXT_PLAYER_LEVEL:
			playerLevel = inText;
			playerText.setText(playerLevel + " " + playerDiff);
			break;
		case PowerPresenter.TEXT_PLAYER_DIFF:
			playerDiff = inText;
			playerText.setText(playerLevel + " " + playerDiff);
			break;
		default:
			throw new WidgetError();
		}
	}

	@Override
	public int getActivityID() {
		return BaseActivity.ACTIVITY_POWER;
	}

}

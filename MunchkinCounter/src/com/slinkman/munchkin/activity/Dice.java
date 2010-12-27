package com.slinkman.munchkin.activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.data.BaseData;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.DicePresenter;
import com.slinkman.munchkin.presenter.DicePresenter.DiceView;
import com.slinkman.munchkin.widget.BaseActivity;

public class Dice extends BaseActivity implements DiceView {

	private Button rollBotton;
	private TextView diceImage;
	private TextView rollingDisplay;
	private Persistance data;
	private AlphaAnimation rollingAnimation;

	public void setListener(int objectID, final Listener inListener)
			throws WidgetError {
		switch (objectID) {
		case DicePresenter.LISTENER_ROLL_CLICK:
			rollBotton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					rollingAnimation = new AlphaAnimation(0, 1);
					rollingAnimation.setDuration(200);
					rollingAnimation.setAnimationListener(new Fadein());
					rollingDisplay.setAnimation(rollingAnimation);
					inListener.onAction();
				}
			});
			break;
		default:
			throw new WidgetError();
		}
	}

	public void setWidgetResource(int objectID, final int widgetState)
			throws WidgetError {
		switch (objectID) {
		case DicePresenter.RESOURCE_TARGET_DICE:
			Log.i("Dice", "Resource Change");
			if (rollingAnimation != null)
				rollingAnimation.start();
			diceImage.setText(Integer.toString(widgetState));
			break;
		default:
			diceImage.setText("0");
		}

	}

	protected Presenter bindMainView(View inView) {
		rollBotton = (Button) findViewById(R.id.dice_roll_button);
		diceImage = (TextView) findViewById(R.id.dice_roll_display);
		rollingDisplay = (TextView) findViewById(R.id.dice_rolling_text);

		data = new BaseData(this);
		return new DicePresenter(this, data);
	}

	public int getActivityID() {
		return BaseActivity.ACTIVITY_DICE;
	}

	protected View getMainView() {
		return inflator.inflate(R.layout.dice_layout, null);

	}
	
	private class Fadein implements AnimationListener {

		public void onAnimationEnd(Animation arg0) {
			AlphaAnimation temp = new AlphaAnimation(1, 0);
			temp.setDuration(1000);
			rollingDisplay.setAnimation(temp);
			temp.start();
		}

		public void onAnimationRepeat(Animation arg0) {

		}

		public void onAnimationStart(Animation arg0) {
			Log.i("Dice Animation", "Animation");

		}

	}

}

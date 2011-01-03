package com.slinkman.munchkin.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.data.BaseData;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.DicePresenter;
import com.slinkman.munchkin.presenter.DicePresenter.DiceView;
import com.slinkman.munchkin.widget.BaseActivity;

public class Dice extends BaseActivity implements DiceView, SensorEventListener {

	private Button rollBotton;
	private TextView diceImage;
	private TextView rollingDisplay;
	private Persistance data;
	private AlphaAnimation rollingAnimation;
	private static final int SHAKE_THRESHOLD = 500;
	private boolean sensorRegistered = false;
	private Listener<Void> shakeAction;
	private long lastUpdate = 0;
	private float lastValues[];
	private SensorManager sensorMgt;

	public void setListener(int objectID, final Listener<Void> inListener)
			throws WidgetError {
		switch (objectID) {
		case DicePresenter.LISTENER_ROLL_CLICK:
			rollBotton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					rollAnimation();
					inListener.onAction(null);
				}
			});
			break;
		case DicePresenter.LISTENER_SHAKE:
			shakeAction = inListener;
			break;
		default:
			throw new WidgetError();
		}
	}

	private void rollAnimation() {
		rollingAnimation = new AlphaAnimation(0, 1);
		rollingAnimation.setDuration(200);
		rollingAnimation.setAnimationListener(new Fadein());
		rollingDisplay.setAnimation(rollingAnimation);
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
		sensorMgt = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorRegistered = sensorMgt.registerListener(this,
				sensorMgt.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		if (!sensorRegistered) {
			sensorMgt.unregisterListener(this);
		}
		data = new BaseData(this);
		return new DicePresenter(this, data);
	}

	@Override
	protected void onPause() {
		super.onPause();
		shakeAction = null;
		if (sensorRegistered) 
			sensorMgt.unregisterListener(this);
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

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.sensor.getType() == SensorManager.SENSOR_ACCELEROMETER) {
			long curTime = System.currentTimeMillis();
			if ((curTime - lastUpdate) > 1000) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;
				float values[] = arg0.values;
				float speed = getSpeed(values, diffTime);
				if (speed > SHAKE_THRESHOLD && shakeAction != null) {
					rollAnimation();
					shakeAction.onAction(null);
				}
				lastValues = arg0.values.clone();
			}
		}
	}

	private float getSpeed(float[] inValues, long timeDiff) {
		if (lastValues == null) {
			return 0;
		}
		return (float) Math.abs(inValues[SensorManager.DATA_X]
				+ inValues[SensorManager.DATA_Y]
				+ inValues[SensorManager.DATA_Z]
				- lastValues[SensorManager.DATA_X]
				- lastValues[SensorManager.DATA_Y]
				- lastValues[SensorManager.DATA_Z])
				/ timeDiff * 10000;
	}
}

package com.slinkman.munchkin.activity;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.data.BaseData;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.CounterPresenter;
import com.slinkman.munchkin.widget.BaseActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Counter extends BaseActivity implements CounterPresenter.CountView {

	private ImageView upButton;
	private ImageView downButton;
	private TextView counterText;
	private Persistance data;

	@Override
	protected View getMainView() {
		return inflator.inflate(R.layout.counter_layout, null);
	}

	@Override
	protected Presenter bindMainView(View inView) {
		upButton = (ImageView) inView.findViewById(R.id.counter_up);
		downButton = (ImageView) inView.findViewById(R.id.counter_down);
		counterText = (TextView) inView.findViewById(R.id.full_count);
		data = new BaseData(this);
		return new CounterPresenter(this, data);
	}

	@Override
	public void setListener(int objectID, final Listener<Void> inListener)
			throws WidgetError {
		switch (objectID) {
		case CounterPresenter.LISTENER_UP_BUTTON:
			upButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case CounterPresenter.LISTENER_DOWN_BUTTON:
			downButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
		case CounterPresenter.LISTENER_SWIPE_UP:
			break;
		}

	}

	@Override
	public void setWidgetEnabled(int objectID, boolean inEnabled)
			throws WidgetError {
		try {
			switch (objectID) {
			case CounterPresenter.ENABLED_UP:
				setImageAlpha(upButton, inEnabled);
				upButton.setEnabled(inEnabled);
				break;
			case CounterPresenter.ENABLED_DOWN:
				setImageAlpha(downButton, inEnabled);
				downButton.setEnabled(inEnabled);
				break;
			}
		} catch (Exception ex) {
		}

	}

	private void setImageAlpha(ImageView inImage, boolean inEnabled) {
		if (inEnabled)
			inImage.setAlpha(255);
		else
			inImage.setAlpha(100);
	}

	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID) {
		case CounterPresenter.TEXT_COUNTER:
			counterText.setText(inText);
			break;
		default:
			throw new WidgetError();
		}
	}

	@Override
	public int getActivityID() {
		// TODO Auto-generated method stub
		return BaseActivity.ACTIVITY_COUNTER;
	}
}
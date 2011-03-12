package com.slinkman.munchkin.android;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.presenter.CounterPresenter;
import com.slinkman.munchkin.presenter.DicePresenter;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.SummaryPresenter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class AbstractActivity extends Activity {

	RelativeLayout totalLayout;
	protected LayoutInflater inflate;
	Injector inject;
	Presenter presenter;
	int holder = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Abstract", "onCreate");
		Mover move = new Mover() {

			@Override
			public void moveToGear() {
				holder = 3;
				changeView();

			}
		};
		inject = Guice.createInjector(new AndroidGuice(this, move));
		inflate = LayoutInflater.from(getApplicationContext());
		totalLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		totalLayout.addView(getBottomView(), params);

		setContentView(totalLayout);
	}

	@Override
	protected void onResume() {
		Log.i("Abstract", "onResume");
		super.onResume();
		presenter = getPresenter();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.addRule(RelativeLayout.ABOVE, R.id.bottom_layout);
		totalLayout.addView(getMainView(), params);
		presenter.bind();
	}

	@Override
	protected void onPause() {
		super.onPause();
		presenter.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter = null;
	}

	protected View getMainView() {
		return presenter.getAppHandle();
	}

	protected Presenter getPresenter() {
		if (holder == 1)
			return inject.getInstance(CounterPresenter.class);
		else if (holder == 2)
			return inject.getInstance(DicePresenter.class);
		else if (holder == 3)
			return inject.getInstance(GearPresenter.class);
		else
			return inject.getInstance(SummaryPresenter.class);

	}

	private void changeView() {
		totalLayout.removeView(presenter.<View> getAppHandle());
		presenter.onPause();
		presenter = getPresenter();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.addRule(RelativeLayout.ABOVE, R.id.bottom_layout);
		totalLayout.addView(presenter.<View> getAppHandle(), params);
		presenter.bind();
	}

	private View getBottomView() {
		View bottom = inflate.inflate(R.layout.bottom_layout, null);
		TextView counter = (TextView) bottom.findViewById(R.id.bottom_counter);
		counter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				holder = 1;
				changeView();
			}
		});
		TextView dice = (TextView) bottom.findViewById(R.id.bottom_dice);
		dice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				holder = 2;
				changeView();
			}
		});
		TextView gear = (TextView) bottom.findViewById(R.id.bottom_gear);
		gear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				holder = 3;
				changeView();
			}
		});

		TextView summary = (TextView) bottom.findViewById(R.id.bottom_summary);
		summary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				holder = 4;
				changeView();

			}
		});
		return bottom;
	}
}

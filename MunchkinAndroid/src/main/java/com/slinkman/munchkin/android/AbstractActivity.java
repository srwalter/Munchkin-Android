package com.slinkman.munchkin.android;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.presenter.CounterPresenter;
import com.slinkman.munchkin.presenter.DicePresenter;
import com.slinkman.munchkin.presenter.FightPresenter;
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
	int holder = 4;

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

			@Override
			public void moveToFight() {
				holder = 5;
				changeView();
			}

			@Override
			public void moveToSummary() {
				holder = 4;
				changeView();

			}
		};
		inject = Guice.createInjector(new AndroidGuice(this, move));
		inflate = LayoutInflater.from(getApplicationContext());
		totalLayout = new RelativeLayout(this);

		setContentView(totalLayout);
	}

	@Override
	protected void onResume() {
		Log.i("Abstract", "onResume");
		super.onResume();
		presenter = getPresenter();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		totalLayout.addView(getMainView(), params);
		presenter.bind();
	}

	@Override
	public void onBackPressed() {
		if (holder == 4){
			super.onBackPressed();
		}
		else{
			holder =4;
		}
		changeView();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("Abstract", "onPause");
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
		else if (holder == 4)
			return inject.getInstance(SummaryPresenter.class);
		else
			return inject.getInstance(FightPresenter.class);

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

}

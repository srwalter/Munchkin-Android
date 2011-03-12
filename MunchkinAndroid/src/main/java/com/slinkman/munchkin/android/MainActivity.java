package com.slinkman.munchkin.android;

import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.presenter.CounterPresenter;

import android.view.View;

public class MainActivity extends AbstractActivity {

	@Override
	protected Presenter getPresenter() {
		return inject.getInstance(CounterPresenter.class);
	}

	@Override
	protected View getMainView() {
		return presenter.getAppHandle();
	}

}

package com.slinkman.munchkin.android;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.slinkman.munchkin.android.view.DiceViewImpl;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.DiceView;
import com.slinkman.munchkin.presenter.DicePresenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class DiceActivity extends Activity {

	private static String TAG = "MunchkinAndroid";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		Injector inject = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Presenter.class).to(DicePresenter.class);
				bind(Persistance.class).to(BaseData.class).in(Singleton.class);
				bind(LayoutInflater.class).toInstance(
						LayoutInflater.from(DiceActivity.this));
				bind(Context.class).toInstance(getApplicationContext());
				bind(new TypeLiteral<DiceView<?>>(){}).to(DiceViewImpl.class);
			}
		});
		Presenter presenter = inject.getInstance(Presenter.class);
		View diceView = presenter.getAppHandle();
		setContentView(diceView);
		presenter.bind();
	}
}
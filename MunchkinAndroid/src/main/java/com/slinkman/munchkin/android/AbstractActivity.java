package com.slinkman.munchkin.android;

import java.util.Random;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.presenter.CounterPresenter;
import com.slinkman.munchkin.presenter.DicePresenter;
import com.slinkman.munchkin.presenter.FightPresenter;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.SummaryPresenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class AbstractActivity extends Activity {

	RelativeLayout totalLayout;
	protected LayoutInflater inflate;
	Injector inject;
	Presenter presenter;
	int holder = 4;
	Random mRand ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRand = new Random(System.currentTimeMillis());
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflate = getMenuInflater();
		inflate.inflate(R.menu.overmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menu_dice_item:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Rolled "+Integer.toString(mRand.nextInt(6)+1))
			       .setCancelable(false)
			       .setPositiveButton("Done", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						
					}
				});
			AlertDialog alert = builder.create();
			alert.show();
			return true;
			default:
				return super.onOptionsItemSelected(item);
		}
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
		try{
		super.onDestroy();
		} catch (Exception ex){
			Log.i("Abstract", "Destroy Failed");
		}
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

package com.slinkman.munchkin.android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.slinkman.munchkin.android.data.DatabaseData;
import com.slinkman.munchkin.android.view.CounterViewImpl;
import com.slinkman.munchkin.android.view.DiceViewImpl;
import com.slinkman.munchkin.android.view.FightViewImpl;
import com.slinkman.munchkin.android.view.GearViewImpl;
import com.slinkman.munchkin.android.view.SummaryViewImpl;
import com.slinkman.munchkin.apis.data.GearData;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.CounterView;
import com.slinkman.munchkin.apis.view.DiceView;
import com.slinkman.munchkin.apis.view.FightView;
import com.slinkman.munchkin.apis.view.GearView;
import com.slinkman.munchkin.apis.view.SummaryView;

public class AndroidGuice extends AbstractModule {

	Activity context;
	Mover move;

	public AndroidGuice(Activity inAct, Mover move) {
		context = inAct;
		this.move = move;
	}

	@Override
	protected void configure() {

		// Android Specific binds
		bind(LayoutInflater.class).toInstance(
				(LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE));
		bind(Context.class).toInstance(context);
		bind(Activity.class).toInstance(context);
		bind(Mover.class).toInstance(move);

		// Android Data Binds
		bind(Persistance.class).to(BaseData.class);
		bind(GearData.class).to(DatabaseData.class);

		// Android View Binds
		bind(new TypeLiteral<DiceView<?>>() {
		}).to(DiceViewImpl.class);
		bind(new TypeLiteral<CounterView<?>>() {
		}).to(CounterViewImpl.class);
		bind(new TypeLiteral<GearView<?>>() {
		}).to(GearViewImpl.class);
		bind(new TypeLiteral<FightView<?>>() {
		}).to(FightViewImpl.class);
		
		bind(new TypeLiteral<SummaryView<?>>() {
		}).to(SummaryViewImpl.class);
	}

}

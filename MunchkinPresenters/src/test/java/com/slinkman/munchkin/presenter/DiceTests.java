package com.slinkman.munchkin.presenter;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.DiceView;
import com.slinkman.munchkin.mocks.data.BaseDataMock;
import com.slinkman.munchkin.mocks.view.DiceViewMock;

public class DiceTests {

	
	Injector injector;
	DicePresenter presenter;
	BaseDataMock data;
	DiceViewMock view;
	
	public DiceTests() {
		injector = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(Presenter.class).to(DicePresenter.class);
				bind(Persistance.class).to(BaseDataMock.class);
				bind(new TypeLiteral<DiceView<?>>(){}).to(DiceViewMock.class);
				
			}
		});
	}
	
	@Before
	public void setup(){
		presenter = (DicePresenter)injector.getInstance(Presenter.class);
		data = (BaseDataMock) presenter.data;
		view = (DiceViewMock) presenter.view;
	}
	
	@Test
	public void rollTest(){
		presenter.bind();
		int previousCache = view.cache;
		view.rollHandle.onAction(null);
		assertTrue(!(view.cache==previousCache));
	}
	
	@Test
	public void initTest(){
		presenter.bind();
		assertEquals(1, view.diceValue);
	}
}

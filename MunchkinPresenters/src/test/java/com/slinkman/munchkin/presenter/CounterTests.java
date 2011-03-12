package com.slinkman.munchkin.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.CounterView;
import com.slinkman.munchkin.mocks.data.BaseDataMock;
import com.slinkman.munchkin.mocks.view.CounterViewMock;

public class CounterTests {

	public CounterTests() {
		injector = Guice.createInjector(new AbstractModule() {

			protected void configure() {
				bind(Persistance.class).to(BaseDataMock.class);
				bind(new TypeLiteral<CounterView<?>>(){}).to(CounterViewMock.class);
				bind(Presenter.class).to(CounterPresenter.class);

			}
		});
	}

	CounterPresenter presenter;
	CounterViewMock viewHandle;
	BaseDataMock data;

	@Before
	public void setupTest() {
		presenter = (CounterPresenter) injector.getInstance(Presenter.class);
		viewHandle = (CounterViewMock) presenter.view;
		data = (BaseDataMock) presenter.data;
	}

	Injector injector;

	@Test
	public void counterInit() {
		presenter.bind();
		assertFalse(viewHandle.downEnabled);
		assertTrue(viewHandle.upEnabled);
		assertNotNull(viewHandle.downHandle);
		assertNotNull(viewHandle.upHandle);
	}

	@Test
	public void counterIncriment() {
		presenter.bind();
		viewHandle.upHandle.onAction(null);
		assertEquals("2", viewHandle.counterText);
		assertTrue(viewHandle.downEnabled);
		assertTrue(viewHandle.upEnabled);
	}

	@Test
	public void counterIncrimentLimit() {
		presenter.bind();
		for (int simpleCounter = 2; simpleCounter <= 10; simpleCounter++) {
			viewHandle.upHandle.onAction(null);
			assertEquals(Integer.toString(simpleCounter),
					viewHandle.counterText);
		}
		assertFalse(viewHandle.upEnabled);
	}

	@Test
	public void counterSave() {
		presenter.bind();
		viewHandle.upHandle.onAction(null);
		presenter.onPause();
		BaseDataMock data = (BaseDataMock) presenter.data;
		int value = data.intHash.get(Persistance.VAR_PLAYER_LEVEL_LAST);
		assertEquals(2, value);
	}
	
	@Test
	public void counterLoad(){
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 3);
		presenter.bind();
		assertEquals("3", viewHandle.counterText);
	}
	
	@Test 
	public void counterLoadIncriment(){
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 3);
		presenter.bind();
		viewHandle.upHandle.onAction(null);
		assertEquals("4", viewHandle.counterText);
	}
	
	@Test
	public void counterLoadDecriment(){
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 3);
		presenter.bind();
		viewHandle.downHandle.onAction(null);
		assertEquals("2", viewHandle.counterText);
	}
	
	@Test
	public void counterUpDown(){
		presenter.bind();
		viewHandle.upHandle.onAction(null);
		viewHandle.upHandle.onAction(null);
		assertEquals("3",viewHandle.counterText);
		viewHandle.downHandle.onAction(null);
		assertEquals("2", viewHandle.counterText);
	}
	
	@Test
	public void counterLoadUpDown(){
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 3);
		presenter.bind();
		viewHandle.upHandle.onAction(null);
		viewHandle.upHandle.onAction(null);
		assertEquals("5",viewHandle.counterText);
		viewHandle.downHandle.onAction(null);
		assertEquals("4", viewHandle.counterText);
	}
}

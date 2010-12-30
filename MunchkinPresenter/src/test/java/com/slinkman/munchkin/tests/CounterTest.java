package com.slinkman.munchkin.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.mocks.data.CountDataMock;
import com.slinkman.munchkin.mocks.view.CounterViewMock;
import com.slinkman.munchkin.presenter.CounterPresenter;


public class CounterTest {

	CounterViewMock temp;
	CountDataMock data;
	Presenter presenter;
	
	@Before
	public void setup() {
		temp = new CounterViewMock();
		data = new CountDataMock();
		new CounterPresenter(temp,data);
	}

	@Test
	public void testCounterSetup() {
		assertEquals(CounterViewMock.FALSE, (Integer) temp.enabledMap
				.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.TRUE, (Integer) temp.enabledMap
				.get(CounterPresenter.ENABLED_UP));
		assertEquals("1", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
	}

	@Test
	public void testCounterSimpleIncriment() {
		if (temp.enabledMap.get(CounterPresenter.ENABLED_UP) == CounterViewMock.TRUE)
			temp.listenerMap.get(CounterPresenter.LISTENER_UP_BUTTON)
					.onAction();
		assertEquals("2", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap
				.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap
				.get(CounterPresenter.ENABLED_UP));
	}

	@Test
	public void testCounterLimitIncriment() {
		int simpleCounter = 1;
		for (; temp.enabledMap.get(CounterPresenter.ENABLED_UP) == CounterViewMock.TRUE;) {
			temp.listenerMap.get(CounterPresenter.LISTENER_UP_BUTTON)
					.onAction();
			simpleCounter++;
			assertEquals(Integer.toString(simpleCounter), (String) temp.textMap
					.get(CounterPresenter.TEXT_COUNTER));
		}
		assertEquals("10", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.FALSE, temp.enabledMap
				.get(CounterPresenter.ENABLED_UP));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap
				.get(CounterPresenter.ENABLED_DOWN));
	}

	@Test
	public void testCounterFirstLoad() {
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, (Integer)1);
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		assertEquals("1", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.FALSE, temp.enabledMap.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_UP));
	}
	
	@Test
	public void testCounterMidLoad() {
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, (Integer)2);
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		assertEquals("2", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_UP));
	}
	
	@Test
	public void testCounterTopLoad() {
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, (Integer)10);
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		assertEquals("10", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.FALSE, temp.enabledMap.get(CounterPresenter.ENABLED_UP));
	}
	
	@Test
	public void testCounterTopLoadDynamicLimit() {
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, (Integer)12);
		data.dataHash.put(Persistance.VAR_TOPLEVEL, (Integer)12);
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		assertEquals("12", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.FALSE, temp.enabledMap.get(CounterPresenter.ENABLED_UP));
	}
	
	@Test
	public void testCounterMidLoadDynamicLimit() {
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, (Integer)8);
		data.dataHash.put(Persistance.VAR_TOPLEVEL, (Integer)12);
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		assertEquals("8", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_UP));
	}
	
	@Test
	public void testCounterFirstLoadDynamicLimit() {
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, (Integer)1);
		data.dataHash.put(Persistance.VAR_TOPLEVEL, (Integer)12);
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		assertEquals("1", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
		assertEquals(CounterViewMock.FALSE, temp.enabledMap.get(CounterPresenter.ENABLED_DOWN));
		assertEquals(CounterViewMock.TRUE, temp.enabledMap.get(CounterPresenter.ENABLED_UP));
	}
	
	@Test
	public void testSave(){
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		temp.listenerMap.get(CounterPresenter.LISTENER_UP_BUTTON).onAction();
		presenter.onPause();
		assertEquals(2, data.dataHash.get(Persistance.VAR_PLAYER_LEVEL_LAST));
	}
	
	@Test
	public void testLoadSave(){
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, (Integer)2);
		temp = new CounterViewMock();
		presenter = new CounterPresenter(temp, data);
		temp.listenerMap.get(CounterPresenter.LISTENER_UP_BUTTON).onAction();
		presenter.onPause();
		assertEquals(3, data.dataHash.get(Persistance.VAR_PLAYER_LEVEL_LAST));
	}
	
	@Test
	public void testOverLimitLoad(){
		data.dataHash.put(Persistance.VAR_TOPLEVEL, 10);
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 13);
		presenter = new CounterPresenter(temp, data);
		assertEquals("10", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
	}
	
	@Test
	public void testUnderLegal(){
		data.dataHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, -1);
		presenter = new CounterPresenter(temp, data);
		assertEquals("1", temp.textMap.get(CounterPresenter.TEXT_COUNTER));
	}
}

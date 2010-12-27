package com.slinkman.munchkin.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.mocks.data.PowerDataMock;
import com.slinkman.munchkin.mocks.view.PowerViewMock;
import com.slinkman.munchkin.presenter.PowerPresenter;


public class PowerTest {
	PowerViewMock view;
	PowerDataMock data;
	Presenter presenter;

	@Before
	public void cleanSetup() {
		view = new PowerViewMock();
		data = new PowerDataMock();
		presenter = new PowerPresenter(view, data);
	}

	@Test
	public void powerColdStart() {
		assertEquals("1", view.textMap.get(PowerPresenter.TEXT_PLAYER_LEVEL));
		assertEquals("(0)", view.textMap.get(PowerPresenter.TEXT_PLAYER_DIFF));
		assertEquals("1", view.textMap.get(PowerPresenter.TEXT_MONSTER_LEVEL));
	}

	@Test
	public void powerRecover(){
		data.dataHash.put(Persistance.VAR_MONSTER_LAST, 2);
		data.dataHash.put(Persistance.VAR_PLAYER_POWER_LAST, 4);
		presenter = new PowerPresenter(view, data);
		assertEquals("2", view.textMap.get(PowerPresenter.TEXT_MONSTER_LEVEL));
		assertEquals("(+3)", view.textMap.get(PowerPresenter.TEXT_PLAYER_DIFF));
		assertEquals("4", view.textMap.get(PowerPresenter.TEXT_PLAYER_LEVEL));
	}
	
	@Test
	public void powerWriteRecovery(){
		view.listenerMap.get(PowerPresenter.LISTENER_UP_PLAYER).onAction();
		view.listenerMap.get(PowerPresenter.LISTENER_UP_PLAYER).onAction();
		view.listenerMap.get(PowerPresenter.LISTENER_UP_MONSTER).onAction();
		presenter.onPause();
		assertEquals(2, data.dataHash.get(Persistance.VAR_MONSTER_LAST));
		assertEquals(3, data.dataHash.get(Persistance.VAR_PLAYER_POWER_LAST));
		presenter = new PowerPresenter(view, data);
		assertEquals("2", view.textMap.get(PowerPresenter.TEXT_MONSTER_LEVEL));
		assertEquals("(+2)", view.textMap.get(PowerPresenter.TEXT_PLAYER_DIFF));
		assertEquals("3", view.textMap.get(PowerPresenter.TEXT_PLAYER_LEVEL));
	}
	
	@Test
	public void powerWarmStart(){
		data.dataHash.put(Persistance.VAR_MONSTER_LAST, 5);
		data.dataHash.put(Persistance.VAR_PLAYER_POWER_LAST, 6);
		presenter = new PowerPresenter(view, data);
		assertEquals("6", view.textMap.get(PowerPresenter.TEXT_PLAYER_LEVEL));
		assertEquals("(+5)", view.textMap.get(PowerPresenter.TEXT_PLAYER_DIFF));
		assertEquals("5", view.textMap.get(PowerPresenter.TEXT_MONSTER_LEVEL));
	}
	
}

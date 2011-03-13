package com.slinkman.munchkin.presenter;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.slinkman.munchkin.apis.Presenter;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.FightView;
import com.slinkman.munchkin.mocks.data.BaseDataMock;
import com.slinkman.munchkin.mocks.view.FightViewMock;

public class FightTest {

	Injector injector;
	Presenter presenter;
	FightViewMock view;
	BaseDataMock data;

	@Before
	public void setup() {
		injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(new TypeLiteral<FightView<?>>() {
				}).to(FightViewMock.class);
				bind(Persistance.class).to(BaseDataMock.class);
			}
		});
		presenter = injector.getInstance(FightPresenter.class);
		view = (FightViewMock) ((FightPresenter) presenter).view;
		data = (BaseDataMock) ((FightPresenter) presenter).data;
	}

	@Test
	public void testInit() {
		presenter.bind();
		assertNotNull(view.backHandle);
		assertNotNull(view.monsterDown);
		assertNotNull(view.monsterUp);
		assertNotNull(view.playerDown);
		assertNotNull(view.playerUp);
		assertEquals("1", view.playerfight);
		assertEquals("0", view.playerModifier);
		assertEquals("1", view.playerTotal);
		assertEquals("0", view.monsterModifier);
		assertEquals("0", view.monsterFight);
	}

	@Test
	public void testPlayerLoad() {
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 2);
		data.intHash.put(Persistance.VAR_TOTAL_GEAR, 3);
		presenter.bind();
		assertEquals("5", view.playerfight);
		assertEquals("0", view.playerModifier);
		assertEquals("5", view.playerTotal);
	}

	@Test
	public void testPlayerModifierUp() {
		presenter.bind();
		view.playerUp.onAction(null);
		assertEquals(view.playerModifier, "1");
	}

	@Test
	public void testPlayerModifierDown() {
		presenter.bind();
		// 1
		view.playerUp.onAction(null);
		assertEquals("1", view.playerModifier);
		// 0
		view.playerDown.onAction(null);
		assertEquals("0", view.playerModifier);
	}

	@Test
	public void testMonsterUp() {
		presenter.bind();
		view.monsterUp.onAction(null);
		assertEquals("1", view.monsterModifier);
		assertEquals("1", view.monsterTotal);
	}

	@Test
	public void testMonsterDown() {
		presenter.bind();
		view.monsterUp.onAction(null);
		assertEquals("1", view.monsterModifier);
		assertEquals("1", view.monsterTotal);
		view.monsterDown.onAction(null);
		assertEquals("0", view.monsterModifier);
		assertEquals("0", view.monsterTotal);
	}

	@Test
	public void testBackHit() {
		presenter.bind();
		assertFalse(view.backHit);
		view.backHandle.onAction(null);
		assertTrue(view.backHit);
	}

}

package com.slinkman.munchkin.presenter;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.GearData;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.data.Persistance;
import com.slinkman.munchkin.apis.view.SummaryView;
import com.slinkman.munchkin.mocks.data.GearDataMock;
import com.slinkman.munchkin.mocks.view.SummaryViewMock;

public class SummaryTest {

	Injector injector;
	SummaryPresenter presenter;
	SummaryViewMock view;
	GearDataMock data;

	public SummaryTest() {
		injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(new TypeLiteral<SummaryView<?>>() {
				}).to(SummaryViewMock.class);
				bind(GearData.class).to(GearDataMock.class);
			}
		});
	}

	@Before
	public void setup() {
		presenter = injector.getInstance(SummaryPresenter.class);
		view = (SummaryViewMock) presenter.view;
		data = (GearDataMock) presenter.data;
	}

	@Test
	public void testInit() {
		presenter.bind();
		assertNotNull(view.add);
		assertNotNull(view.down);
		assertNotNull(view.edit);
		assertNotNull(view.fight);
		assertNotNull(view.dataHandle);
		assertEquals("1", view.fightScore);
		assertEquals("1", view.levelScore);
		assertEquals("0", view.gearScore);

	}

	@Test
	public void testLevelUp() {
		presenter.bind();
		view.up.onAction(null);
		assertEquals("2", view.fightScore);
		assertEquals("2", view.levelScore);
		assertEquals("0", view.gearScore);
	}

	@Test
	public void testLevelDown() {
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 2);
		presenter.bind();
		view.down.onAction(null);
		assertEquals("1", view.fightScore);
		assertEquals("1", view.levelScore);
		assertEquals("0", view.gearScore);
	}

	@Test
	public void testLevelPreload() {
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 3);
		data.intHash.put(Persistance.VAR_TOTAL_GEAR, 4);
		data.dataMap.put(1, new GearItemData() {

			@Override
			public int getID() {
				return 1;
			}

			@Override
			public int getBonus() {
				return 4;
			}

			@Override
			public String getArmorType() {
				return "Arm";
			}
		});
		presenter.bind();
		assertEquals("3", view.levelScore);
		assertEquals("4", view.gearScore);
		assertEquals("7", view.fightScore);
	}

	@Test
	public void testShowGear() {
		presenter.bind();
		assertEquals(false, view.gearShown);
		view.edit.onAction(null);
		assertEquals(true, view.gearShown);
	}

	@Test
	public void testShowFight() {
		presenter.bind();
		assertFalse(view.fightShown);
		view.fight.onAction(null);
		assertTrue(view.fightShown);
	}

	@Test
	public void testAddGear() {
		presenter.bind();
		view.add.onAction(null);
		assertTrue(view.addShown);
		view.dataHandle.onAction(new GearItemData() {

			@Override
			public int getID() {
				return -1;
			}

			@Override
			public int getBonus() {
				return 1;
			}

			@Override
			public String getArmorType() {
				return "Head";
			}
		});
		data.getArmorType(0, new Listener<String>() {

			@Override
			public void onAction(String inObject) {
				assertEquals("Head", inObject);
			}
		});
		data.getBonus(0, new Listener<Integer>() {

			@Override
			public void onAction(Integer inObject) {
				assertEquals((Integer) 1, inObject);
			}
		});
		assertEquals("1", view.gearScore);
		assertEquals("2", view.fightScore);

	}

	@Test
	public void testLoadDisable() {
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 10);
		presenter.bind();
		assertFalse(view.upEnabled);
		assertEquals("10", view.levelScore);
	}

	@Test
	public void testLevelLimitUp() {
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 9);
		presenter.bind();
		assertTrue(view.upEnabled);
		view.up.onAction(null);
		assertFalse(view.upEnabled);
		assertEquals("10", view.levelScore);
	}

	@Test
	public void testLevelLimitDown() {
		presenter.bind();
		assertFalse(view.downEnabled);
		assertEquals("1", view.levelScore);
	}

	@Test
	public void testTopLevelChange() {
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 9);
		presenter.bind();
		assertEquals("9", view.levelScore);
		// Change top level
		view.topHandle.onAction(15);
		assertTrue(view.upEnabled);
		view.up.onAction(null);
		assertTrue(view.upEnabled);
		view.up.onAction(null);
		assertEquals("11", view.levelScore);
		view.up.onAction(null);
		view.up.onAction(null);
		view.up.onAction(null);
		view.up.onAction(null);
		assertEquals(false, view.upEnabled);
		assertEquals(true, view.downEnabled);
	}

	@Test
	public void testTopLevelMaxChange() {
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 10);
		presenter.bind();
		assertFalse(view.upEnabled);
		assertTrue(view.downEnabled);
		view.topHandle.onAction(12);
		assertTrue(view.upEnabled);
		assertTrue(view.downEnabled);
	}

	@Test
	public void testSetLevelHandle() {
		data.intHash.put(Persistance.VAR_TOTAL_GEAR, 2);
		data.dataMap.put(1, new GearItemData() {

			@Override
			public int getID() {
				return 1;
			}

			@Override
			public int getBonus() {
				return 2;
			}

			@Override
			public String getArmorType() {
				return "Arm";
			}
		});
		presenter.bind();
		assertEquals("3", view.fightScore);
		view.levelHandle.onAction(4);
		assertEquals("4", view.levelScore);
		assertEquals("6", view.fightScore);
	}
	
	@Test
	public void testMaxChangeTooLow(){
		data.intHash.put(Persistance.VAR_TOPLEVEL, 9);
		data.intHash.put(Persistance.VAR_PLAYER_LEVEL_LAST, 7);
		presenter.bind();
		view.topHandle.onAction(6);
		assertEquals("6", view.levelScore);
		
		assertEquals("6", view.fightScore);
	}

}

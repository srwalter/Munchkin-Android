package com.slinkman.munchkin.presenter;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.slinkman.munchkin.apis.Populator;
import com.slinkman.munchkin.apis.data.GearData;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.view.GearItemView;
import com.slinkman.munchkin.apis.view.GearView;
import com.slinkman.munchkin.mocks.data.GearDataMock;
import com.slinkman.munchkin.mocks.view.GearItemViewMock;
import com.slinkman.munchkin.mocks.view.GearViewMock;

public class GearTest {

	Injector injector;
	GearPresenter presenter;
	GearViewMock view;
	GearDataMock data;
	int totalTest;

	public GearTest() {
		injector = Guice.createInjector(new AbstractModule() {
			protected void configure() {
				bind(new TypeLiteral<GearView<?>>(){}).to(GearViewMock.class);
				bind(GearData.class).to(GearDataMock.class);
			}
		});
	}

	@Before
	public void setup() {
		presenter = injector.getInstance(GearPresenter.class);
		view = (GearViewMock) presenter.view;
		data = (GearDataMock) presenter.data;
	}

	@Test
	public void testInit() {
		presenter.bind();
		assertNotNull(view.clearHandle);
		assertNotNull(view.displayHandle);
		assertNotNull(view.refreshHandle);
	}

	@Test
	public void testAdd() {
		presenter.bind();
		view.displayHandle.onAction(new ChangingGearItem(1, "Head"));

		assertEquals(1, data.dataMap.size());
		assertEquals("Head", data.dataMap.get(0).getArmorType());
		assertEquals(1, data.dataMap.get(0).getBonus());
	}

	@Test
	public void testOnePopulator() {
		presenter.bind();
		view.displayHandle.onAction(new ChangingGearItem(1, "Head"));

		// Mock a view for population
		final GearItemViewMock viewMock = new GearItemViewMock();

		// View requests the population of the view. Targets first item in the
		// list
		view.populator.onAction(new Populator<GearItemView<?>>() {
			@Override
			public int getID() {
				return 0;
			}

			@Override
			public GearItemView<?> getView() {
				return viewMock;
			}
		});

		// Ensure that view was populated
		assertNotNull(viewMock.delete);
		assertNotNull(viewMock.edit);
		assertEquals("1", viewMock.bonus);
		assertEquals("Head", viewMock.armor);
	}
	
	@Test
	public void testTwoPopulator(){
		presenter.bind();
		view.displayHandle.onAction(new ChangingGearItem( 1, "Head"));
		view.displayHandle.onAction(new ChangingGearItem( 2, "Foot"));
		assertEquals("Head", data.dataMap.get(0).getArmorType());
		assertEquals(1, data.dataMap.get(0).getBonus());
		assertEquals("Foot", data.dataMap.get(1).getArmorType());
		assertEquals(2, data.dataMap.get(1).getBonus());
	}
	
	@Test
	public void testDelete(){
		presenter.bind();
		view.displayHandle.onAction(new ChangingGearItem( 1, "Head"));
		view.displayHandle.onAction(new ChangingGearItem( 2, "Foot"));
		final GearItemViewMock viewMock = new GearItemViewMock();
		view.populator.onAction(new Populator<GearItemView<?>>() {
			@Override
			public int getID() {
				// TODO Auto-generated method stub
				return 0;
			}
			@Override
			public GearItemView<?> getView() {
				// TODO Auto-generated method stub
				return viewMock;
			}
		});
		viewMock.delete.onAction(null);
		assertNull(data.dataMap.get(0));
		assertEquals("Foot", data.dataMap.get(1).getArmorType());
		assertEquals(2, data.dataMap.get(1).getBonus());
	}
	
	@Test
	public void testDeleteAdd(){
		presenter.bind();
		view.displayHandle.onAction(new ChangingGearItem( 1, "Head"));
		view.displayHandle.onAction(new ChangingGearItem( 2, "Foot"));
		final GearItemViewMock viewMock = new GearItemViewMock();
		view.populator.onAction(new Populator<GearItemView<?>>() {
			@Override
			public int getID() {
				// TODO Auto-generated method stub
				return 0;
			}
			@Override
			public GearItemView<?> getView() {
				// TODO Auto-generated method stub
				return viewMock;
			}
		});
		viewMock.delete.onAction(null);
		view.displayHandle.onAction(new ChangingGearItem(2, "Armor"));
		assertEquals("Armor", data.dataMap.get(2).getArmorType());
		assertEquals(2, data.dataMap.get(2).getBonus());
	}
	
	@Test
	public void testEdit(){
		presenter.bind();
		view.displayHandle.onAction(new ChangingGearItem(1,"Head"));
		final GearItemViewMock viewMock= new GearItemViewMock();
		view.populator.onAction(new Populator<GearItemView<?>>() {
			@Override
			public int getID() {
				return 0;
			}
			@Override
			public GearItemView<?> getView() {
				return viewMock;
			}
		});
		
		viewMock.edit.onAction(null);
		assertEquals(0, view.passedId);
		view.displayHandle.onAction(new ChangingGearItem(view.passedId, 2, "Feet"));
		assertEquals(2, data.dataMap.get(0).getBonus());
		assertEquals("Feet", data.dataMap.get(0).getArmorType());
	}

	class ChangingGearItem implements GearItemData {

		int id;
		int bonus;
		String armor;

		public ChangingGearItem() {
			id = -1;
			bonus = -1;
			armor = null;
		}
		
		public ChangingGearItem(int bonus, String armor){
			this.id = -1;
			this.bonus = bonus;
			this.armor = armor;
		}

		public ChangingGearItem(int id, int bonus, String armor) {
			this.id = id;
			this.bonus = bonus;
			this.armor = armor;
		}

		@Override
		public int getID() {
			return id;
		}

		@Override
		public String getArmorType() {
			return armor;
		}

		@Override
		public int getBonus() {
			return bonus;
		}

	}
}

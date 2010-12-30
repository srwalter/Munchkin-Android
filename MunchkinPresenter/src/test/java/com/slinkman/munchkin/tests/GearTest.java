package com.slinkman.munchkin.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.mocks.data.GearDataMock;
import com.slinkman.munchkin.mocks.view.GearViewMock;
import com.slinkman.munchkin.presenter.GearPresenter;

public class GearTest {

	private GearViewMock view;
	private GearDataMock data;
	private Presenter presenter;

	@Before
	public void setup() {
		view = new GearViewMock();
		data = new GearDataMock();
		presenter = new GearPresenter(view, data);
	}

	@Test
	public void coldStart() {
		assertNotNull(view.listenerMap.get(GearPresenter.LISTENER_CLEAR_GEAR));
		assertNotNull(view.listenerMap.get(GearPresenter.LISTENER_NEW_GEAR));
	}

	@Test
	public void addGear(){
		view.listenerMap.get(GearPresenter.LISTENER_NEW_GEAR).onAction(null);
		assertNotNull(view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR));
		Object[] objectArray = {new Integer(2),"Hand"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR).onAction(objectArray);
		assertNotNull(view.viewMap.get(0));
		assertNotNull(view.viewMap.get(0).listenerMap.get(GearPresenter.LIST_LISTENER_DELETE));
		assertNotNull(view.viewMap.get(0).listenerMap.get(GearPresenter.LIST_LISTENER_EDIT));
		assertEquals("Hand",view.viewMap.get(0).textMap.get(GearPresenter.LIST_TEXT_ARMOR_TYPE));
		assertEquals("2",view.viewMap.get(0).textMap.get(GearPresenter.LIST_TEXT_BONUS));
	}
	
	@Test
	public void removeGear(){
		view.listenerMap.get(GearPresenter.LISTENER_NEW_GEAR).onAction(null);
		Object[] objectArray = {new Integer(2),"Hand"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR).onAction(objectArray);
		view.viewMap.get(0).listenerMap.get(GearPresenter.LIST_LISTENER_DELETE).onAction(null);
		assertNull(view.viewMap.get(0));
	}
	
	@Test
	public void removeTopGear(){
		view.listenerMap.get(GearPresenter.LISTENER_NEW_GEAR).onAction(null);
		Object[] objectArray = {new Integer(2),"Hand"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR).onAction(objectArray);
		Object[] objectArray2 = {new Integer(5),"2 Hand"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR).onAction(objectArray2);
		view.viewMap.get(0).listenerMap.get(GearPresenter.LIST_LISTENER_DELETE).onAction(null);
		assertEquals("2 Hand", view.viewMap.get(0).textMap.get(GearPresenter.LIST_TEXT_ARMOR_TYPE));
		assertEquals("5", view.viewMap.get(0).textMap.get(GearPresenter.LIST_TEXT_BONUS));
	}
	
	@Test
	public void clearGear(){
		view.listenerMap.get(GearPresenter.LISTENER_NEW_GEAR).onAction(null);
		Object[] objectArray = {new Integer(2),"Hand"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR).onAction(objectArray);
		view.listenerMap.get(GearPresenter.LISTENER_CLEAR_GEAR).onAction(null);
		assertNull(view.viewMap.get(0));
	}
	
	@Test
	public void editItem(){
		view.listenerMap.get(GearPresenter.LISTENER_NEW_GEAR).onAction(null);
		Object[] objectArray = {new Integer(2),"Hand"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR).onAction(objectArray);
		view.viewMap.get(0).listenerMap.get(GearPresenter.LIST_LISTENER_EDIT).onAction(null);
		Object[] array2 = {new Integer(3), "Footgear"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_GEAR_ITEM).onAction( array2);
		assertNotNull(view.viewMap.get(0));
		assertNotNull(view.viewMap.get(0).listenerMap.get(GearPresenter.LIST_LISTENER_DELETE));
		assertNotNull(view.viewMap.get(0).listenerMap.get(GearPresenter.LIST_LISTENER_EDIT));
		assertEquals("Footgear",view.viewMap.get(0).textMap.get(GearPresenter.LIST_TEXT_ARMOR_TYPE));
		assertEquals("3",view.viewMap.get(0).textMap.get(GearPresenter.LIST_TEXT_BONUS));
	}
	
	
	
	@Test
	public void infoSave(){
		view.listenerMap.get(GearPresenter.LISTENER_NEW_GEAR).onAction(null);
		Object[] objectArray = {new Integer(2),"Hand"};
		view.returnMap.get(GearPresenter.RETURN_LISTENER_NEW_GEAR).onAction(objectArray);
		presenter.onPause();
		assertNotNull(data.objectMap.get(GearPresenter.DATA_CAST_ARMOR_ARRAY));
		assertNotNull(data.objectMap.get(GearPresenter.DATA_CAST_BONUS_ARRAY));
	}
	
	
}

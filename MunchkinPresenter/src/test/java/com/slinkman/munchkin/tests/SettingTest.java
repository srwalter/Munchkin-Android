package com.slinkman.munchkin.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.mocks.data.BaseDataMock;
import com.slinkman.munchkin.mocks.view.SettingViewMock;
import com.slinkman.munchkin.presenter.SettingPresenter;


public class SettingTest {

	SettingViewMock view;
	BaseDataMock data;
	Presenter presenter;

	private final static int warmLimit = 12;
	private final static int coldLimit = 10;
	private final static int limitChange = 13;
	
	@Before
	public void setup() {
		view = new SettingViewMock();
		data = new BaseDataMock();
		presenter = new SettingPresenter(view, data);
	}

	@Test
	public void coldStart() {
		assertEquals(Integer.toString(coldLimit), view.textMap.get(SettingPresenter.TEXT_LEVEL_LIMIT));
	}

	@Test
	public void warmStart() {
		data.dataHash.put(Persistance.VAR_TOPLEVEL, warmLimit);
		presenter = new SettingPresenter(view, data);
		assertEquals(Integer.toString(warmLimit), view.textMap.get(SettingPresenter.TEXT_LEVEL_LIMIT));
	}

	@Test
	public void itemTouch() {
		view.listenerMap.get(SettingPresenter.LISTENER_MAX_ITEM).onAction(null);
		assertNotNull(view.returnMap.get(SettingPresenter.RETURN_CHANGE_DIALOG));
	}

	@Test
	public void limitChange() {
		// Setup Return Listener
		view.listenerMap.get(SettingPresenter.LISTENER_MAX_ITEM).onAction(null);
		view.returnMap.get(SettingPresenter.RETURN_CHANGE_DIALOG).onAction(limitChange);
		assertEquals(Integer.toString(limitChange), view.textMap.get(SettingPresenter.TEXT_LEVEL_LIMIT));
	}
	
	@Test
	public void limtChangeSave(){
		// Setup Return Listener
		view.listenerMap.get(SettingPresenter.LISTENER_MAX_ITEM).onAction(null);
		view.returnMap.get(SettingPresenter.RETURN_CHANGE_DIALOG).onAction(limitChange);
		presenter.onPause();
		assertEquals(limitChange, data.getSaveMap().get(Persistance.VAR_TOPLEVEL));
	}
}

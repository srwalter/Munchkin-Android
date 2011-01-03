package com.slinkman.munchkin.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.mocks.data.DiceDataMock;
import com.slinkman.munchkin.mocks.view.DiceViewMock;
import com.slinkman.munchkin.presenter.DicePresenter;

public class DiceTest {

	DiceViewMock view;
	DiceDataMock data;
	Presenter presenter;

	private int existingResource = DicePresenter.RESOURCE_FOUR;

	@Before
	public void setup() {
		view = new DiceViewMock();
		data = new DiceDataMock();
		presenter = new DicePresenter(view, data);
	}

	public void warmStart() {
		data.dataHash.put(Persistance.VAR_DICE_LAST, existingResource);
		presenter = new DicePresenter(view, data);
	}

	@Test
	public void coldLoad() {
		assertEquals(DicePresenter.RESOURCE_ONE,
				(int) view.resourceMap.get(DicePresenter.RESOURCE_TARGET_DICE));
	}

	@Test
	public void rollAction() {
		int tempCache = view.clickCache.get(DicePresenter.LISTENER_ROLL_CLICK);
		view.listenerMap.get(DicePresenter.LISTENER_ROLL_CLICK).onAction(null);
		if (tempCache == view.clickCache.get(DicePresenter.LISTENER_ROLL_CLICK))
			fail();

		assertNotNull(view.listenerMap.get(DicePresenter.LISTENER_ROLL_CLICK));
		assertNotNull(view.resourceMap.get(DicePresenter.RESOURCE_TARGET_DICE));
	}

	@Test
	public void coldLoadPause() {
		presenter.onPause();
		assertEquals(1, data.dataHash.get(Persistance.VAR_DICE_LAST));
	}

	@Test
	public void warmLoad() {
		warmStart();
		assertEquals(existingResource,
				(int) view.resourceMap.get(DicePresenter.RESOURCE_TARGET_DICE));
	}

	@Test
	public void warmRollAction() {
		warmStart();
		rollAction();
	}

	@Test
	public void shakeAction() {
		int temp = view.clickCache.get(DicePresenter.LISTENER_SHAKE);
		assertNotNull(view.listenerMap.get(DicePresenter.LISTENER_SHAKE));
		view.listenerMap.get(DicePresenter.LISTENER_SHAKE).onAction(null);
		if (temp == view.clickCache.get(DicePresenter.LISTENER_SHAKE))
			fail();
		assertNotNull(view.listenerMap.get(DicePresenter.LISTENER_SHAKE));
		assertNotNull(view.resourceMap.get(DicePresenter.RESOURCE_TARGET_DICE));
	}

}

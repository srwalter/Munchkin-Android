package com.slinkman.munchkin.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.ReturnListener;
import com.slinkman.munchkin.mocks.data.GearDataMock;
import com.slinkman.munchkin.mocks.view.GearDialogMock;
import com.slinkman.munchkin.presenter.GearDialogPresenter;

public class GearDialogTest {
	GearDataMock data;
	GearDialogMock temp;
	Presenter presenter;
	class ReturnAccess implements ReturnListener<Object[]>{
		Object[] accessVar;
		@Override
		public void onAction(Object[] inObject) {
			accessVar = inObject;
		}
	}
	ReturnAccess returnObject = new ReturnAccess();
	ReturnListener<Void> testReturn = new ReturnListener<Void>() {
		@Override
		public void onAction(Void inObject) {
			// TODO Auto-generated method stub

		}
	};

	@Before
	public void setup() {
		temp = new GearDialogMock();
		data = new GearDataMock();
		presenter = new GearDialogPresenter(temp, data, returnObject);
	}

	@Test
	public void initialSetupTest() {
		temp.integerReturnMap.get(GearDialogPresenter.RETURN_LISTENER_BONUS)
				.onAction(3);
		temp.stringReturnMap.get(GearDialogPresenter.RETURN_LISTENER_ARMORTYPE)
				.onAction("Armor");
		temp.listenerMap.get(GearDialogPresenter.LISTENER_ADD).onAction();
		assertEquals( 3, returnObject.accessVar[0] );
		assertEquals("Armor", returnObject.accessVar[1]);
	}
}

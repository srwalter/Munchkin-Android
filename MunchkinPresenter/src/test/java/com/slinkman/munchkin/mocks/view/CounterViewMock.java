package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.presenter.CounterPresenter.CountView;

/**
 * Simple mock for the CountView interface. 
 * All internal variable are public for access in integration tests
 * @author chrisslinkman
 *
 */
public class CounterViewMock implements CountView {
	
	public String counterText;
	public Listener<Void> upHandle;
	public Listener<Void> downHandle;
	public boolean upEnabled;
	public boolean downEnabled;

	@Override
	public void setCounterText(String inString) {
		counterText = inString;

	}

	@Override
	public void setUpListener(Listener<Void> handle) {
		upHandle = handle;
	}

	@Override
	public void setDownListener(Listener<Void> handle) {
		downHandle = handle;
	}

	@Override
	public void setUpEnabled(boolean enabled) {
		upEnabled = enabled;
	}

	@Override
	public void setDownEnabled(boolean enabled) {
		downEnabled = enabled;
	}

}

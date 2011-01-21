package com.slinkman.munchkin.mocks.view;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.presenter.SettingPresenter.SettingView;

public class SettingViewMock implements SettingView {

	Listener<Integer> dialogHandle;
	String maxText;

	@Override
	public void setDialog(Listener<Integer> handle) {
		dialogHandle = handle;

	}

	@Override
	public void setMaxText(String inString) {
		maxText = inString;

	}

}

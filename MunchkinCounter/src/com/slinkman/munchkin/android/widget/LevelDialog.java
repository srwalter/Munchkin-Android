package com.slinkman.munchkin.android.widget;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.MaxLevelDialogPresenter;
import com.slinkman.munchkin.presenter.MaxLevelDialogPresenter.MaxLevelDialogViewInterface;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevelDialog extends Dialog implements MaxLevelDialogViewInterface {

	Listener<Integer> myListener;
	int limit;
	Button upButton;
	Button downButton;
	Button doneButton;
	TextView currentLevel;
	MaxLevelDialogPresenter presenter;

	public LevelDialog(Context context, Persistance data,
			Listener<Integer> returnListener) {
		super(context);
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myListener = returnListener;
		View dialogView = inflator.inflate(R.layout.level_dialog, null);
		setContentView(dialogView);
		setTitle("Set Max Level");
		doneButton = (Button) dialogView.findViewById(R.id.dialog_done);
		upButton = (Button) dialogView.findViewById(R.id.dialog_up);
		downButton = (Button) dialogView.findViewById(R.id.dialog_down);
		currentLevel = (TextView) dialogView
				.findViewById(R.id.dialog_top_level);
		presenter = new MaxLevelDialogPresenter(this, data, returnListener);
		show();
	}

	@Override
	public void setListener(int objectID, final Listener<Void> inListener)
			throws WidgetError {
		switch (objectID) {
		case MaxLevelDialogPresenter.LISTENER_UP:
			upButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case MaxLevelDialogPresenter.LISTENER_DOWN:
			downButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case MaxLevelDialogPresenter.LISTENER_DONE:
			doneButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					inListener.onAction(null);
					presenter.onPause();
					dismiss();
				}
			});
			break;
		default:
			throw new WidgetError();
		}

	}

	@Override
	public void setWidgetEnabled(int objectID, boolean inEnabled)
			throws WidgetError {
		switch (objectID) {
		case MaxLevelDialogPresenter.ENABLE_DOWN:

			break;
		default:
			throw new WidgetError();
		}

	}

	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID) {
		case MaxLevelDialogPresenter.TEXT_TOP_LEVEL:
			currentLevel.setText("Max Level: " + inText);
			break;
		default:
			throw new WidgetError();
		}
	}
}

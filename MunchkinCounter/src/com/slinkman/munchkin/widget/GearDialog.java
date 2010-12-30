package com.slinkman.munchkin.widget;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.ReturnListener;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearDialogPresenter;
import com.slinkman.munchkin.presenter.GearDialogPresenter.GearDialogViewInterface;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class GearDialog extends Dialog implements GearDialogViewInterface {

	private Button addButton;
	private Button cancelButton;
	private EditText bonusText;
	private Spinner armorText;

	int targetID;

	public GearDialog(Context context, Persistance data,
			ReturnListener<Object[]> returnListener, int id) {
		super(context);
		targetID = id;
		init(data, returnListener);
	}

	public GearDialog(Context context, Persistance data,
			ReturnListener<Object[]> returnListener) {
		super(context);
		init(data, returnListener);
	}

	public void init(Persistance data, ReturnListener<Object[]> returnListener) {
		LayoutInflater inflator = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialogView = inflator.inflate(R.layout.gear_dialog, null);
		setContentView(dialogView);
		setTitle("Add New Gear Information");
		addButton = (Button) dialogView
				.findViewById(R.id.gear_dialog_add_button);
		cancelButton = (Button) dialogView
				.findViewById(R.id.gear_dialog_cancel_button);
		bonusText = (EditText) dialogView
				.findViewById(R.id.gear_dialog_bonus_text);
		armorText = (Spinner) dialogView
				.findViewById(R.id.gear_dialog_armor_type);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getContext(), R.array.armor_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		armorText.setAdapter(adapter);
		new GearDialogPresenter(this, data, returnListener);
	}

	@Override
	public void setListener(int objectID, final Listener inListener)
			throws WidgetError {
		switch (objectID) {
		case GearDialogPresenter.LISTENER_ADD:
			addButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					inListener.onAction();
					dismiss();
				}
			});
			break;
		case GearDialogPresenter.LISTENER_CANCEL:
			cancelButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					inListener.onAction();
					dismiss();
				}
			});
			break;
		default:
			throw new WidgetError();
		}
	}


	@Override
	public void setWidgetText(int objectID, String inText) throws WidgetError {
		switch (objectID) {
		case GearDialogPresenter.TEXT_ARMOR:
			armorText.setSelection(3);
			// armorText.setText(inText);
			break;
		case GearDialogPresenter.TEXT_BONUS:
			bonusText.setText(inText);
			break;
		default:
			throw new WidgetError();
		}

	}

	@Override
	public void setStringReturnListener(int objectID,
			final ReturnListener<String> inListener) throws WidgetError {
		switch (objectID) {
		case GearDialogPresenter.RETURN_LISTENER_ARMORTYPE:
			armorText.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					inListener.onAction((String)armorText.getSelectedItem());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

			break;
		default:
			throw new WidgetError();
		}
		
	}

	@Override
	public void setIntegerReturnListener(int objectID,
			final ReturnListener<Integer> inListener) throws WidgetError {
		switch (objectID) {
		case GearDialogPresenter.RETURN_LISTENER_BONUS:
			bonusText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					try {
						if (!s.toString().equals("-"))
							inListener.onAction(Integer.parseInt(s.toString()));
					} catch (Exception ex) {
						Log.i("Bad Input", "Bad");
					}

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});
			break;
		default:
			throw new WidgetError();
		}
		
	}

}

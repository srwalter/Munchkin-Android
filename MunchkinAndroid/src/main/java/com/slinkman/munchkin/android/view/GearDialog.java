package com.slinkman.munchkin.android.view;

import com.slinkman.munchkin.android.R;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.data.GearItemData;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class GearDialog extends Dialog {

	public GearDialog(Context context, final Listener<GearItemData> inGear,
			final int gearID) {
		super(context);
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewHandle = inflator.inflate(R.layout.gear_dialog, null);
		setContentView(viewHandle);
		Button addButton = (Button) viewHandle
				.findViewById(R.id.gear_dialog_add_button);
		final EditText bonus = (EditText) viewHandle
				.findViewById(R.id.gear_dialog_bonus_text);
		final Spinner armor = (Spinner) viewHandle
				.findViewById(R.id.gear_dialog_armor_type);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				context, R.array.armor_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		armor.setAdapter(adapter);
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				inGear.onAction(new GearItemData() {

					@Override
					public int getID() {
						return gearID;
					}

					@Override
					public int getBonus() {
						return Integer.parseInt(bonus.getText().toString());
					}

					@Override
					public String getArmorType() {
						return (String) armor.getSelectedItem();
					}
				});
				dismiss();
			}
		});
		Button cancel = (Button) viewHandle
				.findViewById(R.id.gear_dialog_cancel_button);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		setTitle("Edit Gear Information");
	}

}

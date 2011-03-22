package com.slinkman.munchkin.android.view;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.apis.Listener;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MonsterDialog extends Dialog {

	public MonsterDialog(Context context, final Listener<Integer> monsterHandle) {
		super(context);
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewHandle = inflator.inflate(R.layout.monster_dialog, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(viewHandle);
		Button setButton = (Button) viewHandle
				.findViewById(R.id.monster_dialog_set_button);

		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				context.getString(R.string.typeface));
		TextView titleView = (TextView) viewHandle
				.findViewById(R.id.monster_dialog_title);
		titleView.setTypeface(tf);

		final EditText inValue = (EditText) viewHandle
				.findViewById(R.id.monster_dialog_bonus_text);
		setButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Integer passValue = Integer.parseInt(inValue.getText()
						.toString());
				monsterHandle.onAction(passValue);
				dismiss();
			}
		});
		Button cancelButton = (Button) viewHandle
				.findViewById(R.id.monster_dialog_cancel_button);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();

			}
		});
		Window window = getWindow();
		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

	}
}

package com.slinkman.munchkin.android.view;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.apis.Listener;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;

public class LevelDialog extends Dialog {

	public LevelDialog(Context context, int topLevel, int currentLevel,
			final Listener<Integer> topHandle,
			final Listener<Integer> currentHandle) {
		super(context);
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewHandle = inflator.inflate(R.layout.level_dialog, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(viewHandle);
		final EditText topLevelEdit = (EditText) viewHandle
				.findViewById(R.id.level_dialog_top_level);
		topLevelEdit.setText(Integer.toString(topLevel));
		final EditText curLevelEdit = (EditText) viewHandle
				.findViewById(R.id.level_dialog_cur_level);
		curLevelEdit.setText(Integer.toString(currentLevel));
		Button setButton = (Button) viewHandle
				.findViewById(R.id.level_dialog_set);
		setButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Integer levelIn = null;
				Integer topIn = null;
				try {
					if (currentHandle != null)
						levelIn = Integer.parseInt(curLevelEdit.getText()
								.toString());
					if (topHandle != null)
						topIn = Integer.parseInt(topLevelEdit
								.getText().toString());
				} catch (NumberFormatException ex) {
					curLevelEdit.setText("");
					topLevelEdit.setText("");
				} finally {
					if (levelIn != null && topIn != null){
						currentHandle.onAction(levelIn);
						topHandle.onAction(topIn);
					}
				}
				dismiss();
			}
		});
		Button cancelButton = (Button) viewHandle
				.findViewById(R.id.level_dialog_cancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		Window window = getWindow();
		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	}

}

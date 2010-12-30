package com.slinkman.munchkin.viewbind;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class GearItemAndroid implements GearItemView{

	private TextView bonusText;
	private TextView armorText;
	private ImageView xButton;
	private View myView;
	public GearItemAndroid(View inView) {
		bonusText = (TextView) inView.findViewById(R.id.gear_item_bonus_text);
		armorText = (TextView) inView.findViewById(R.id.gear_item_armor_type);
		xButton = (ImageView) inView.findViewById(R.id.gear_item_x);
		myView = inView;
	}

	public void setListener(int objectID, final Listener inListener)
			throws WidgetError {
		switch (objectID){
		case GearPresenter.LIST_LISTENER_EDIT:
			myView.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					inListener.onAction();
				}
			});
			break;
		case GearPresenter.LIST_LISTENER_DELETE:
			xButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					inListener.onAction();
				}
			});
			break;
		}
		
	}

	public void setWidgetText(int objectID, final String inText) throws WidgetError {
		switch (objectID){
		case GearPresenter.LIST_TEXT_ARMOR_TYPE:
			armorText.setText(inText);
			break;
		case GearPresenter.LIST_TEXT_BONUS:
			bonusText.setText(inText);
			break;
		}
		
	}
}

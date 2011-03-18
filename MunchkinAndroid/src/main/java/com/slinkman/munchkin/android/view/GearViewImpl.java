package com.slinkman.munchkin.android.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.slinkman.munchkin.android.Mover;
import com.slinkman.munchkin.android.R;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.Populator;
import com.slinkman.munchkin.apis.data.GearItemData;
import com.slinkman.munchkin.apis.view.GearItemView;
import com.slinkman.munchkin.apis.view.GearView;

public class GearViewImpl implements GearView<View> {

	View viewHandle;

	Listener<GearItemData> displayHandle;

	Context context;

	Listener<Populator<GearItemView<?>>> popHandle;

	LayoutInflater inflator;

	GearAdapter adapter;

	int countView = 0;

	ListView list;

	Button addButton;

	Button clearButton;

	Typeface typeface;

	@Inject
	GearViewImpl(LayoutInflater inflator, Context context, final Mover move) {
		viewHandle = inflator.inflate(R.layout.gear_layout, null);
		list = (ListView) viewHandle.findViewById(R.id.gear_list);
		adapter = new GearAdapter();
		list.setAdapter(adapter);
		this.inflator = inflator;
		this.context = context;
		typeface = Typeface.createFromAsset(context.getAssets(),
				context.getString(R.string.typeface));
		addButton = (Button) viewHandle.findViewById(R.id.gear_add_gear);
		addButton.setTypeface(typeface);
		clearButton = (Button) viewHandle.findViewById(R.id.gear_clear_gear);
		clearButton.setTypeface(typeface);
		ImageView backButton = (ImageView) viewHandle
				.findViewById(R.id.gear_back_button);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				move.moveToSummary();

			}
		});
	}

	public void displayGearWindow(int gearID) {
		GearDialog dialog = new GearDialog(context, displayHandle, gearID);
		dialog.show();
	}

	public void setDisplayHandle(Listener<GearItemData> handle) {
		displayHandle = handle;
		Button addButton = (Button) viewHandle.findViewById(R.id.gear_add_gear);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				displayGearWindow(-1);

			}
		});
	}

	public void setClearGear(final Listener<Void> handle) {
		Button clear = (Button) viewHandle.findViewById(R.id.gear_clear_gear);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				handle.onAction(null);

			}
		});
	}

	public Listener<Integer> getRefreshList() {
		return new Listener<Integer>() {

			@Override
			public void onAction(Integer inObject) {
				Log.i("GearViewImpl", "Changing Item Count " + inObject);
				countView = inObject;
				adapter.notifyDataSetChanged();
			}
		};
	}

	public void setPopulator(Listener<Populator<GearItemView<?>>> handle) {
		popHandle = handle;

	}

	public View getHandle() {
		return viewHandle;
	}

	class GearAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return countView;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			final View adaView = inflator.inflate(R.layout.gear_item, null);
			final GearItemView<View> tempView = new GearItemView<View>() {

				String bonus = "";
				String armor = "";

				@Override
				public View getHandle() {
					return adaView;
				}

				@Override
				public void setEditListner(final Listener<Void> handle) {
					adaView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							handle.onAction(null);

						}
					});
				}

				@Override
				public void setDeleteListener(final Listener<Void> handle) {
					ImageView deleteButton = (ImageView) adaView
							.findViewById(R.id.gear_item_x);
					deleteButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							handle.onAction(null);
						}
					});

				}

				@Override
				public void setBonusText(String inString) {
					bonus = inString;
					TextView item = (TextView) adaView
							.findViewById(R.id.gear_item_bonus_text);
					item.setTypeface(typeface);
					item.setText(inString);
					if (!armor.equals(""))
						adaView.setVisibility(View.VISIBLE);
				}

				@Override
				public void setArmorText(String inString) {
					armor = inString;
					TextView armor = (TextView) adaView
							.findViewById(R.id.gear_item_armor_type);
					armor.setTypeface(typeface);
					armor.setText(inString);
					if (!bonus.equals(""))
						adaView.setVisibility(View.VISIBLE);
				}
			};
			popHandle.onAction(new Populator<GearItemView<?>>() {

				@Override
				public GearItemView<?> getView() {
					return tempView;
				}

				@Override
				public int getID() {
					return arg0;
				}
			});
			return adaView;
		}

	}
}

package com.slinkman.munchkin.android.activity;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.slinkman.munchkin.Listener;
import com.slinkman.munchkin.ParameterListener;
import com.slinkman.munchkin.Persistance;
import com.slinkman.munchkin.Presenter;
import com.slinkman.munchkin.R;
import com.slinkman.munchkin.android.data.GearDataImpl;
import com.slinkman.munchkin.android.viewbind.GearItemAndroid;
import com.slinkman.munchkin.android.widget.BaseActivity;
import com.slinkman.munchkin.android.widget.GearDialog;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearDialogPresenter;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;
import com.slinkman.munchkin.presenter.GearPresenter.GearView;

public class Gear extends BaseActivity implements GearView {

	// Widgets
	private Button addGear;
	private Button clearGear;
	private ListView gearList;
	private GearAdapter adapter;

	// Presenter Handles
	Presenter presenter;
	GearDataImpl data;
	ParameterListener<GearItemView> populator;
	int listCount;

	@Override
	protected Presenter bindMainView(View inView) {
		addGear = (Button) inView.findViewById(R.id.gear_add_gear);
		clearGear = (Button) inView.findViewById(R.id.gear_clear_gear);
		gearList = (ListView) inView.findViewById(R.id.gear_list);

		data = new GearDataImpl(this);
		presenter = new GearPresenter(this, data);
		return null;
	}

	@Override
	public int getActivityID() {
		return BaseActivity.ACTIVITY_GEAR;
	}

	@Override
	protected View getMainView() {
		return inflator.inflate(R.layout.gear_layout, null);
	}

	@Override
	public void setReturnListener(int objectID, final Listener<Object[]> inListener)
			throws WidgetError {
		GearDialog temp;
		switch (objectID) {
		case GearPresenter.RETURN_LISTENER_NEW_GEAR:
			temp = new GearDialog(this, data, inListener);
			temp.show();
			// TODO: Gear Entry Dialog
			break;
		case GearPresenter.RETURN_LISTENER_GEAR_ITEM:
			temp = new GearDialog(this, data, inListener);
			temp.setWidgetText(GearDialogPresenter.TEXT_ARMOR, (String) data
					.getSaveMap().get(Persistance.VAR_ITEM_EDIT_ARMOR));
			temp.setWidgetText(
					GearDialogPresenter.TEXT_BONUS,
					Integer.toString((Integer) data.getSaveMap().get(
							Persistance.VAR_ITEM_EDIT_BONUS)));
			temp.show();
			break;
		}
	}

	@Override
	public void setListener(int objectID, final Listener<Void> inListener)
			throws WidgetError {
		switch (objectID) {
		case GearPresenter.LISTENER_CLEAR_GEAR:
			clearGear.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
			break;
		case GearPresenter.LISTENER_NEW_GEAR:
			addGear.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					inListener.onAction(null);
				}
			});
		}

	}

	@Override
	public Listener<Integer> getRefreshList() {
		return new Listener<Integer>() {

			@Override
			public void onAction(Integer inObject) {
				listCount = inObject;
				if (gearList.getAdapter() == null) {
					adapter = new GearAdapter();
					gearList.setAdapter(adapter);
				} else
					adapter.notifyDataSetChanged();
			}
		};
	}

	@Override
	public void setPopulator(ParameterListener<GearItemView> inListener) {
		populator = inListener;

	}

	private class GearAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listCount;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = inflator.inflate(R.layout.gear_item, null);
			GearItemAndroid item = new GearItemAndroid(convertView);
			populator.onAction(position,item);

			return convertView;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		presenter.onPause();
	}
}

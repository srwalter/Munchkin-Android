package com.slinkman.munchkin.activity;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.slinkman.munchkin.R;
import com.slinkman.munchkin.baseinterface.Listener;
import com.slinkman.munchkin.baseinterface.ParameterReturn;
import com.slinkman.munchkin.baseinterface.Persistance;
import com.slinkman.munchkin.baseinterface.Presenter;
import com.slinkman.munchkin.baseinterface.ReturnListener;
import com.slinkman.munchkin.data.GearDataImpl;
import com.slinkman.munchkin.error.WidgetError;
import com.slinkman.munchkin.presenter.GearDialogPresenter;
import com.slinkman.munchkin.presenter.GearPresenter;
import com.slinkman.munchkin.presenter.GearPresenter.GearItemView;
import com.slinkman.munchkin.presenter.GearPresenter.GearView;
import com.slinkman.munchkin.viewbind.GearItemAndroid;
import com.slinkman.munchkin.widget.BaseActivity;
import com.slinkman.munchkin.widget.GearDialog;

public class Gear extends BaseActivity implements GearView {

	// Widgets
	private Button addGear;
	private Button clearGear;
	private ListView gearList;
	private GearAdapter adapter;

	// Presenter Handles
	Presenter presenter;
	GearDataImpl data;
	ParameterReturn<GearItemView> populator;
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
	public void setReturnListener(int objectID, final ReturnListener<Object[]> inListener)
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
	public void setListener(int objectID, final Listener inListener)
			throws WidgetError {
		switch (objectID) {
		case GearPresenter.LISTENER_CLEAR_GEAR:
			clearGear.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					inListener.onAction();
				}
			});
			break;
		case GearPresenter.LISTENER_NEW_GEAR:
			addGear.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					inListener.onAction();
				}
			});
		}

	}

	@Override
	public ReturnListener<Integer> refreshList() {
		return new ReturnListener<Integer>() {

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
	public void setPopulator(ParameterReturn<GearItemView> inListener) {
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

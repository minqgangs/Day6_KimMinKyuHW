package org.androidtown.calendar.month;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * 어댑터 클래스 정의
 * 
 * @author Mike
 *
 */
public class IconTextListAdapter extends BaseAdapter {

	private Context mContext;

	private List<IconTextItem> mItems = new ArrayList<IconTextItem>();

	public IconTextListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(IconTextItem it) {
		mItems.add(it);
	}

	public void setListItems(List<IconTextItem> lit) {
		mItems = lit;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
		try {
			return mItems.get(position).isSelectable();
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final IconTextView itemView;
		if (convertView == null) {
			itemView = new IconTextView(mContext, mItems.get(position));
		} else {
			itemView = (IconTextView) convertView;
			
			itemView.setIcon(mItems.get(position).getIcon());
			itemView.setText(0, mItems.get(position).getData(0));
			itemView.setText(1, mItems.get(position).getData(1));
		}

		Button btnDel = (Button)itemView.findViewById(R.id.btnDel);

		btnDel.setOnClickListener(new View.OnClickListener() {
				@Override
			public void onClick(View v) {

				Log.d("index","position :" + position);

				removeItem(position);
			}
		});

		return itemView;
	}



	public void removeItem(int position)
	{
		Log.d("index",Integer.toString(position));
		mItems.remove(position);
		this.notifyDataSetChanged();
	}

	public Object getItemDay(int position) {
		return mItems.get(position).getData(0);
	}

}

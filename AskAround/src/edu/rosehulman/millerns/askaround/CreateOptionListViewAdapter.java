package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CreateOptionListViewAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private ArrayList<String> mOptions;
	
	
	public CreateOptionListViewAdapter(Context context, ArrayList<String> options) {
		super(context, R.layout.create_option_row_view, options);
		mContext = context;
		mOptions = options;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CreateOptionRowView rowView;
		if (convertView == null) {
			rowView = new CreateOptionRowView(mContext);
		} else {
			rowView = (CreateOptionRowView) convertView;
		}
		
		rowView.setNewOption(mOptions.get(position));
		
		return rowView;
	}

}

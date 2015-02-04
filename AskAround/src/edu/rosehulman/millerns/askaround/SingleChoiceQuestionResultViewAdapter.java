package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import android.R.anim;
import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

public class SingleChoiceQuestionResultViewAdapter extends ArrayAdapter<Map<String, Integer>> {
	
	private Context mContext;
	private int totalVotes = 0;
	private ArrayList<Map<String, Integer>> mOptionAndVotes;
	private ArrayList<String> mOptions = new ArrayList<String>();
	private ArrayList<Integer> mVotes = new ArrayList<Integer>();

	public SingleChoiceQuestionResultViewAdapter(Context context, ArrayList<Map<String, Integer>> opsVotes) {
		super(context, R.layout.single_choice_row_view, opsVotes);
		mContext = context;
		mOptionAndVotes = opsVotes;
		
		for (Map<String, Integer> map : opsVotes) {
			for (Entry<String, Integer> entry : map.entrySet()) {
				totalVotes += entry.getValue();
				mOptions.add(entry.getKey());
				mVotes.add(entry.getValue());
			}
		}
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		SingleChoiceQuestionResultRowView rowView;
		if (convertView == null) {
			rowView = new SingleChoiceQuestionResultRowView(mContext);
		} else {
			rowView = (SingleChoiceQuestionResultRowView) convertView;
		}
		
		Log.d("RV", mOptions.get(position));
		
		rowView.setOption(mOptions.get(position));
		rowView.setProgressBar(mVotes.get(position), totalVotes);
		return rowView;
		
	}

}

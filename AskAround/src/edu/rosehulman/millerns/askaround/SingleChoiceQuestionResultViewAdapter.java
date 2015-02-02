package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.R.anim;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SingleChoiceQuestionResultViewAdapter extends ArrayAdapter<Question> {
	
	private Context mContext;
	private ArrayList<Question> mQuestions;
	private int optionAt;

	public SingleChoiceQuestionResultViewAdapter(Context context, ArrayList<Question> questions) {
		super(context, R.layout.activity_specific_results);
		mContext = context;
		mQuestions = questions;
		optionAt = 0;
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
		
		rowView.setOption(mQuestions.get(position).getQuestionOptions().get(optionAt));
		rowView.setProgressBar(mQuestions.get(position).getVotes().get(optionAt), mQuestions.get(position).getTotalVotes());		
		return rowView;
		
	}

}

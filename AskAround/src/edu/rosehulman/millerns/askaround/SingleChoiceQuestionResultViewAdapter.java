package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.R.anim;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SingleChoiceQuestionResultViewAdapter extends ArrayAdapter<String> {
	
	private Context mContext;
	private Question mQuestion;

	public SingleChoiceQuestionResultViewAdapter(Context context, Question question) {
		super(context, R.layout.single_choice_row_view);
		mContext = context;
		mQuestion = question;
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
		
		rowView.setOption(mQuestion.getQuestionOptions().get(position));
		rowView.setProgressBar(mQuestion.getVotes().get(position), mQuestion.getTotalVotes());
		return rowView;
		
	}

}

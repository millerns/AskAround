package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SingleChoiceQuestionCommentAdapter extends ArrayAdapter<String> {
	private Context mContext;
	private Question mQuestion;

	public SingleChoiceQuestionCommentAdapter(Context context, Question question) {
		super(context, R.layout.single_choice_comment);
		mContext = context;
		mQuestion = question;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		SingleChoiceQuestionCommentRowView rowView;
		if (convertView == null) {
			rowView = new SingleChoiceQuestionCommentRowView(mContext);
		} else {
			rowView = (SingleChoiceQuestionCommentRowView) convertView;
		}
		
		rowView.setQuestionComment(mQuestion.getComments().get(position));
		
		
		return rowView;
	}

}

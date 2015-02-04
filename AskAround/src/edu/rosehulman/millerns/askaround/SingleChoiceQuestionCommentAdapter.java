package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SingleChoiceQuestionCommentAdapter extends ArrayAdapter<String> {
	private Context mContext;
	//private Question mQuestion;
	private ArrayList<String> mComments;

	public SingleChoiceQuestionCommentAdapter(Context context, ArrayList<String> comments) {
		super(context, R.layout.single_choice_comment, comments);
		mContext = context;
		mComments = comments;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		SingleChoiceQuestionCommentRowView rowView;
		if (convertView == null) {
			rowView = new SingleChoiceQuestionCommentRowView(mContext);
		} else {
			rowView = (SingleChoiceQuestionCommentRowView) convertView;
		}
		
		rowView.setQuestionComment(mComments.get(position));
		
		
		return rowView;
	}

}

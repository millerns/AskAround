package edu.rosehulman.millerns.askaround;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleChoiceQuestionCommentRowView extends LinearLayout {
	private TextView mQuestionComment;

	public SingleChoiceQuestionCommentRowView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.single_choice_comment, this);
		
		mQuestionComment = (TextView) findViewById(R.id.single_choice_comment);
	}

	public void setQuestionComment(String comment) {
		mQuestionComment.setText(comment);
	}
	
}

package edu.rosehulman.millerns.askaround;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BrowseResultsRowView extends LinearLayout {
	
	private TextView mQuestionContent;
	private TextView mQuestionDueTime;
	private TextView mQuestionHotness;
	

	public BrowseResultsRowView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.browse_results_row, this);
		mQuestionContent = (TextView) findViewById(R.id.browse_results_question_textview);
		mQuestionDueTime = (TextView) findViewById(R.id.browse_results_duetime_textview);
		mQuestionHotness = (TextView) findViewById(R.id.browse_results_hotness_textview);
		
	}

		
	public void setQuestionmContent(String question) {
		mQuestionContent.setText(question);
	}
	
	public void setQuestionDueTime(String time) {
		mQuestionDueTime.setText(time);
	}
	
	public void setQuestionHotness(String hotness) {
		mQuestionHotness.setText(hotness);
	}
}

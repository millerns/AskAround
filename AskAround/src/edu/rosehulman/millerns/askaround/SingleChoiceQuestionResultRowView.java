package edu.rosehulman.millerns.askaround;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SingleChoiceQuestionResultRowView extends LinearLayout {
	
	private TextView option;
	private ProgressBar progressBar;
	

	public SingleChoiceQuestionResultRowView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.single_choice_row_view, this);
		option = (TextView) findViewById(R.id.question_option);
		progressBar = (ProgressBar) findViewById(R.id.question_option_progressbar);
	}
	
	public void setOption(String optionName) {
		option.setText(optionName);
	}
	
	public void setProgressBar(int votes, int totalVotes) {
		progressBar.setMax(totalVotes);
		progressBar.setProgress(votes);
	}

}

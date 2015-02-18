package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import com.appspot.dingx_askaround.questions.model.Question;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BrowseResultsViewAdapter extends ArrayAdapter<Question> {
	
	private Context mContext;
	private ArrayList<Question> mQuestions;
	
	

	public BrowseResultsViewAdapter(Context context, ArrayList<Question> questions) {
		super(context, R.layout.browse_results_row, questions);
		mContext = context;
		mQuestions = questions;	
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BrowseResultsRowView rowView;
		if (convertView == null) {
			rowView = new BrowseResultsRowView(mContext);
		} else {
			rowView = (BrowseResultsRowView) convertView;
		}
		
		rowView.setQuestionmContent(mQuestions.get(position).getContent());
		rowView.setQuestionDueTime(Integer.toString(mQuestions.get(position).getVotes().size()));
		rowView.setQuestionHotness(Integer.toString(mQuestions.get(position).getComments().size()));
		
		return rowView;
	}

}


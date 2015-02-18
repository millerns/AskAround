package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SpecificResultsActivity extends Activity {
	private SingleChoiceQuestionResultViewAdapter questionResultAdapter;
	private SingleChoiceQuestionCommentAdapter questionCommentAdapter;
	private ArrayList<String> mComments;
	private ArrayList<String> mOptions;
	private ArrayList<Integer> mVotes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specific_results);

		ListView questionResultListView = (ListView) findViewById(R.id.specific_result_list_view);
		ListView questionCommentListView = (ListView) findViewById(R.id.specific_comments_list_view);
		
		Intent intent = getIntent();
		mComments = intent.getStringArrayListExtra(CurrentQuestionFragment.UPDATED_COMMENTS);
		mOptions = intent.getStringArrayListExtra(CurrentQuestionFragment.UPDATED_QUESTION_OPTIONS);
		mVotes = intent.getIntegerArrayListExtra(CurrentQuestionFragment.UPDATED_QUESTION_VOTES);
		
		ArrayList<Map<String, Integer>> optionsVotes = new ArrayList<Map<String, Integer>>();
		for (int i = 0; i < mOptions.size(); i++) {
			Map<String, Integer> optionVote = new HashMap<String, Integer>();
			optionVote.put(mOptions.get(i), mVotes.get(i));
			optionsVotes.add(optionVote);
		}
		questionResultAdapter = new SingleChoiceQuestionResultViewAdapter(this,
				optionsVotes);
		questionCommentAdapter = new SingleChoiceQuestionCommentAdapter(this,
				mComments);

		questionResultListView.setAdapter(questionResultAdapter);
		questionCommentListView.setAdapter(questionCommentAdapter);

	}
}

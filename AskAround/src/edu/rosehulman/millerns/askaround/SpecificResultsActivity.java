package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SpecificResultsActivity extends Activity {
	private SingleChoiceQuestionResultViewAdapter questionResultAdapter;
	private SingleChoiceQuestionCommentAdapter questionCommentAdapter;
	private ArrayList<String> mComments;
	private ArrayList<String> mOptions;
	private ArrayList<Integer> mVotes;
	private String mQuestionType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specific_results);
		
		Intent intent = getIntent();
		mComments = intent.getStringArrayListExtra(CurrentQuestionFragment.UPDATED_COMMENTS);
		mOptions = intent.getStringArrayListExtra(CurrentQuestionFragment.UPDATED_QUESTION_OPTIONS);
		mVotes = intent.getIntegerArrayListExtra(CurrentQuestionFragment.UPDATED_QUESTION_VOTES);
		mQuestionType = intent.getStringExtra(CurrentQuestionFragment.CURRENT_QUESTION_TYPE);
		
		showOptionsAndVotes();
		setNextQustionButtonListener();	
		setQuestionTypeHint();
	}
	
	private void setNextQustionButtonListener() {
		Button nextQuestionButton = (Button) findViewById(R.id.next_question);
		nextQuestionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				Log.d("AA", "next question button click");
//				Fragment newFragment = new CurrentQuestionFragment();
//				FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//				// Replace whatever is in the fragment_container view with this fragment,
//				// and add the transaction to the back stack
//				transaction.replace(R.id.pager, newFragment);
//				transaction.addToBackStack(null);
//
//				// Commit the transaction
//				transaction.commit();
			}
		});
	}
	
	private void setQuestionTypeHint() {
		TextView questionTypeHint = (TextView) findViewById(R.id.instructions);
		if (mQuestionType.equals("COMMENTS_ONLY_QUESTION"))
			questionTypeHint.setText(getResources().getString(R.string.comments_only));
		else if (mQuestionType.equals("SINGLE_CHOICE_QUESTION"))
			questionTypeHint.setText(getResources().getString(R.string.pick_one_option));
		else 
			questionTypeHint.setText(getResources().getString(R.string.pick_any_option));
	}
	
	private void showOptionsAndVotes() {
		ListView questionResultListView = (ListView) findViewById(R.id.specific_result_list_view);
		ListView questionCommentListView = (ListView) findViewById(R.id.specific_comments_list_view);
		
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

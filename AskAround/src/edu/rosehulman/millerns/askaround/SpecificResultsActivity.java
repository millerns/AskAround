package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SpecificResultsActivity extends Activity {
	private SingleChoiceQuestionResultViewAdapter questionResultAdapter;
	private SingleChoiceQuestionCommentAdapter questionCommentAdapter;
	private Question mQuestion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specific_results);

		ListView questionResultListView = (ListView) findViewById(R.id.specific_result_list_view);
		ListView questionCommentListView = (ListView) findViewById(R.id.specific_comments_list_view);

		// Intent intent = getIntent();
		// mQuestion = intent.getParcelableExtra(Question.QUESTION);

		// just for testing
		mQuestion = new SingleChoiceQuestion(2, 3, "test question 1");
		mQuestion.addComment("Comment 1");
		mQuestion.addComment("Comment 2");
		mQuestion.addOption("Option 1");
		mQuestion.addOption("Option 2");
		mQuestion.addVote(1);
		mQuestion.addVote(1);
		mQuestion.addVote(0);

		// real stuff
		ArrayList<Map<String, Integer>> optionsVotes = new ArrayList<Map<String, Integer>>();
		for (int i = 0; i < mQuestion.getQuestionOptions().size(); i++) {
			Map<String, Integer> optionVote = new HashMap<String, Integer>();
			optionVote.put(mQuestion.getQuestionOptions().get(i), mQuestion
					.getVotes().get(i));
			optionsVotes.add(optionVote);
		}
		questionResultAdapter = new SingleChoiceQuestionResultViewAdapter(this,
				optionsVotes);
		questionCommentAdapter = new SingleChoiceQuestionCommentAdapter(this,
				mQuestion.getComments());

		questionResultListView.setAdapter(questionResultAdapter);
		questionCommentListView.setAdapter(questionCommentAdapter);

	}
}

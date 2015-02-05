package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class BrowseResultsActivity extends Activity {
	
	private BrowseResultsViewAdapter adapter;
	private ArrayList<Question> mQuestions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_results);
		
		ListView listView = (ListView) findViewById(R.id.browse_results_list_view);
		
		//Intent intent = getIntent();
		//mQuestions = intent.getParcelableArrayListExtra(Question.ALL_QUESTIONS);
		
		Question q1 = new Question(2, 3, "test question 1");
		Question q2 = new Question(100, 33, "test question 2");
		mQuestions = new ArrayList<Question>();
		mQuestions.add(q1);
		mQuestions.add(q2);
		adapter = new BrowseResultsViewAdapter(this, mQuestions);
		listView.setAdapter(adapter);
	}
	
	
}

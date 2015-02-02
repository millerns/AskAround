package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SpecificResultsActivity extends Activity {
	private SingleChoiceQuestionResultViewAdapter questionResultAdapter;
	private Question mQuestion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specific_results);
		
		ListView questionResultListView = (ListView) findViewById(R.id.specific_result_list_view);
		
		Intent intent = getIntent();
		mQuestion = intent.getParcelableExtra(Question.QUESTION);
		questionResultAdapter = new SingleChoiceQuestionResultViewAdapter(this, mQuestion);
		questionResultListView.setAdapter(questionResultAdapter);
		
		
		
		
		
		
		
	}
}

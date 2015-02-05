package edu.rosehulman.millerns.askaround;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CurrentQuestionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_question);
		
		//Intent intent = new Intent(this, BrowseResultsActivity.class);		
		Intent intent = new Intent(this, SpecificResultsActivity.class);
		startActivity(intent);

	}
}

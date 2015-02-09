package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class CreateQuestionActivity extends Activity {
	
	private ArrayList<String> mNewOptions;
	private CreateOptionListViewAdapter mNewOptionAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_question);
		
		Intent intent = getIntent();
		//mNewOptions = intent.getStringArrayListExtra(Question.NEW_OPTIONS);
		
		mNewOptions = new ArrayList<String>();
		mNewOptions.add("option 1");
		mNewOptions.add("Option 2");
		
		ListView newOptionListView = (ListView) findViewById(R.id.answer_choices_list_view);
		mNewOptionAdapter = new CreateOptionListViewAdapter(this, mNewOptions);
		newOptionListView.setAdapter(mNewOptionAdapter);
		
		newOptionListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				deleteNewOption(position);
				return true;
			}
		});
		
		
	}
	
	private void deleteNewOption(final int position) {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				// set options
				builder.setTitle(R.string.delete_option);
				builder.setMessage(R.string.delete_new_option_text);
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mNewOptions.remove(position);
								mNewOptionAdapter.notifyDataSetChanged();
							}
						});

				builder.setNegativeButton(android.R.string.cancel, null);

				return builder.create();
			}

		};

		df.show(getFragmentManager(), "delete new option");
		
	}
}

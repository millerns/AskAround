package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CurrentQuestionFragment extends Fragment {
	
	private Question currentQuestion;
	private View v;
	private int selectedOptionIndex = -1;
	private String comment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.activity_current_question, container, false);
		
		currentQuestion = generateNextQuestion();
		loadOptions(currentQuestion);
		EditText commentsView = (EditText) v.findViewById(R.id.comment);
		comment = commentsView.getText().toString();
	
		Button submitAnswer = (Button) v.findViewById(R.id.submit_button);
		submitAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO: these two lines need to be put back!
				
				//currentQuestion.addComment(comment);
				if (selectedOptionIndex == -1 ) {
					popErrorDialog();
				} else {
					//currentQuestion.addVote(selectedOptionIndex);
				}
				
				Log.d("AA", comment);
			}
		});
		
		return v;
	}	
	
	private void loadOptions(final Question question) {
		ListView optionListView = (ListView) v.findViewById(R.id.current_question_votes_list_view);
		
		//TODO: This line need to be put back!!
		//ArrayList optionList = question.getQuestionOptions(); 
		
		//for testing
		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add("option 1");
		optionList.add("option 2");
		optionList.add("option 3");
		
		
		ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, optionList);
		optionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		optionListView.setAdapter(optionsAdapter);

		optionListView.setOnItemClickListener(new OnItemClickListener() {			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedOptionIndex = position;
				Log.d("AA", "You selected option: " + selectedOptionIndex);
			}
		});
	}
	
	private void popErrorDialog() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				// set options
				builder.setTitle("No Option Selected");
				builder.setMessage("Please select a option");
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();

							}
						});

				return builder.create();
			}
		};
		df.show(getFragmentManager(), "option error dialog");
	}
	
	private Question generateNextQuestion() {
		//Question q = new Question(3, 10, "First Question");
		
		return null;
	}
}

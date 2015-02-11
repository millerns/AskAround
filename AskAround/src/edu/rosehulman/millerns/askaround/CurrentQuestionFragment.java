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
import android.widget.TextView;

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
		
		TextView questionContent = (TextView) v.findViewById(R.id.question);
		questionContent.setText(currentQuestion.getContent());
		
		loadOptions(currentQuestion);
		final EditText commentsView = (EditText) v.findViewById(R.id.comment);

		Button submitAnswer = (Button) v.findViewById(R.id.submit_button);
		submitAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				comment = commentsView.getText().toString();
				
				if (selectedOptionIndex == -1) {
					popErrorDialog();
				} else {
					currentQuestion.addVote(selectedOptionIndex);
					Log.d("AA", "you choose option: " + currentQuestion.getQuestionOptions().get(selectedOptionIndex));
					
					if (comment != null){
						currentQuestion.addComment(comment);
						Log.d("AA", "Comment: " + comment);
					}
				}				
			}
		});
		
		return v;
	}	
	
	private void loadOptions(final Question question) {
		ListView optionListView = (ListView) v.findViewById(R.id.current_question_votes_list_view);
		
		ArrayList optionList = question.getQuestionOptions(); 
		
		//for testing
//		ArrayList<String> optionList = new ArrayList<String>();
//		optionList.add("option 1");
//		optionList.add("option 2");
//		optionList.add("option 3");
		
		
		ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, optionList);
		optionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		optionListView.setAdapter(optionsAdapter);

		optionListView.setOnItemClickListener(new OnItemClickListener() {			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedOptionIndex = position;
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
								dialog.dismiss();

							}
						});

				return builder.create();
			}
		};
		df.show(getFragmentManager(), "option error dialog");
	}
	
	private Question generateNextQuestion() {
		SingleChoiceQuestion question = new SingleChoiceQuestion(10, "hello question 1");
		question.addOption("so you are option 1");
		question.addOption("I am option 2");
		question.addOption("please choose me");
		
		return question;
	}
}

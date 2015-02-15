package edu.rosehulman.millerns.askaround;

import java.io.IOException;
import java.util.ArrayList;
import com.appspot.dingx_askaround.questions.Questions;
import com.appspot.dingx_askaround.questions.Questions.Question.List;
import com.appspot.dingx_askaround.questions.model.QuestionCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.appspot.dingx_askaround.questions.model.Question;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	private String newComment;
	private Questions mService;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.activity_current_question, container, false);
		
		Questions.Builder builder = new Questions.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		mService = builder.build();
		
		generateNextQuestion();
		
		TextView questionContent = (TextView) v.findViewById(R.id.question);
		questionContent.setText(currentQuestion.getContent());
		
		loadOptions(currentQuestion);
		final EditText commentsView = (EditText) v.findViewById(R.id.comment);

		Button submitAnswer = (Button) v.findViewById(R.id.submit_button);
		submitAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				newComment = commentsView.getText().toString();
				java.util.List<String> comments = currentQuestion.getComments();
				java.util.List<String> options = currentQuestion.getOptions();
				java.util.List<Long> votes = currentQuestion.getVotes();
				String content = currentQuestion.getContent();
				String questionType = currentQuestion.getQuestionType();
				String entityKey = currentQuestion.getEntityKey();
				
				if (selectedOptionIndex == -1) {
					popErrorDialog();
				} else {
					votes.set(selectedOptionIndex, votes.get(selectedOptionIndex) + 1);
					Log.d("AA", "you choose option: " + options.get(selectedOptionIndex));
					
					if (newComment != null){
						comments.add(newComment);
						Log.d("AA", "Comment: " + newComment);
					}
				}	
				
				Question updatedQuestion = new Question();
				updatedQuestion.setContent(content);
				updatedQuestion.setComments(comments);
				updatedQuestion.setOptions(options);
				updatedQuestion.setVotes(votes);
				updatedQuestion.setQuestionType(questionType);
				updatedQuestion.setEntityKey(entityKey);
				
				updateQuestion(updatedQuestion);
			}
		});
		
		return v;
	}	
	
	private void loadOptions(final Question question) {
		ListView optionListView = (ListView) v.findViewById(R.id.current_question_votes_list_view);
		
		ArrayList<String> optionList = (ArrayList<String>) question.getOptions(); 
		
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
	
	private void generateNextQuestion() {
//		SingleChoiceQuestion question = new SingleChoiceQuestion(10, "hello question 1");
//		question.addOption("so you are option 1");
//		question.addOption("I am option 2");
//		question.addOption("please choose me");
//		
//		return question;
		new QueryForTask().execute();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.d("AA", "finish executing query");
	}
	
	private void updateQuestion(Question newQuestion) {
		new InsertQuestionTask().execute(newQuestion);
	}
	
	
	private class QueryForTask extends AsyncTask<Void, Void, QuestionCollection> {

		@Override
		protected QuestionCollection doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.d("AA", "start generating new question");

			QuestionCollection questions = null;
			try {
				List query = mService.question().list();
				query.setOrder("create_date");
				query.setLimit(50L);
				questions = query.execute();
				Log.d("AA", "got new questions: " + questions.size());
				currentQuestion = questions.getItems().get(0);
				Log.d("AA", "current items: " + questions.getItems().get(0).getContent());
				
			} catch (IOException e) {
				Log.e("AA", "Failed loading: " + e);
			}
			
			Log.d("AA", "About to return question collections");
			return questions;
		}
		
		@Override
		protected void onPostExecute(QuestionCollection result) {
			super.onPostExecute(result);
			Log.d("AA", "In post execute");
			
			if (result == null) {
				Log.d("AA", "Failed loading, result is null");
				return;
			}
			
			
			
		}
	}
	
	private class InsertQuestionTask extends AsyncTask<Question, Void, Question> {

		@Override
		protected Question doInBackground(Question... questions) {
			Question returnedQuestion = null;
			try {
				returnedQuestion = mService.question().insert(questions[0]).execute();
			} catch (IOException e) {
				Log.e("AA", "Failed inserting: " + e);
			}
			return returnedQuestion;
		}
		
		@Override
		protected void onPostExecute(Question result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.e("AA", "Failed inserting, result is null");
			}	
		}	
	}
	
	
	
	
}

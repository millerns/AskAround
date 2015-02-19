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
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
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
	private int selectedOptionIndex;
	private ArrayList<Integer> selectedIndexes;
	private String newComment;
	private Questions mService;
	public final static String UPDATED_COMMENTS = "UPDATED__COMMENTS";
	public final static String UPDATED_QUESTION_OPTIONS = "UPDATED__OPTIONS";
	public final static String UPDATED_QUESTION_VOTES = "UPDATED__VOTES";
	public final static String CURRENT_QUESTION_TYPE = "CURRENT_QUESTION_TYPE";
	public final static String CURRENT_QUESTION_CONTENT = "CURRENT_QUESTION_CONTENT";
	public static final int REQUEST_CODE_FOR_RESULT = 1;
	private static int currentQuestionIndex = 0;
	private ArrayAdapter<String> optionsAdapter;
	private ListView optionListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		selectedIndexes = new ArrayList<Integer>();
		selectedOptionIndex = -1;
		Log.d("AA", "yeah we start");

		v = inflater.inflate(R.layout.activity_current_question, container,
				false);

		Questions.Builder builder = new Questions.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
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

				addSelectedOptions(votes, questionType);

				if (!newComment.equals("")) {
					if (comments == null) {
						comments = new ArrayList<String>();
					}
					comments.add(newComment);
				}

				Question updatedQuestion = new Question();
				updatedQuestion.setContent(content);
				updatedQuestion.setComments(comments);
				updatedQuestion.setOptions(options);
				updatedQuestion.setVotes(votes);
				updatedQuestion.setQuestionType(questionType);
				updatedQuestion.setEntityKey(entityKey);

				updateQuestion(updatedQuestion);

				showCurrentQuestionResult(comments, options, votes,
						currentQuestion.getQuestionType(), content);
			}

			

			private void addSelectedOptions(java.util.List<Long> votes,
					String questionType) {
				if (questionType.equals("SINGLE_CHOICE_QUESTION")) {
					if (selectedOptionIndex == -1)
						popErrorDialog();
					else
						votes.set(selectedOptionIndex,
								votes.get(selectedOptionIndex) + 1);
				} else if (questionType.equals("MULTIPLE_CHOICE_QUESTION")) {

					SparseBooleanArray checked = optionListView
							.getCheckedItemPositions();
					for (int i = 0; i < optionListView.getCount(); i++) {
						if (checked.get(i)) {
							if (!selectedIndexes.contains(i))
							selectedIndexes.add(i);
							Log.d("AA", "Add option: " + i);
						}
					}

					if (selectedIndexes.size() == 0)
						popErrorDialog();
					else {
						for (Integer i : selectedIndexes) {
							votes.set(i, votes.get(i) + 1);
							Log.d("AA", "Option " + i + "is selected!");
						}
					}
				}
			}
		});

		return v;
	}

	private void loadOptions(final Question question) {
		optionListView = (ListView) v
				.findViewById(R.id.current_question_votes_list_view);

		ArrayList<String> optionList = (ArrayList<String>) question
				.getOptions();

		TextView questionTypeHint = (TextView) v
				.findViewById(R.id.instructions);
		if (currentQuestion.getQuestionType().equals("COMMENTS_ONLY_QUESTION")) {
			questionTypeHint.setText(getResources().getString(
					R.string.comments_only));
			optionListView.setVisibility(View.GONE);
		} else if (currentQuestion.getQuestionType().equals(
				"SINGLE_CHOICE_QUESTION")) {
			questionTypeHint.setText(getResources().getString(
					R.string.pick_one_option));
			optionsAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_single_choice, optionList);
			optionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			optionListView.setAdapter(optionsAdapter);

			optionListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					selectedOptionIndex = position;
				}
			});
		} else {
			questionTypeHint.setText(getResources().getString(
					R.string.pick_any_option));
			optionsAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_multiple_choice,
					optionList);
			optionListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			optionListView.setAdapter(optionsAdapter);
		}
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

	private void popNoMoreQuestionDialog() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				// set options
				builder.setTitle("Sorry");
				builder.setMessage("No more questions to answer");
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

	private void showCurrentQuestionResult(java.util.List<String> comments,
			java.util.List<String> options, java.util.List<Long> votes,
			String type, String content) {

		Intent resultIntent = new Intent(getActivity(),
				SpecificResultsActivity.class);
		if ((type.equals("SINGLE_CHOICE_QUESTION")) || (type.equals("MULTIPLE_CHOICE_QUESTION"))){
			resultIntent.putStringArrayListExtra(UPDATED_QUESTION_OPTIONS,
					(ArrayList<String>) options);
			
			ArrayList<Integer> mVotes = new ArrayList<Integer>();
			for (int i = 0; i < votes.size(); i++) {
				mVotes.add(safeLongToInt(votes.get(i)));
			}

			resultIntent.putIntegerArrayListExtra(UPDATED_QUESTION_VOTES, mVotes);
		}
		
		resultIntent.putExtra(CURRENT_QUESTION_CONTENT, content);
		resultIntent.putStringArrayListExtra(UPDATED_COMMENTS,
				(ArrayList<String>) comments);
		
		resultIntent.putExtra(CURRENT_QUESTION_TYPE, type);
		
		startActivityForResult(resultIntent, REQUEST_CODE_FOR_RESULT);
		// finishActivityFromChild(resultIntent, REQUEST_CODE_FOR_RESULT);
	}

	// private void finishActivityFromChild(Intent resultIntent,
	// int requestCodeForResult) {
	// Log.d("AA", "returned to current activity!!");
	// }

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l
					+ " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	private class QueryForTask extends
			AsyncTask<Void, Void, QuestionCollection> {

		@Override
		protected QuestionCollection doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.d("AA", "start generating new question");
			QuestionCollection questions = null;

			try {
				List query = mService.question().list();
				query.setOrder("create_date");
				//query.setLimit(50L);
				questions = query.execute();
				Log.d("AA", "got new questions: " + questions.getItems().size());
				if (currentQuestionIndex == questions.getItems().size()) {
					popNoMoreQuestionDialog();
					currentQuestionIndex--;
				}

				currentQuestion = questions.getItems()
						.get(currentQuestionIndex);
				Log.d("AA", "current items: "
						+ questions.getItems().get(currentQuestionIndex).getContent());
				currentQuestionIndex++;

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

	private class InsertQuestionTask extends
			AsyncTask<Question, Void, Question> {

		@Override
		protected Question doInBackground(Question... questions) {
			Question returnedQuestion = null;
			try {
				returnedQuestion = mService.question().insert(questions[0])
						.execute();
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

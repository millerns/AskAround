package edu.rosehulman.millerns.askaround;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.appspot.dingx_askaround.questions.Questions;
import com.appspot.dingx_askaround.questions.model.Question;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class CreateQuestionActivity extends Activity {

	private static final String AA = "AA";

	private enum QuestionType {
		COMMENTS_ONLY_QUESTION, SINGLE_CHOICE_QUESTION, MULTIPLE_CHOICE_QUESTION
	}

	private ArrayList<String> mNewOptions;
	private EditText questionContent;
	private ListView newOptionListView;
	private Button addOptionButton;
	private Button submitQuestionButton;
	private CreateOptionListViewAdapter mNewOptionAdapter;
	private QuestionType mQuestionType;
	private Questions mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_question);

		Questions.Builder builder = new Questions.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		mService = builder.build();

		questionContent = (EditText) findViewById(R.id.question_content);
		addOptionButton = (Button) findViewById(R.id.add_answer_choice);
		submitQuestionButton = (Button) findViewById(R.id.submit_question);
		newOptionListView = (ListView) findViewById(R.id.answer_choices_list_view);
		mNewOptions = new ArrayList<String>();
		mNewOptionAdapter = new CreateOptionListViewAdapter(this, mNewOptions);

		setupOptionsListView();
		setupQuestionTypeButtons();
		setupAddOptionsButton();
		setupSubmitQuestionButton();

	}

	private void setupOptionsListView() {
		newOptionListView.setAdapter(mNewOptionAdapter);
		newOptionListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						deleteNewOption(position);
						return true;
					}
				});
	}

	private void setupAddOptionsButton() {
		addOptionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addNewOption();
			}
		});
	}

	public void setupSubmitQuestionButton() {
		submitQuestionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Question newQuestion = new Question();
				newQuestion.setContent(questionContent.getText().toString());
				Log.d(AA, "question type: " + mQuestionType.toString());

				newQuestion.setQuestionType(mQuestionType.name());
				switch (mQuestionType) {
				case COMMENTS_ONLY_QUESTION:
					break;
				case SINGLE_CHOICE_QUESTION:
					newQuestion.setOptions(mNewOptions);
					break;
				case MULTIPLE_CHOICE_QUESTION:
					newQuestion.setOptions(mNewOptions);
					break;
				default:
					break;
				}
				
				List<Long> votes = new ArrayList<Long>();
				for(int i = 0; i < mNewOptions.size(); i++) {
					votes.add((long) 0);
				}
				newQuestion.setVotes(votes);
				
				insertQuestionTask(newQuestion);
				
				finish();
			}
		});
	}

	private void setupQuestionTypeButtons() {
		final Button commentOnlyButton = (Button) findViewById(R.id.comments_only_type);
		final Button singleChoiceButton = (Button) findViewById(R.id.pick_one_type);
		final Button multipleChoiceButton = (Button) findViewById(R.id.pick_any_type);
		final TextView answerChoice = (TextView) findViewById(R.id.answer_choices);

		commentOnlyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				commentOnlyButton.setBackgroundColor(getResources().getColor(
						R.color.blue));
				singleChoiceButton.setBackgroundColor(getResources().getColor(
						R.color.white));
				multipleChoiceButton.setBackgroundColor(getResources()
						.getColor(R.color.white));
				answerChoice.setVisibility(View.GONE);
				addOptionButton.setVisibility(View.GONE);
				newOptionListView.setVisibility(View.GONE);
				mQuestionType = QuestionType.COMMENTS_ONLY_QUESTION;
			}
		});

		singleChoiceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				commentOnlyButton.setBackgroundColor(getResources().getColor(
						R.color.white));
				singleChoiceButton.setBackgroundColor(getResources().getColor(
						R.color.blue));
				multipleChoiceButton.setBackgroundColor(getResources()
						.getColor(R.color.white));
				answerChoice.setVisibility(View.VISIBLE);
				addOptionButton.setVisibility(View.VISIBLE);
				newOptionListView.setVisibility(View.VISIBLE);
				mQuestionType = QuestionType.SINGLE_CHOICE_QUESTION;
			}
		});

		multipleChoiceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				commentOnlyButton.setBackgroundColor(getResources().getColor(
						R.color.white));
				singleChoiceButton.setBackgroundColor(getResources().getColor(
						R.color.white));
				multipleChoiceButton.setBackgroundColor(getResources()
						.getColor(R.color.blue));
				answerChoice.setVisibility(View.VISIBLE);
				addOptionButton.setVisibility(View.VISIBLE);
				newOptionListView.setVisibility(View.VISIBLE);
				mQuestionType = QuestionType.MULTIPLE_CHOICE_QUESTION;
			}
		});

	}

	private void addNewOption() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());

				builder.setTitle(R.string.add_answer_choice);
				final EditText input = new EditText(getActivity());
				builder.setView(input);

				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String newOption = input.getText().toString();
								mNewOptions.add(newOption);
								mNewOptionAdapter.notifyDataSetChanged();
							}
						});

				builder.setNegativeButton(android.R.string.cancel, null);

				return builder.create();
			}

		};

		df.show(getFragmentManager(), "add new option");
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

	private void insertQuestionTask(Question currentQuestion) {
		new InsertQuestionTask().execute(currentQuestion);
	}

	class InsertQuestionTask extends AsyncTask<Question, Void, Question> {

		@Override
		protected Question doInBackground(Question... questions) {
			Question returnedQuestion = null;
			try {
				returnedQuestion = mService.question().insert(questions[0])
						.execute();
			} catch (IOException e) {
				Log.e(AA, "Failed inserting: " + e);
			}
			return returnedQuestion;
		}

		@Override
		protected void onPostExecute(Question result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.e(AA, "Failed inserting, result is null");
			}

		}

	}

}

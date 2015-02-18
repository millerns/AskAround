package edu.rosehulman.millerns.askaround;

import java.io.IOException;
import java.util.ArrayList;

import com.appspot.dingx_askaround.questions.Questions;
import com.appspot.dingx_askaround.questions.Questions.Question.List;
import com.appspot.dingx_askaround.questions.model.Question;
import com.appspot.dingx_askaround.questions.model.QuestionCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BrowseResultsFragment extends Fragment implements
		OnItemClickListener {
	private BrowseResultsViewAdapter mAdapter;
	private Questions mService;
	private View v;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater
				.inflate(R.layout.activity_browse_results, container, false);

		Questions.Builder builder = new Questions.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		mService = builder.build();

		listView = (ListView) v.findViewById(R.id.browse_results_list_view);
		queryListOfQuestions();
		listView.setOnItemClickListener(this);

		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Question question = mAdapter.getItem(position);
		showCurrentQuestionResult(question.getComments(), question.getOptions(), question.getVotes(), question.getQuestionType());
	}

	private void showCurrentQuestionResult(java.util.List<String> comments,
			java.util.List<String> options, java.util.List<Long> votes,
			String type) {

		Intent resultIntent = new Intent(getActivity(),
				SpecificResultsActivity.class);
		
		resultIntent.putStringArrayListExtra(
				CurrentQuestionFragment.UPDATED_COMMENTS,
				(ArrayList<String>) comments);
		resultIntent.putStringArrayListExtra(
				CurrentQuestionFragment.UPDATED_QUESTION_OPTIONS,
				(ArrayList<String>) options);
		resultIntent.putExtra(CurrentQuestionFragment.CURRENT_QUESTION_TYPE,
				type);
		ArrayList<Integer> mVotes = new ArrayList<Integer>();
		for (int i = 0; i < votes.size(); i++) {
			mVotes.add(safeLongToInt(votes.get(i)));
		}

		resultIntent.putIntegerArrayListExtra(
				CurrentQuestionFragment.UPDATED_QUESTION_VOTES, mVotes);
		startActivityForResult(resultIntent,
				CurrentQuestionFragment.REQUEST_CODE_FOR_RESULT);
	}

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l
					+ " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	private void queryListOfQuestions() {
		new QueryForTask().execute();
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
				query.setLimit(50L);
				questions = query.execute();
				Log.d("AA", "got new questions: " + questions.size());
				Log.d("AA", "current items: "
						+ questions.getItems().get(0).getContent());

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
			
			mAdapter = new BrowseResultsViewAdapter(getActivity(),
					(ArrayList<Question>) result.getItems());
			listView.setAdapter(mAdapter);
		}
	}

}

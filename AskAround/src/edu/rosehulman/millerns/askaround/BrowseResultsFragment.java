package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BrowseResultsFragment extends Fragment implements
		OnItemClickListener {
	private BrowseResultsViewAdapter mAdapter;
	private ArrayList<Question> mQuestions;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_browse_results, container,
				false);

		ListView listView = (ListView) v
				.findViewById(R.id.browse_results_list_view);

		// Intent intent = getIntent();
		// mQuestions =
		// intent.getParcelableArrayListExtra(Question.ALL_QUESTIONS);

		Question q1 = new SingleChoiceQuestion(2, 3, "test question 1");
		Question q2 = new SingleChoiceQuestion(100, 33, "test question 2");
		mQuestions = new ArrayList<Question>();
		mQuestions.add(q1);
		mQuestions.add(q2);
		mAdapter = new BrowseResultsViewAdapter(getActivity(), mQuestions);
		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(this);

		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), SpecificResultsActivity.class);
		startActivity(intent);
	}
}

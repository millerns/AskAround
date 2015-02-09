package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Question implements Parcelable {

	private int numOfAnswers;
	private int dueInDays;
	private String content;
	private ArrayList<String> options = new ArrayList<String>();
	private ArrayList<Integer> votes = new ArrayList<Integer>();
	private int totalVotes = 0;
	private ArrayList<String> comments = new ArrayList<String>();

	public static final String ALL_QUESTIONS = "all_questions";
	public static final String QUESTION = "question";
	public static final String NEW_OPTIONS = "new_options";

	public Question(int numberOfAnswers, int dueInDays, String content) {
		this.numOfAnswers = numberOfAnswers;
		this.dueInDays = dueInDays;
		this.content = content;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(numOfAnswers);
		dest.writeInt(dueInDays);
		dest.writeString(content);
		dest.writeStringList(options);

		dest.writeInt(votes.size());
		int[] vA = new int[votes.size()];
		Iterator<Integer> iterator = votes.iterator();
		for (int i = 0; i < vA.length; i++) {
			vA[i] = iterator.next().intValue();
		}
		dest.writeIntArray(vA);

		dest.writeInt(totalVotes);
		dest.writeStringList(comments);
	}

	public Question(Parcel in) {
		this.numOfAnswers = in.readInt();
		this.dueInDays = in.readInt();
		this.content = in.readString();
		this.options = new ArrayList<String>();
		in.readStringList(options);

		int votesSize = in.readInt();
		int[] votesArray = new int[votesSize];
		in.readIntArray(votesArray);
		this.votes = new ArrayList<Integer>(votesSize);
		for (int i = 0; i < votesSize; i++) {
			votes.set(i, votesArray[i]);
		}

		this.totalVotes = in.readInt();
		this.comments = new ArrayList<String>();
		in.readStringList(comments);
	}

	public int getNumberOfAnswers() {
		return numOfAnswers;
	}

	public int getDueInDays() {
		return dueInDays;
	}

	public String getQuestionContent() {
		return content;
	}

	public ArrayList<String> getQuestionOptions() {
		return options;
	}

	public void addOption(String option) {
		options.add(option);
		votes.add(0);
	}

	public int getTotalVotes() {
		return totalVotes;
	}

	public ArrayList<Integer> getVotes() {
		return votes;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void addComment(String comment) {
		comments.add(comment);
	}

	public void addVote(int option) {
		votes.set(option, votes.get(option) + 1);
		totalVotes++;
	}

}

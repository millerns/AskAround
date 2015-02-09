package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Question implements Parcelable{
	
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
	
	public Question(int numberOfAnswers, int dueInDays, String content){
		this.numOfAnswers = numberOfAnswers;
		this.dueInDays = dueInDays;
		this.content = content;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
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
	
	public void cancleVote(int option) {
		votes.set(option, votes.get(option) - 1);
		totalVotes--;
	}
	

}

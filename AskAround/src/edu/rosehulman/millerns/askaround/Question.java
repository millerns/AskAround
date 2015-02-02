package edu.rosehulman.millerns.askaround;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable{
	
	private int numOfAnswers;
	private int dueInDays;
	private String content;
	private ArrayList<String> options;
	private ArrayList<Integer> votes;
	private int totalVotes;

	public static final String QUESTION = "question";
	
	public Question(int numberOfAnswers, int dueInDays, String content){
		this.numOfAnswers = numberOfAnswers;
		this.dueInDays = dueInDays;
		this.content = content;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	public int getTotalVotes() {
		return totalVotes;
	}
	
	public ArrayList<Integer> getVotes() {
		return votes;
	}

}

package edu.rosehulman.millerns.askaround;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable{
	
	private int numOfAnswers;
	private int dueInDays;
	private String content;
	public static final String QUESTION_CONTENT = "question_content";
	public static final String QUESTION_DUETIME = "question_duetime";
	public static final String QUESTION_HOTNESS = "question_hotness";
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}

//package edu.rosehulman.millerns.askaround;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//public class SingleChoiceQuestion extends Question implements Parcelable {
//
//	public SingleChoiceQuestion(int dueInDays,
//			String content) {
//		super(dueInDays, content);
//		// TODO Auto-generated constructor stub
//	}
//
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		super.writeToParcel(dest, flags);
//	}
//
//	public static final Parcelable.Creator<SingleChoiceQuestion> CREATOR
//	= new Parcelable.Creator<SingleChoiceQuestion>() {
//
//		@Override
//		public SingleChoiceQuestion createFromParcel(Parcel source) {
//			return new SingleChoiceQuestion(source);
//		}
//
//		@Override
//		public SingleChoiceQuestion[] newArray(int size) {
//			return new SingleChoiceQuestion[size];
//		}
//	};
//	
//	
//	public SingleChoiceQuestion(Parcel in) {
//		super(in);
//	}
//
//	@Override
//	int getNumOfAnswers() {
//		return super.getTotalVotes();
//	}
//
//}

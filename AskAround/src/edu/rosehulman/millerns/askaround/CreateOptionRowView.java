package edu.rosehulman.millerns.askaround;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateOptionRowView extends LinearLayout{
	
	private TextView newOption;
	

	public CreateOptionRowView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.create_option_row_view, this);		
		newOption = (TextView) findViewById(R.id.newOption);
	}
	
	public void setNewOption(String option) {
		newOption.setText(option);
	}

}

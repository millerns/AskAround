package edu.rosehulman.millerns.askaround;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateOptionRowView extends LinearLayout{
	
	private Button deleteButton;
	private TextView newOption;
	

	public CreateOptionRowView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.create_option_row_view, this);
		
		deleteButton = (Button) findViewById(R.id.deleteButton);
		newOption = (TextView) findViewById(R.id.newOption);
		
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// delete the current option
				
			}
		});
		
	}
	
	public void setNewOption(String option) {
		newOption.setText(option);
	}

}

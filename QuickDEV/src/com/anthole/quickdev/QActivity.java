package com.anthole.quickdev;


import butterknife.ButterKnife;
import butterknife.OnClick;
import android.app.Activity;
import android.os.Bundle;

public class QActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.bind(this);
	}

}

package com.gree.parsexml;

import android.content.Intent;
import android.os.Bundle;

public class LostSettingActivityone extends BaseSettingActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_one);
		
	}

	@Override
	public void showNext() {
		Intent intent=new Intent(LostSettingActivityone.this,LostSettingActivitytwo.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out); 
	}

	@Override
	public void showPre() {
		
	}
	
	

}

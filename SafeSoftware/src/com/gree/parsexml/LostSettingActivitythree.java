package com.gree.parsexml;

import android.content.Intent;
import android.os.Bundle;

public class LostSettingActivitythree extends BaseSettingActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_three);
		
	}

	@Override
	public void showNext() {
		Intent intent = new Intent(this,LostSettingActivityfour.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this,LostSettingActivitytwo.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}

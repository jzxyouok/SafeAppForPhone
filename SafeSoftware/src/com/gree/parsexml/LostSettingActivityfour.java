package com.gree.parsexml;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class LostSettingActivityfour extends BaseSettingActivity {
	
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_four);
		sp=getSharedPreferences("config", MODE_PRIVATE);
	}

	@Override
	public void showNext() {
		Editor edit=sp.edit();
		edit.putBoolean("configed", true);
		edit.commit();
		Intent intent=new Intent(LostSettingActivityfour.this,LostSettingActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	@Override
	public void showPre() {
		Intent intent=new Intent(LostSettingActivityfour.this,LostSettingActivitythree.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

}

package com.gree.parsexml;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class LostSettingActivity extends Activity {
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean configed=sp.getBoolean("configed", false);
		Log.e("LostSettingActivity", "config:"+configed);
		//设置了手机向导
		if (configed) {
			setContentView(R.layout.activity_lost_find);
		}else{
			//跳转设置向导页面
			Intent intent=new Intent(LostSettingActivity.this,LostSettingActivityone.class);
			startActivity(intent);
			finish();
		}
	}
	

}

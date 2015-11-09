package com.gree.application;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

public class MyApplication extends Application {
	private Context context;
	@Override
	public void onCreate() {
		super.onCreate();
		context=getApplicationContext();
		new MyColor();
	}
	
	class MyColor extends Activity{
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setColor();
		}
		
		@TargetApi(Build.VERSION_CODES.KITKAT)
		@SuppressLint("InlinedApi")
		public void setColor(){
			if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			}
		}
		
		
	}
}

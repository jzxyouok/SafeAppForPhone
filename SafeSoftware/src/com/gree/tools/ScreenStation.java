package com.gree.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

public class ScreenStation extends Activity{
	
	private Context context;
	
	public ScreenStation(Activity activity) {
		this.context=activity;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setColor();
	}
	@TargetApi(19)
	public void setColor(){
		//如果是api19以上则开启亲尘世屏幕
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			
		}
		
		
	}

}

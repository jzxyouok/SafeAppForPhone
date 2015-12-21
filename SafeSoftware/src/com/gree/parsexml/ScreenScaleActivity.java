package com.gree.parsexml;

import com.gree.parsexml.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * 获取手机屏幕宽高
 * @author susan
 *
 */
public class ScreenScaleActivity extends Activity {
	private float width;
	private float height;
	private float midu;
	private float w;
	private TextView screen;
	private float scale;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_main);
		
		DisplayMetrics metric = new DisplayMetrics();
	      getWindowManager().getDefaultDisplay().getMetrics(metric);
		
		
		DisplayMetrics dm =getResources().getDisplayMetrics();
		width = dm.widthPixels;// 获取屏幕分辨率宽度 单位为px（像素）  并不是屏幕绝对尺寸
		height = dm.heightPixels;
		midu=dm.densityDpi;
		w=dm.density;
		
		scale=ScreenScaleActivity.this.getResources().getDisplayMetrics().density;
		float value=(width/scale+0.5f);
		String str="宽度："+width+"高度："+height+"密度："+midu+"w:  "+w+"value:  "+value;
		
		screen=(TextView)findViewById(R.id.screen);
		screen.setText(str);
		
	}
	
	
	

}

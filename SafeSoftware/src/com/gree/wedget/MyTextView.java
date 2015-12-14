package com.gree.wedget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义组件
 * 设置一个永远处在获取了焦点状态的TextView
 * @author susan
 *
 */
public class MyTextView extends TextView {

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyTextView(Context context) {
		super(context);
	}
	
	@Override
	public boolean isFocused() {
		
		return true;
	}
	

}

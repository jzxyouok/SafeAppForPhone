package com.gree.tools;

import com.gree.parsexml.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 自定义相对布局  自定义布局意义？？  里面有一个TextView  一个checkbox 
 * @author susan
 *
 */
public class SettingGroupView extends RelativeLayout {
	
	private CheckBox myBox;
	private TextView myText;
	
	/**
	 * 初始化布局文件
	 */
	private void initView(Context context) {
		//第三个参数：ViewGroup root:布局文件的父类？？
		View.inflate(context, R.layout.group_setting, this);
		myBox=(CheckBox) this.findViewById(R.id.updata_chebox);
		myText=(TextView)this.findViewById(R.id.updata_text);
		
	}

	public SettingGroupView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		
		initView(context);
	}

	public SettingGroupView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public SettingGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SettingGroupView(Context context) {
		super(context);
		initView(context);
	}
	
	public boolean isChecked(){
		return myBox.isChecked();
		
	}
	
	//摄制组和控件的焦点
	public void setChecked(boolean checked){
		myBox.setChecked(checked);
	}

}

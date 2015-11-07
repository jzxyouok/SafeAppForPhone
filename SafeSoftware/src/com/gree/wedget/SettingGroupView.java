package com.gree.wedget;

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
	private TextView tipText;//提醒文字
	private String desc_on;
	private String desc_off;
	
	/**
	 * 初始化布局文件  xml中引用的widget一般来说执行的是带两个参数的构造方法，自动执行的
	 */
	private void initView(Context context) {
		//第三个参数：ViewGroup root:布局文件的父类？？
		View.inflate(context, R.layout.group_setting, this);
		myBox=(CheckBox) this.findViewById(R.id.updata_chebox);
		myText=(TextView)this.findViewById(R.id.updata_text);
		tipText=(TextView)this.findViewById(R.id.tip_text);
		
	}
	//
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
//布局文件使用的时候调用  属性集合
	public SettingGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
//		System.out.println(attrs.getAttributeValue(0));
//		System.out.println(attrs.getAttributeValue(1));
//		System.out.println(attrs.getAttributeValue(2));
//		System.out.println(attrs.getAttributeValue(3));
//		System.out.println(attrs.getAttributeValue(4));
//		System.out.println(attrs.getAttributeValue(5));//数组的形式
		//命名空间得到属性：
		String title=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.gree.parsexml","tittle");
		desc_on=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.gree.parsexml","desc_on");
		desc_off=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.gree.parsexml","desc_off");
		myText.setText(title);
		
		
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
		if (checked) {
			
			setTipTxt(desc_on);
		} else {
			setTipTxt(desc_off);
		}
		myBox.setChecked(checked);
	}
	//组件描述信息
	public void setTipTxt(String str){
		tipText.setText(str);
	}

}

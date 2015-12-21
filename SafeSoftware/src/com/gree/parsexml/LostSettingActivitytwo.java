package com.gree.parsexml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.gree.wedget.SettingGroupView;

public class LostSettingActivitytwo extends BaseSettingActivity {
	//点击事件
	private SettingGroupView bindSimCard;
	private TelephonyManager tm;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_two);
		setView();
		setListener();
	}
	private void setView() {
		bindSimCard=(SettingGroupView)findViewById(R.id.bind_sim_card);
		tm=(TelephonyManager) getSystemService(TELECOM_SERVICE);
	}
	public void setListener(){
		String sim=sp.getString("sim", null);
		if (TextUtils.isEmpty(sim)) {
			bindSimCard.setChecked(true);
		}else{
			bindSimCard.setChecked(false);
		}
		bindSimCard.setOnClickListener(new OnClickListener() {
			Editor edit=sp.edit();
			String simNumber=tm.getSimSerialNumber();//空指针异常
			@Override
			public void onClick(View v) {
				if (bindSimCard.isChecked()) {
					bindSimCard.setChecked(false);
					//保存sim卡序列号
					edit.putString("sim", null);
				}else{
					bindSimCard.setChecked(true);
					//保存sim卡序列号
					edit.putString("sim", simNumber);
				}
				edit.commit();
			}
		});
	}

	@Override
	public void showNext() {
		Intent intent=new Intent(LostSettingActivitytwo.this,LostSettingActivitythree.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	@Override
	public void showPre() {
		Intent intent=new Intent(LostSettingActivitytwo.this,LostSettingActivityone.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}
}

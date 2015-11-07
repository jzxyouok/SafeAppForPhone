package com.gree.parsexml;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gree.wedget.SettingGroupView;

public class SettingActivity extends Activity {
	private SettingGroupView siv_update;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		sp = getSharedPreferences("config", MODE_PRIVATE);//保存的文件名为config,如果文件不存在则会新建一个
		siv_update = (SettingGroupView) findViewById(R.id.item1);

		boolean update = sp.getBoolean("update", false);
		if (update) {
			// 自动升级已经开启
			siv_update.setChecked(true);
		} else {
			// 自动升级已经关闭
			siv_update.setChecked(false);
		}
		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Editor editor = sp.edit();
				// 判断是否有选中
				// 已经打开自动升级了
				if (siv_update.isChecked()) {
					siv_update.setChecked(false);
//					siv_update.setTipTxt("自动升级已关闭");
					editor.putBoolean("update", false);

				} else {
					// 没有打开自动升级
					siv_update.setChecked(true);
//					siv_update.setTipTxt("自动升级已开启");
					editor.putBoolean("update", true);
				}
				editor.commit();
			}
		});
	}
}

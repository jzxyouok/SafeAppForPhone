package com.gree.parsexml;

import com.gree.application.MyApplication;
import com.gree.parsexml.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private MyApplication ma;
	
	private GridView my_gridview;
	// 文字内容
	private String txts[] = { "手机防盗", "通讯卫视", "软件管理", "进程管理", "流量统计", "手机杀毒",
			"缓存清理", "高级工具", "设置中心" };
	// 图片资源
	private int imgs[] = { R.drawable.st01, R.drawable.st02, R.drawable.st03,
			R.drawable.st04, R.drawable.st05, R.drawable.st06, R.drawable.st07,
			R.drawable.st08, R.drawable.st09 };
	private SelfAdapter adapter;
	
	private SharedPreferences sp;
	private Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_main);
		
		initView();
		initListener();

	}

	public void initView() {
		my_gridview = (GridView) findViewById(R.id.my_gridview);
		ma=new MyApplication();
		sp=getSharedPreferences("config",MODE_PRIVATE);
	}

	public void initListener() {
		adapter = new SelfAdapter();
		my_gridview.setAdapter(adapter);
		// 点击每一项
		my_gridview.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * list view 位置 id
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 8:
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, SettingActivity.class);
					startActivity(intent);
					break;
				case 0:
					//手机防盗页面
					showMyDialog();
					Intent oneIntent=new Intent();

				default:
					break;
				}
			}
		});
	}
	//判断是否设置过密码
	protected void showMyDialog() {
		
	}
	
	private boolean isSetPas(){
		String pwd=sp.getString("password", null);
//		if (TextUtils.isEmpty(pwd)) {
//			return false;
//		}
		return !TextUtils.isEmpty(pwd);
		
	}

	/**
	 * 自定义adapter
	 * 
	 * @author susan
	 *
	 */
	public class SelfAdapter extends BaseAdapter {
		// 得到item总数目
		@Override
		public int getCount() {
			return txts.length;// 数组里面length是个属性
		}

		// 得到每一项
		@Override
		public Object getItem(int position) {

			return null;
		}

		//
		@Override
		public long getItemId(int position) {

			return 0;
		}

		// 返回每一个item的视图
		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = View.inflate(MainActivity.this, R.layout.home_item, null);
			// 动态设置文字
			TextView text = (TextView) v.findViewById(R.id.itemtext);// 通过映射寻找到组建的id
			text.setText(txts[position]);// 某个位置上的
			ImageView image = (ImageView) v.findViewById(R.id.itemimg);
			image.setBackgroundResource(imgs[position]);

			// 返回这个view
			return v;
		}

	}

}

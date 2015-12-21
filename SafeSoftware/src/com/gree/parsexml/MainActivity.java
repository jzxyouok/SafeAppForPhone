package com.gree.parsexml;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gree.application.MyApplication;
import com.gree.parsexml.R;
import com.gree.tools.MD5Entry;

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
	// 确认密码界面
	private EditText inputPwd;
	private EditText reinputPwd;
	private Button ensureBtn;
	private Button cancelBtn;
	private Button inputCancelBtn;
	private Button inputEnsureBtn;
	private EditText inputPassword;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_main);

		initView();
		initListener();

	}

	public void initView() {
		my_gridview = (GridView) findViewById(R.id.my_gridview);
		ma = new MyApplication();
		sp = getSharedPreferences("config", MODE_PRIVATE);
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
					// 设置中心
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, SettingCenterActivity.class);
					startActivity(intent);
					break;
				case 0:
					// 手机防盗页面
					showMyDialog();
					System.out.println("gagagagagagaga");
				default:
					break;
				}
			}
		});
	}

	/**
	 *  判断是否设置过密码
	 */
	protected void showMyDialog() {
		if (isSetPas()) {
			// 弹出输入密码框
			showEnterDialog();
		} else {
			// 弹出设置密码框
			showSetDialogDialog();
		}

	}

	/**
	 * 设置密码
	 * 
	 * @return
	 */
	private void showSetDialogDialog() {
		AlertDialog.Builder setPwdBuilder = new Builder(MainActivity.this);
		View view = View.inflate(MainActivity.this, R.layout.dialog_setting_password, null);
		inputPwd = (EditText) view.findViewById(R.id.input_pwd);
		reinputPwd = (EditText) view.findViewById(R.id.reinput_pwd);
		ensureBtn = (Button) view.findViewById(R.id.ensure);
		cancelBtn = (Button) view.findViewById(R.id.cancel);

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		ensureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取出密码
				String password = inputPwd.getText().toString().trim();
				String repassword = reinputPwd.getText().toString().trim();
				String entryPassword=MD5Entry.mdPassword(password);
				// 比较一和二是否相同，不同提示
				if (TextUtils.isEmpty(password)
						|| TextUtils.isEmpty(repassword)) {
					Toast.makeText(MainActivity.this, "密码为空，请输入密码",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (password.equals(repassword)) {
					// 保存密码 去掉对话框 进入手机防盗页面
					Editor edit = sp.edit();
					edit.putString("password", MD5Entry.mdPassword(password));//保存加密的密码
					edit.commit();// 提交
					dialog.dismiss();
					// 进入主界面
					// 进入主界面
					System.out.println("呵呵，进入主界面");
					Intent intent=new Intent(MainActivity.this,LostSettingActivity.class);
					startActivity(intent);

				} else {
					Toast.makeText(MainActivity.this, "密码不一致",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});

		setPwdBuilder.setView(view);
		// 显示
		dialog = setPwdBuilder.show();
	}

	/**
	 * 直接输入密码
	 */
	private void showEnterDialog() {
		AlertDialog.Builder inPwdBuilder = new Builder(MainActivity.this);
		View view = View.inflate(MainActivity.this, R.layout.dialog_input_password,
				null);
		inputPassword = (EditText) view.findViewById(R.id.reinput_pwd);
		inputEnsureBtn = (Button) view.findViewById(R.id.ensure);
		inputCancelBtn = (Button) view.findViewById(R.id.cancel);

		inputCancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		inputEnsureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取出密码
				String password = inputPassword.getText().toString().trim();
				String entryPassword=MD5Entry.mdPassword(password);//加密
				String setPassword = sp.getString("password", "");
				// 比较一和二是否相同，不同提示
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(MainActivity.this, "密码为空，请输入密码",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (MD5Entry.mdPassword(password).equals(setPassword)) {
					dialog.dismiss();
					// 进入主界面
					System.out.println("呵呵，进入主界面");
					Intent intent=new Intent(MainActivity.this,LostSettingActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "密码错误",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});

		inPwdBuilder.setView(view);
		// 显示
		dialog = inPwdBuilder.show();
	}

	/**
	 * 判断是否设置过密码
	 * 
	 * @return
	 */
	private boolean isSetPas() {
		String pwd = sp.getString("password", null);
		// if (TextUtils.isEmpty(pwd)) {
		// return false;
		// }
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

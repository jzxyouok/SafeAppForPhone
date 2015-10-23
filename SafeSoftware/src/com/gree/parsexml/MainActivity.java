package com.gree.parsexml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.gree.tools.StreamUtils;
import com.gree.tools.UIUtils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView splash_version;
	private PackageManager PM;
	private PackageInfo PI;
	String path = "www.baidu.com";
	private int clientVersionInt;
	private int serverVersionInt;
	private Handler handler;
	private ImageView loadingImg;
	private AnimationDrawable ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();
		checkVersion();
	}

	public void initView() {
		splash_version = (TextView) findViewById(R.id.tv_splash_version);
		//获取软件自身版本信息
		PM = getPackageManager();
		try {
			PI = PM.getPackageInfo(getPackageName(), 0);
			String clientVersion = PI.versionName;

			splash_version.setText(clientVersion);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loadingImg=(ImageView)findViewById(R.id.loading);
		ad=(AnimationDrawable) loadingImg.getDrawable();
	}
	
	public void initListener(){
		loadingImg.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ad.start();
			}
		}, 100);
	}

	// 检查更新 子线程联网操作
	private void checkVersion() {
		Log.e("MainActivity", "开始执行");
		new Thread() {
			public void run() {
				try {
					URL url = new URL(getResources().getString(
							R.string.serviceurl));
					// 得到一个httpurlconnection
					HttpsURLConnection conn = (HttpsURLConnection) url
							.openConnection();
					// 设置请求方式
					conn.setRequestMethod("GET");
					// 超时时间
					conn.setConnectTimeout(1000);
					// 得到getResponseCode响应码
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream is = conn.getInputStream();// json文本
						String myJson = StreamUtils.readStream(is);
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+myJson);
						if (TextUtils.isEmpty(myJson)) {
							// 获取失败
							Log.e("MainActivity", "获取失败");
						} else {
							// 把json转化成一个字符串
							JSONObject jsonObj = new JSONObject(myJson);
							serverVersionInt  = jsonObj.getInt("version");
							if (clientVersionInt==serverVersionInt) {
								Log.e("MainActivity", "不用升级，版本相同");
								Message msg=Message.obtain();
								//msg.what=LOAD
							}else{
								Log.e("MainActivity", "升级");
							}
							
							Log.e("MainActivity", "获取成功");
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					//生成错误码 请联系客服，比如505，404等等错误
					UIUtils.showToast(MainActivity.this, "111");
				} catch (NotFoundException e) {
					e.printStackTrace();
					UIUtils.showToast(MainActivity.this, "112");
				} catch (IOException e) {
					e.printStackTrace();
					UIUtils.showToast(MainActivity.this, "113");
				} catch (JSONException e) {
					e.printStackTrace();
					UIUtils.showToast(MainActivity.this, "114");
				}
			};
		}.start();

	}
	
}

package com.gree.parsexml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gree.tools.StreamUtils;
import com.gree.tools.UIUtils;

public class MainActivity extends Activity {
	protected static final int ENTER_HOME = 100;
	protected static final int SHOW_UP_LOGO = 200;
	private TextView splash_version;
	private PackageManager PM;
	private PackageInfo PI;
	String path = "www.baidu.com";
	private int clientVersionInt;
	private int serverVersionInt;
	private ImageView loadingImg;
	private AnimationDrawable ad;
	private String descripe = "";
	private String spkURL = "";
	private AlphaAnimation aanim;
	private RelativeLayout rl;
	private TextView progress;

	private SharedPreferences sp;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 最新版本，进入主界面
			case 100:
				enterHome();
				break;
			// 需要升级吗对话框
			case 200:
				upDataDialog();
				break;

			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		initView();
		initListener();

	}

	public void initView() {
		splash_version = (TextView) findViewById(R.id.tv_splash_version);
		// 获取软件自身版本信息
		PM = getPackageManager();
		try {
			PI = PM.getPackageInfo(getPackageName(), 0);
			String clientVersion = PI.versionName;

			splash_version.setText(clientVersion);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 旋转进度条
		loadingImg = (ImageView) findViewById(R.id.loading);
		ad = (AnimationDrawable) loadingImg.getDrawable();
		// 渐变界面
		aanim = new AlphaAnimation(0.2f, 1.0f);
		aanim.setDuration(500);
		// 设置渐变动画
		rl = (RelativeLayout) findViewById(R.id.rl);
		rl.startAnimation(aanim);
		// 进度
		progress = (TextView) findViewById(R.id.progress);
		// sp
		sp = getSharedPreferences("updata", MODE_PRIVATE);
	}

	public void initListener() {
		/**
		 * 进度条动画
		 */
		loadingImg.postDelayed(new Runnable() {

			@Override
			public void run() {
				// loading动画
				ad.start();
			}
		}, 100);
		/**
		 * 是否要升级
		 */
		boolean updata = sp.getBoolean("updata", false);
		if (updata) {
			checkVersion();
		} else {
			// 自动升级关闭
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// 进入主界面
					enterHome();

				}
			}, 2000);
		}
	}

	/**
	 * 检查更新 子线程联网操作
	 */
	private void checkVersion() {
		Log.e("MainActivity", "开始执行");
		final Message myMsg = Message.obtain();
		final long startTime = System.currentTimeMillis();

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
						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
										+ myJson);
						if (TextUtils.isEmpty(myJson)) {
							// 获取失败
							Log.e("MainActivity", "获取失败");
						} else {
							// 把json转化成一个字符串
							JSONObject jsonObj = new JSONObject(myJson);
							serverVersionInt = jsonObj.getInt("version");
							descripe = jsonObj.getString("description");
							spkURL = jsonObj.getString("apkURL");
							if (clientVersionInt == serverVersionInt) {
								Log.e("MainActivity", "不用升级，版本相同");
								Message msg = Message.obtain();
								myMsg.what = ENTER_HOME;
								// msg.what=LOAD
							} else {
								Log.e("MainActivity", "升级");
								myMsg.what = SHOW_UP_LOGO;
							}

							Log.e("MainActivity", "获取成功");
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					// 生成错误码 请联系客服，比如505，404等等错误
					UIUtils.showToast(MainActivity.this, "111");
					enterHome();
				} catch (NotFoundException e) {
					e.printStackTrace();
					UIUtils.showToast(MainActivity.this, "112");
					enterHome();
				} catch (IOException e) {
					e.printStackTrace();
					UIUtils.showToast(MainActivity.this, "113");
					enterHome();
				} catch (JSONException e) {
					e.printStackTrace();
					UIUtils.showToast(MainActivity.this, "114");
					enterHome();
				} finally {
					long endTime = System.currentTimeMillis();
					// 花的时间
					long sTime = endTime - startTime;
					if (sTime < 2000) {
						try {
							Thread.sleep(2000 - sTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
							UIUtils.showToast(MainActivity.this, "连接超时");
						}
					}
					handler.sendMessage(myMsg);
				}
			};
		}.start();

	}

	/**
	 * 进入主界面
	 */
	protected void enterHome() {
		Intent intent = new Intent(MainActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 升级对话框
	 */
	protected void upDataDialog() {
		// Intent intent = new Intent(MainActivity.this, NewVersionToast.class);
		// startActivity(intent);
		// finish();
		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		dialog.setTitle("呵呵，你想删了我？");
		dialog.setMessage("呵呵，你觉得我会恩准你吗？");
		// dialog.setCancelable(false);//这个选项会导致点击手机返回键或者对话框空白处没反应，对话框强制存在 慎用
		// 点击返回框或者空白处对话框消失
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// 进入主界面
				enterHome();
				dialog.dismiss();// 注销对话框

			}
		});
		dialog.setPositiveButton("呵呵", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 升级程序，下载
				// sd卡存在
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// afinal框架的使用
					FinalHttp fh = new FinalHttp();
					fh.download(spkURL,
							Environment.getExternalStorageDirectory()
									+ "/safeApp2.0.apk",
							new AjaxCallBack<File>() {

								// 下载失败
								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									super.onFailure(t, errorNo, strMsg);
									// 下载失败
									UIUtils.showToast(MainActivity.this, "下载失败");

								}

								// count:要下载文件的总大小 current:当前下载位置
								@Override
								public void onLoading(long count, long current) {
									super.onLoading(count, current);
									progress.setVisibility(View.VISIBLE);
									// 显示进度
									int progressValue = (int) (current * 100 / count);
									progress.setText(progressValue + "%");

								}

								// 下载成功
								@Override
								public void onSuccess(File t) {
									super.onSuccess(t);
									UIUtils.showToast(MainActivity.this, "下载成功");
									// 安装应用
									installApk(t);
								}

								/**
								 * 安装apk 用意图安装
								 * 
								 * @param t
								 */
								private void installApk(File t) {
									Intent intent = new Intent(
											"android.intent.action.View");
									intent.addCategory("android.intent.category.DEFAULT");// 类型
									intent.setDataAndType(Uri.fromFile(t),
											"application/vnd.android.package-archive");
									startActivity(intent);
								}

							});
				}
				// sdk不存在
				else {
					UIUtils.showToast(MainActivity.this, "请检查sdk是否插好，检测不到sd卡");
					return;
				}
			}
		});
		dialog.setNegativeButton("哈哈", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 不升级 注销对话框
				dialog.dismiss();
				enterHome();
			}
		});
		// 显示出来
		dialog.show();
	}

}

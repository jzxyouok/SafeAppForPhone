package com.gree.tools;

import android.app.Activity;
import android.widget.Toast;

public class UIUtils {
	public static void showToast(final Activity context, final String msg) {
		// 如果是主线程
		if ("main".equals(Thread.currentThread().getName())) {
			//吐司
			Toast.makeText(context, msg, 1).show();
		} else {
			//获得ui线程
			context.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, msg, 1).show();
				}
			});
		}
	}

}

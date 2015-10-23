package com.gree.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	/**
	 * 把流里的内容转化成字符串
	 * 
	 * @param is
	 *            流里的内容
	 * @return
	 */
	public static String readStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			is.close();
			return new String(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}

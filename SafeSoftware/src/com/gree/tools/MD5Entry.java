package com.gree.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Entry {
	
	public static String mdPassword(String password){

		MessageDigest md;//信息
		byte[] result;
		StringBuffer sb = null;
		try {
			md=MessageDigest.getInstance("md5");//信息
			result=md.digest(password.getBytes());
			sb=new StringBuffer();
			//与运算0xff
			for(byte b:result){
				int passwordInt=b & 0xff;
				String passwordStr=Integer.toHexString(passwordInt);
				System.out.println("passwordStr:  "+passwordStr);
				if (passwordStr.length()==1) {
					sb.append("0");//因为结果中有只占四位的，要补全八位，在最前面补0
				}
				sb.append(passwordStr);
			}
			System.out.println("last md5:"+sb.toString());
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		
	}

}

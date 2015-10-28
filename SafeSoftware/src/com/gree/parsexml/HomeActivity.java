package com.gree.parsexml;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class HomeActivity extends Activity {
	private GridView my_gridview;
	//文字内容
	private String txts[]={"手机防盗","通讯卫视","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
	//图片资源
	private int imgs[]={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_main);
		initView();
		initListener();
		
	}
	
	public void initView(){
		my_gridview=(GridView)findViewById(R.id.my_gridview);
	}
	
	public void initListener(){
		//数据源
		ArrayList<HashMap<String, Object>> myList=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map=new HashMap<String, Object>();
		for (int i = 0; i < txts.length; i++) {
			map.put("function", txts[i]);
			map.put("image", imgs[i]);
			myList.add(map);
		}
		//适配器
		SimpleAdapter adapter=new SimpleAdapter(
				this, 
				myList, //数据
				R.layout.home_item,//资源
				new String[]{"function","image"},//map中关键字
				new int[]{R.id.itemimg,R.id.itemtext});
		
		my_gridview.setAdapter(adapter);
	}
	
	public class SelfAdapter extends BaseAdapter{
		//得到item总数目
		@Override
		public int getCount() {
			return txts.length;//数组里面length是个属性
		}
		//得到每一项
		@Override
		public Object getItem(int position) {
			
			
			return null;
		}
		//
		@Override
		public long getItemId(int position) {
			
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			View v=View.inflate(this, R.layout.home_item, null);
			
			return null;
		}
		
	}

}

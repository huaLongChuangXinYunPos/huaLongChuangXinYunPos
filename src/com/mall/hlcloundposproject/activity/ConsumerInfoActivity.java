package com.mall.hlcloundposproject.activity;

import java.util.ArrayList;


import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.Content;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.adapter.QueryFragmentListAdapter;
import com.mall.hlcloundposproject.db.MyOpenHelper;
import com.mall.hlcloundposproject.db.OperationDbTableUtils;
import com.mall.hlcloundposproject.entity.Consumer;
import com.mall.hlcloundposproject.fragments.AddConsumerInfoFragment;
import com.mall.hlcloundposproject.fragments.FragmentCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class ConsumerInfoActivity extends FragmentActivity implements FragmentCallback, OnItemClickListener, OnClickListener{
	
	@ViewInject(R.id.query_goods_select)
	private ListView listView;
	
	@ViewInject(R.id.ic_back)
	private ImageView icBack;
	
	@ViewInject(R.id.add_consumer_info)
	private ImageView addConsumerInfo;
	
	private ArrayList<Consumer> list;
	private QueryFragmentListAdapter adapter;
	private MyOpenHelper openHelper;

	private SQLiteDatabase userDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumer_info);
		ViewUtils.inject(this);
		openHelper = new MyOpenHelper(this, Content.USER_INFO_DB_NAME);
		userDb = openHelper.getReadableDatabase();
		list = new ArrayList<Consumer>();
		adapter = new QueryFragmentListAdapter(this,list);
		listView.setAdapter(adapter);
		
		initList();
		
		icBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		addConsumerInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddConsumerInfoFragment addInfo = AddConsumerInfoFragment.getInstance(openHelper);
				// 提示    输入会员     卡号：
				addInfo.show(getSupportFragmentManager(), "vipF");
			}
		});
		
		listView.setOnItemClickListener(this);
		
		adapter.setDeleteOnClickListener(this);
	}

	//初始化  list数组
	private void initList() {
		/**
		 * 到本地商品信息表中 查询该条码 ：
		 */
		Cursor cursor = userDb.query("t_"+Content.TABLR_CONSUMER_NAME, new String[]{"*"}, 
				null, null, null, null, null);
		if(cursor.getCount()!=0){
			while(cursor.moveToNext()){
				Consumer consumer = OperationDbTableUtils.consumerCursorToEntity(cursor);
				list.add(consumer);
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 信息    回调   添加到数据库中
	 */
	@Override
	public void fragmentCallback(String result, int fragmentAuthority) {
		switch(fragmentAuthority){
			case Configs.ADD_CONSUMER_INFO_SUCCESS_AUTHORITY:
				list.clear();
				initList();
				break;
			default:
			break;
		}
	}
	
	/**
	 * 添加     listView  的点击事件：
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//回调   回去 goods商品信息：
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("consumer", list.get(arg2));
		intent.putExtras(bundle);
		this.setResult(Configs.CONSUMER_INFO_RESULT_AUTHORITY, intent);
		finish();
	}

	
	/**
	 * 处理   单击删除事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Object tag = v.getTag();
		switch(id){
		//删除当前  list数据
		case R.id.add_consumer_delete_info:
			int position = (Integer) tag;
			Consumer consumer = list.get(position);
			//删除    数据库中信息：
			userDb = openHelper.getWritableDatabase();
			userDb.delete("t_"+Content.TABLR_CONSUMER_NAME, "name = ? and phone = ? ", new String[]{consumer.getName(),consumer.getPhone()});
			list.remove(position);
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

}

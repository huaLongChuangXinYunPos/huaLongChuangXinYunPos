package com.mall.hlcloundposproject.activity;

import java.util.ArrayList;

import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.Content;
import com.mall.hlcloundposproject.MainActivity;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.adapter.PayListViewAdapter;
import com.mall.hlcloundposproject.adapter.QueryFragmentListAdapter;
import com.mall.hlcloundposproject.db.MyOpenHelper;
import com.mall.hlcloundposproject.db.OperationDbTableUtils;
import com.mall.hlcloundposproject.entity.Goods;
import com.mall.hlcloundposproject.utils.ExitApplicationUtils;
import com.mall.hlcloundposproject.utils.KeyboardUtil;
import com.mall.hlcloundposproject.utils.MyToast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

public class QueryGoodsActivity extends Activity implements TextWatcher, OnItemClickListener, OnTouchListener {
	
	//获取  edittext:
	@ViewInject(R.id.query_fragment_edit)
	private EditText edittext;
		
	@ViewInject(R.id.query_list)
	private ListView listView;
	
	private MyOpenHelper goodsDataHelper;
	
	@ViewInject(R.id.ic_back)
	private ImageView icBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_goods);
		
		ViewUtils.inject(this);
		goodsDataHelper = new MyOpenHelper(this, Content.GOODS_DB_NAME);
	        
	    edittext.addTextChangedListener(this);
	    icBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	    
	    edittext.setOnTouchListener(this);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}
	private SQLiteDatabase goodsDataDb;
	private ArrayList<Goods> list;
	private QueryFragmentListAdapter adapter;
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		list = new ArrayList<Goods>();
		if(s.toString()!=null&&!s.toString().equals("")){
			goodsDataDb = goodsDataHelper.getWritableDatabase();
			goodsDataDb.beginTransaction();
			/**
			 * 到本地商品信息表中 查询该条码 ：
			 */
			Cursor cursor = goodsDataDb.query("t_"+Content.TABLE_FORMNALPRICE, new String[]{"*"}, 
					" cBarcode like '%"+s.toString()+"%'", null, null, null, null);
			goodsDataDb.setTransactionSuccessful();
			goodsDataDb.endTransaction();
			if(cursor.getCount()!=0){
				list = OperationDbTableUtils.goodsCursorToList(cursor,s.toString());
			}else{
				list.clear();
			}
		}else{
			list.clear();
		}
		adapter = new QueryFragmentListAdapter(this,list);
		
		listView.setAdapter(adapter);  
		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(this);
	}
	
	//添加事件
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//回调   回去 goods商品信息：
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("goods", list.get(arg2));
		intent.putExtras(bundle);
		this.setResult(Configs.QUERY_ACTIVITY_RESULT_CODE, intent);
		finish();
	}
	
	@Override
	protected void onDestroy() {
		if (goodsDataDb != null && goodsDataDb.isOpen()) {
			goodsDataDb.close();
		}
		super.onDestroy();
	}
	
	
	/**
	 * 添加  键盘事件
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			edittext.setInputType(InputType.TYPE_NULL);
			new KeyboardUtil(QueryGoodsActivity.this,getApplicationContext(),edittext).showKeyboard();
			
			return true;
		}
		return false;
	}
	
}

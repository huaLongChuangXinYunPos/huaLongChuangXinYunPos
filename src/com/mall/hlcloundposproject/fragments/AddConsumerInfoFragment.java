package com.mall.hlcloundposproject.fragments;

import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.Content;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.db.MyOpenHelper;
import com.mall.hlcloundposproject.entity.Consumer;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AddConsumerInfoFragment extends DialogFragment {

	private static MyOpenHelper useropenHelper;
	
	public static AddConsumerInfoFragment getInstance(MyOpenHelper openHelper) {
		useropenHelper = openHelper;
		return new AddConsumerInfoFragment();
	}
	
	private FragmentCallback callback;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();
	}
	
	@ViewInject(R.id.add_consumer_name)
	private EditText nameEt;
	
	@ViewInject(R.id.add_consumer_phone)
	private EditText phoneEt;
	
	@ViewInject(R.id.add_consumer_address)
	private EditText addressEt;
	
	@ViewInject(R.id.add_consumer_man)
	private RadioButton man;
	
	@ViewInject(R.id.add_consumer_woman)
	private RadioButton woman;
	
	private Button sureBt,exitBt;

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
		view = inflater.inflate(R.layout.add_consumerinfo_fragment, container,true);
		ViewUtils.inject(this,view);
		
		sureBt = (Button) view.findViewById(R.id.add_consumer_sure);
		exitBt = (Button) view.findViewById(R.id.add_consumer_exit);
		
		sureBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取   内容
				String name = nameEt.getText().toString().trim();
				String phone = phoneEt.getText().toString().trim();
				String address = addressEt.getText().toString().trim();
				String sex = man.isChecked()?"男":woman.isChecked()?"女":null;
				
				if(TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||
						TextUtils.isEmpty(address)){
					if(TextUtils.isEmpty(name)){
						nameEt.setError("姓名不准为空");
						nameEt.requestFocus();
					}else if(TextUtils.isEmpty(phone)){
						phoneEt.setError("电话不准为空");
						phoneEt.requestFocus();
					}else if(TextUtils.isEmpty(address)){
						addressEt.setError("地址不准为空");
						addressEt.requestFocus();
					}
				}else{
					//将数据    插入数据库
					SQLiteDatabase userDb = useropenHelper.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put("name", name);
					values.put("phone", phone);
					values.put("address",address);
					values.put("sex", sex);
					userDb.insert("t_"+Content.TABLR_CONSUMER_NAME, null, values);
					
					callback.fragmentCallback(null, Configs.ADD_CONSUMER_INFO_SUCCESS_AUTHORITY);
					onDestroyView();
				}
			}
		});
		
		exitBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDestroyView();
			}
		});
		
		return view;
	}

}

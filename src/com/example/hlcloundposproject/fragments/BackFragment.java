package com.example.hlcloundposproject.fragments;

import com.example.hlcloundposproject.Configs;
import com.example.hlcloundposproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * 商品  单号  查询    fragment
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-26
 */
public class BackFragment extends DialogFragment implements OnClickListener {
	
	@ViewInject(R.id.constume_back_fragment_inputEt)
	private EditText etInput;
	
	@ViewInject(R.id.constum_back_fragment_sure)
	private Button sureBtn;
	
	@ViewInject(R.id.constum_back_fragment_exit)
	private Button exitBtn;

	public static BackFragment getInstance() {
		return new BackFragment();
	}
	
	//定义   fragment的回调
	private FragmentCallback callback;

	private ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

	   /** 
         * 先设置     无标题样式的     对话框 
         */  
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
		View view = inflater.inflate(R.layout.consume_back_fragment, container,true);
		
		EditText ed = (EditText) view.findViewById(R.id.constume_back_fragment_inputEt);
		ed.setHint("请输入客退单号");
		
		ViewUtils.inject(this,view);
		
		initListener();
		
		return view;
	}
	
	/**
	 * 单击事件方法：
	 */
	private void initListener() {
		sureBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.constum_back_fragment_sure:
				
				//获取     卡号：
				String inputSellNum = etInput.getText().toString().trim();
				
				if(inputSellNum!=null&&!inputSellNum.equals("")){
					
					dialog = new ProgressDialog(getActivity());
					
					dialog.setMessage("正在购买商品...");
					
					dialog.show();
					
					onDestroyView();
					
					querySellNum(inputSellNum);//查询商品单号  是否存在：
					
				}else{
					etInput.setError("商品单号不准为空");
				}
				
				break;
			case R.id.constum_back_fragment_exit:
				//销毁     fragment
				onDestroyView();
				
				break;
			
			default:break;
			
		}
	}

	/**
	 * 查询商品单号：
	 */
	private void querySellNum(String inputSellNum) {
	
//		//TODO 查询     商品单号 ：   查询到商品   则      将数据回调给    activity:
		callback.fragmentCallback(inputSellNum, Configs.CONSUME_BACK_FRAGMENT_AUTHORITY);
		
		dialog.dismiss();
	}
}

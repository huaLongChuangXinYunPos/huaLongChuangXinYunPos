package com.mall.hlcloundposproject.fragments;

import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UpdateAmountFragment extends DialogFragment implements OnClickListener {
	
	@ViewInject(R.id.constume_back_fragment_inputEt)
	private EditText etInput;
	
	@ViewInject(R.id.constum_back_fragment_sure)
	private Button sureBtn;
	
	@ViewInject(R.id.constum_back_fragment_exit)
	private Button exitBtn;

	public static UpdateAmountFragment getInstance() {
		return new UpdateAmountFragment();
	}
	
	//定义   fragment的回调
	private FragmentCallback callback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		View view = inflater.inflate(R.layout.update_amount_fragment, container,true);
		
		EditText ed = (EditText) view.findViewById(R.id.constume_back_fragment_inputEt);
		
		ed.setHint("请输入商品数量");
		
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
				String etAmount = etInput.getText().toString().trim();
				
				callback.fragmentCallback(etAmount, Configs.UPDATE_FRAGMENT_AMOUNT);
				
				onDestroyView();
				
				break;
			case R.id.constum_back_fragment_exit:
				
				//销毁     fragment
				onDestroyView();
				
				break;
			
			default:break;
			
		}
	}
	
	
}



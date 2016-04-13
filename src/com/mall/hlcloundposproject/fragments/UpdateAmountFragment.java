package com.mall.hlcloundposproject.fragments;

import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.utils.NumKeysUtils;
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
import android.widget.ImageView;

public class UpdateAmountFragment extends DialogFragment implements OnClickListener {
	
	@ViewInject(R.id.update_amount_fragment_input)
	private EditText etInput;
	
	@ViewInject(R.id.update_amount_sure)
	private Button sureBtn;
	
	@ViewInject(R.id.update_amount_exit)
	private Button exitBtn;

	public static UpdateAmountFragment getInstance() {
		return new UpdateAmountFragment();
	}
	
	//定义   fragment的回调
	private FragmentCallback callback;
	
	private ImageView keysIc;
	
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
		
		keysIc = (ImageView) view.findViewById(R.id.keys);
		
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
		keysIc.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.update_amount_sure:
				String etAmount = etInput.getText().toString().trim();
				
				callback.fragmentCallback(etAmount, Configs.UPDATE_FRAGMENT_AMOUNT);
				
				onDestroyView();
				
				break;
			case R.id.update_amount_exit:
				
				//销毁     fragment
				onDestroyView();
				
				break;
				
			case R.id.keys:
				
				//弹出数字键盘
				NumKeysUtils keyDialog = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						etInput, NumKeysUtils.FLOAT,
						new NumKeysUtils.TextChangeListener() {
							@Override
							public void onTextChange(String value) {
								etInput.setText(value);
							}
						});
				keyDialog.show();
				
				break;
			
			default:break;
			
		}
	}
	
	
}



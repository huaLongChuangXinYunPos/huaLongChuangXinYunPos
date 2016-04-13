package com.mall.hlcloundposproject.fragments;

import java.math.BigDecimal;

import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.utils.NumKeysUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 商品  单号  查询    fragment
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-26
 */
public class PayBalanceFragmentDialog extends DialogFragment implements OnClickListener, TextWatcher {
	
	@ViewInject(R.id.pay_should_banlance)
	private TextView shouldPay; //应付金额
	
	@ViewInject(R.id.pay_input_banlance)
	private EditText inputPay;//实付金额
	
	@ViewInject(R.id.pay_overplus)
	private TextView overplusPay; //找零金额
	
	@ViewInject(R.id.pay_sure_btn)
	private Button btnSure;
	
	@ViewInject(R.id.pay_exit_btn)
	private Button btnExit;
	
	private static PayBalanceFragmentDialog dialog;
	
	public static PayBalanceFragmentDialog getInstance(String pay) {
		 
		dialog = new PayBalanceFragmentDialog();
		
		Bundle bundle = new Bundle();
		bundle.putString("pay", pay);
		dialog.setArguments(bundle);
		
		return dialog;
	}
	
	private FragmentCallback callback;

	private String[] payStrs;
	
	private ImageView keysIc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();//  回调给  activity：
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
		View view = inflater.inflate(R.layout.pay_balance_dialog, container,true);
		ViewUtils.inject(this,view);
		
		String pay = getArguments().getString("pay", null);
		payStrs = pay.split("-");
		
		shouldPay.setText(payStrs[0]);
		
		inputPay.addTextChangedListener(this);
		btnExit.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		
		keysIc = (ImageView) view.findViewById(R.id.key_icon);
		
		keysIc.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
			case R.id.pay_sure_btn:  
				//确认按钮      确认  回调  activity
				//清除数据
				if(!inputPay.getText().toString().trim().equals("")&&inputPay.getText().toString().trim()!=null){
					String backMoney = 
							shouldPay.getText().toString().trim()+"-"+
									inputPay.getText().toString().trim()+"-"+
									overplusPay.getText().toString().trim()+"-"+
									payStrs[1];
					
					callback.fragmentCallback(backMoney, Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY);
					
					onDestroyView();
				}else{
					inputPay.setError("输入结算金额");
				}

				break;
				
			case R.id.pay_exit_btn:
				//取消  结算界面：
				onDestroyView();
				
			case R.id.key_icon:
				
				//弹出数字键盘
				NumKeysUtils keyDialog = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						inputPay, NumKeysUtils.FLOAT,new NumKeysUtils.TextChangeListener() {
							@Override
							public void onTextChange(String value) {
								inputPay.setText(value);
							}
						});
				keyDialog.show();
				
				break;
		}
	}
	
	/**
	 * 监听   输入  金额的变化：
	 */
	@Override
	public void afterTextChanged(Editable arg0) {
		if(arg0.toString()==null||arg0.toString().equals("")){
			overplusPay.setText(0.0+"");
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//数据     正在改变的     时候     修改     找零金额：
		if(s.toString()!=null&&!s.toString().equals("")){
			float inputMoney = Float.parseFloat(s.toString());
			if((inputMoney - Float.parseFloat(payStrs[0]))>=0){
				overplusPay.setText(getFormatFloat((inputMoney - Float.parseFloat(payStrs[0]))+"").toString());
				btnSure.setEnabled(true);
				inputPay.setError(null);
			}else{
				inputPay.setError("当前金额输入错误");
				btnSure.setEnabled(false);
			}
		}
	}

	public BigDecimal getFormatFloat(String floatNum){
		BigDecimal bd = new BigDecimal(floatNum);
		bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);  
		return bd;
	}
}

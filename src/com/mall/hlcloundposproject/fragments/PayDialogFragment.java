package com.mall.hlcloundposproject.fragments;

import java.util.ArrayList;

import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.adapter.PayListViewAdapter;
import com.mall.hlcloundposproject.entity.PayWayMap;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 自定义  dialog警告框   弹出后  用于用户选择需要  的支付方式：
 * @author hl
 * zhaoq_hero@163.com
 */
public class PayDialogFragment extends DialogFragment implements OnItemClickListener{
	
	/**
	 * 用volatile修饰的变量 
	 * 线程在每次使用变量的时候  都会读取变量修改后的值
	 * volatile用来进行原子性操作
	 */
	private static volatile PayDialogFragment dialog = null;
	
	@ViewInject(R.id.pay_caculate_way)
	private GridView gridView;

	private ArrayList<String> list;
	
	private ArrayList<PayWayMap> payWays;
	
	@ViewInject(R.id.pay_fragment_money)
	private TextView payMon;
	
	/*
	 * 使用 onCreateView()
	 */
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
        Bundle savedInstanceState) {  
		
        /** 
         * 先设置   无标题样式的  对话框 
         */  
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
        View view = inflater.inflate(R.layout.pay_dialog_fragment, container,true);  
        ViewUtils.inject(this, view);
        
        list = getArguments().getStringArrayList("list");
        payWays = new ArrayList<PayWayMap>();
        payMon.setText("确认付款:"+getArguments().getString("payMoney")+"元");
        
        initPayWays();
        
        PayListViewAdapter adapter = new PayListViewAdapter(getActivity(),payWays);
        
        gridView.setAdapter(adapter);  
        adapter.notifyDataSetChanged();
        
        gridView.setOnItemClickListener(this);
        
        return view;  
    }

	/**
	 * 初始化   map对象：
	 */
	private void initPayWays() {
		for(String  str : list){
			PayWayMap payWayMap = new PayWayMap();
			if(str.equals("人民币")){
				payWayMap.setImageView(R.drawable.pay_qianbaokoukuan);//pay_qianbaokoukuan
				payWayMap.setTextView(str);
			}else if(str.equals("微信")){
				payWayMap.setImageView(R.drawable.weixin);
				payWayMap.setTextView(str);
			}else if(str.equals("支付宝")){
				payWayMap.setImageView(R.drawable.pay_zhifubao);
				payWayMap.setTextView(str);
			}else if(str.equals("银联卡")){
				payWayMap.setImageView(R.drawable.pay_bank_card);
				payWayMap.setTextView(str);
			}else if(str.equals("钱包扣款")){
				payWayMap.setImageView(R.drawable.pay_chuzhihuanbi);//pay_chuzhihuanbi
				payWayMap.setTextView(str);
			}else if(str.equals("礼金扣款")){
				payWayMap.setImageView(R.drawable.pay_lijinkoukuan);
				payWayMap.setTextView(str);
			}else if(str.equals("礼券")){
				payWayMap.setImageView(R.drawable.pay_liquan);
				payWayMap.setTextView(str);
			}else if(str.equals("储值换货")){
				payWayMap.setImageView(R.drawable.pay_rmb);
				payWayMap.setTextView(str);
			}else if(str.equals("现金卡")){
				payWayMap.setImageView(R.drawable.pay_xianjincard);
				payWayMap.setTextView(str);
			}else if(str.equals("京东到家")){
				payWayMap.setImageView(R.drawable.pay_jingdong_to_home);
				payWayMap.setTextView(str);
			}else if(str.equals("京东差价")){
				payWayMap.setImageView(R.drawable.pay_jingdong_chajia);
				payWayMap.setTextView(str);
			}else if(str.equals("京东补价")){
				payWayMap.setImageView(R.drawable.pay_jingdong_bujia);
				payWayMap.setTextView(str);
			}else{
				payWayMap.setImageView(R.drawable.ic_launcher);
				payWayMap.setTextView(str);
			}
			payWays.add(payWayMap);
		}
	}

	public static PayDialogFragment getInstance(ArrayList<String> list,String payMoney) {

			dialog = new PayDialogFragment();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("list", list);
			bundle.putString("payMoney",payMoney);
			dialog.setArguments(bundle);
		
		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();
	}
	
    private	FragmentCallback callback;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		callback.fragmentCallback(arg2+"", Configs.GET_CALCULATE_WAY_AUTHORITY);
		onDestroyView();
	}
	
}

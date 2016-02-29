package com.example.hlcloundposproject.fragments;

import java.util.ArrayList;

import com.example.hlcloundposproject.adapter.PayListViewAdapter;
import com.example.hlcloundposproject.entity.PayWayMap;
import com.example.hlcloundposproject.Configs;
import com.example.hlcloundposproject.R;
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

/**
 * �Զ���  dialog�����   ������  �����û�ѡ����Ҫ  ��֧����ʽ��
 * @author hl
 * zhaoq_hero@163.com
 */
public class PayDialogFragment extends DialogFragment implements OnItemClickListener{
	
	/**
	 * ��volatile���εı��� 
	 * �߳���ÿ��ʹ�ñ�����ʱ��  �����ȡ�����޸ĺ��ֵ
	 * volatile��������ԭ���Բ���
	 */
	private static volatile PayDialogFragment dialog = null;
	
	@ViewInject(R.id.pay_caculate_way)
	private GridView gridView;

	private ArrayList<String> list;
	
	private ArrayList<PayWayMap> payWays;
	
	/*
	 * ʹ�� onCreateView()
	 */
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
        Bundle savedInstanceState) {  
		
        /** 
         * ������   �ޱ�����ʽ��  �Ի��� 
         */  
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
        View view = inflater.inflate(R.layout.pay_dialog_fragment, container,true);  
        ViewUtils.inject(this, view);
        
        list = getArguments().getStringArrayList("list");
        payWays = new ArrayList<PayWayMap>();
        
        initPayWays();
        
        PayListViewAdapter adapter = new PayListViewAdapter(getActivity(),payWays);
        
        gridView.setAdapter(adapter);  
        adapter.notifyDataSetChanged();
        
        gridView.setOnItemClickListener(this);
        
        return view;  
    }

	/**
	 * ��ʼ��   map����
	 */
	private void initPayWays() {
		for(String  str : list){
			PayWayMap payWayMap = new PayWayMap();
			if(str.equals("�����")){
				payWayMap.setImageView(R.drawable.pay_rmb);
				payWayMap.setTextView(str);
			}else if(str.equals("������")){
				payWayMap.setImageView(R.drawable.pay_bank_card);
				payWayMap.setTextView(str);
			}else if(str.equals("Ǯ���ۿ�")){
				payWayMap.setImageView(R.drawable.pay_qianbaokoukuan);
				payWayMap.setTextView(str);
			}else if(str.equals("���ۿ�")){
				payWayMap.setImageView(R.drawable.pay_lijinkoukuan);
				payWayMap.setTextView(str);
			}else if(str.equals("��ȯ")){
				payWayMap.setImageView(R.drawable.pay_liquan);
				payWayMap.setTextView(str);
			}else if(str.equals("��ֵ����")){
				payWayMap.setImageView(R.drawable.pay_chuzhihuanbi);
				payWayMap.setTextView(str);
			}else if(str.equals("�ֽ�")){
				payWayMap.setImageView(R.drawable.pay_xianjincard);
				payWayMap.setTextView(str);
			}else if(str.equals("��������")){
				payWayMap.setImageView(R.drawable.pay_jingdong_to_home);
				payWayMap.setTextView(str);
			}else if(str.equals("�������")){
				payWayMap.setImageView(R.drawable.pay_jingdong_chajia);
				payWayMap.setTextView(str);
			}else if(str.equals("��������")){
				payWayMap.setImageView(R.drawable.pay_jingdong_bujia);
				payWayMap.setTextView(str);
			}else if(str.equals("΢��")){
				payWayMap.setImageView(R.drawable.pay_wechat);
				payWayMap.setTextView(str);
			}else if(str.equals("֧����")){
				payWayMap.setImageView(R.drawable.pay_zhifubao);
				payWayMap.setTextView(str);
			}else{
				payWayMap.setImageView(R.drawable.ic_launcher);
				payWayMap.setTextView(str);
			}
			payWays.add(payWayMap);
		}
	}

	public static PayDialogFragment getInstance(ArrayList<String> list) {

			dialog = new PayDialogFragment();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("list", list);
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

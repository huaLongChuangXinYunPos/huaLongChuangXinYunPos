package com.mall.hlcloundposproject.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.utils.MyToast;
import com.mall.hlcloundposproject.utils.NumKeysUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

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
import android.widget.ImageView;
import android.widget.Toast;

/**
 * vip  ���Ų�ѯ
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-26
 */
public class VipFragment extends DialogFragment implements OnClickListener {
	
	public static VipFragment getInstance() {
		return new VipFragment();
	}
	
	@ViewInject(R.id.vip_fragment_inputEt)
	private EditText etInput;
	
	@ViewInject(R.id.vip_calulate_sure)
	private Button sureBtn;
	
	@ViewInject(R.id.vip_calulate_exit)
	private Button exitBtn;
	
	//����   fragment�Ļص�
	private FragmentCallback callback;

	private ProgressDialog dialog;
	
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
		
		View view = inflater.inflate(R.layout.vip_fragment, container,true);
		
		ViewUtils.inject(this,view);
		
		keysIc = (ImageView) view.findViewById(R.id.keys_icon);
		
		initListener();
		
		return view;
	}

	/**
	 * �����¼�������
	 */
	private void initListener() {
		sureBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
		keysIc.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
			case R.id.vip_calulate_sure:
				//��ȡ     ���ţ�
				String inputVipNum = etInput.getText().toString().trim();
				
				if(inputVipNum!=null&&!inputVipNum.equals("")){

					dialog = new ProgressDialog(getActivity());
					
					dialog.setMessage("���ڲ�ѯ��Ա��Ϣ...");
					
					dialog.show();
					
					onDestroyView();
					//��ѯvip����   �Ƿ���ڣ�
					queryVipInfo(String.format(Configs.SERVER_BASE_URL+Configs.QUERY_VIP_INFO,inputVipNum));
					
				}else{
					etInput.setError("���Ų�׼Ϊ��");
				}
				
				break;
			case R.id.vip_calulate_exit:
				//����     fragment
				onDestroyView();
				break;
				
			case R.id.keys_icon:
				//�������ּ���
				NumKeysUtils keyDialog = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						etInput, NumKeysUtils.INT,
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
	/**
	 * ��ѯ  vip����  �Ƿ���ڣ�
	 */
	private void queryVipInfo(String url) {
		//ʹ��   volley��ܣ�
		//ʵ����   �������
		StringRequest request = new StringRequest(url,new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				
//				{"resultStatus":1,"data":[{"cVipno":"120001","fCurValue":120.5000}]}
				
				try {
					JSONObject json = new JSONObject(response);
					int resultStatus = json.getInt("resultStatus");
					if(resultStatus==1){
						callback.fragmentCallback(response, Configs.VIP_FRAGMENT_QUHORITY);
						MyToast.ToastIncenter(getActivity(),  "��ӭʹ��vip��,�Ѹ���vip��Ʒ...").show();
					}else{
						MyToast.ToastIncenter(getActivity(), "��ǰvip�Ų�����,�����Ƿ����...").show();
					}
					
					dialog.dismiss();
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				dialog.dismiss();
				MyToast.ToastIncenter(getActivity(), "����������Ƿ���...").show();
			}
		});
		
		//���� ������У�
		Volley.newRequestQueue(getActivity()).add(request);
	}
	
}

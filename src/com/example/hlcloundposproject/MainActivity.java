package com.example.hlcloundposproject;


import hardware.print.printer;
import hardware.print.printer.PrintType;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.hlcloundposproject.R;
import com.example.hlcloundposproject.activity.LoginActivity;
import com.example.hlcloundposproject.adapter.GoodsAdapter;
import com.example.hlcloundposproject.db.MyOpenHelper;
import com.example.hlcloundposproject.db.OperationDbTableUtils;
import com.example.hlcloundposproject.entity.Goods;
import com.example.hlcloundposproject.entity.SpecialGoods;
import com.example.hlcloundposproject.entity.User;
import com.example.hlcloundposproject.entity.VIPGoods;
import com.example.hlcloundposproject.fragments.BackFragment;
import com.example.hlcloundposproject.fragments.FragmentCallback;
import com.example.hlcloundposproject.fragments.PayBalanceFragmentDialog;
import com.example.hlcloundposproject.fragments.PayDialogFragment;
import com.example.hlcloundposproject.fragments.SelectFormTempFragment;
import com.example.hlcloundposproject.fragments.UpdateAmountFragment;
import com.example.hlcloundposproject.fragments.UpdateUserPwdDialog;
import com.example.hlcloundposproject.fragments.VipFragment;
import com.example.hlcloundposproject.tasks.GetGoodsInfoAsyncTask;
import com.example.hlcloundposproject.tasks.TaskCallBack;
import com.example.hlcloundposproject.tasks.TaskResult;
import com.example.hlcloundposproject.utils.FastJsonUtils;
import com.example.hlcloundposproject.utils.MyProgressDialog;
import com.example.hlcloundposproject.utils.TimeUtils;
import com.example.hlcloundposproject.utils.VolleyUtils;
import com.example.hlcloundposproject.utils.VolleyUtils.VolleyCallback;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mining.app.zxing.MipcaActivityCapture;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.barcode.Scanner;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements TaskCallBack,
		FragmentCallback, OnClickListener, OnItemClickListener, VolleyCallback {

	@ViewInject(R.id.keys_1)
	private Button keys_1;
	@ViewInject(R.id.keys_2)
	private Button keys_2;
	@ViewInject(R.id.keys_3)
	private Button keys_3;
	@ViewInject(R.id.keys_4)
	private Button keys_4;
	@ViewInject(R.id.keys_5)
	private Button keys_5;
	@ViewInject(R.id.keys_6)
	private Button keys_6;
	@ViewInject(R.id.keys_7)
	private Button keys_7;
	@ViewInject(R.id.keys_8)
	private Button keys_8;
	@ViewInject(R.id.keys_9)
	private Button keys_9;
	@ViewInject(R.id.keys_0)
	private Button keys_0;
	@ViewInject(R.id.keys_dot)
	private Button keys_dot;
	@ViewInject(R.id.keys_back_space)
	private Button keys_back_space;

	@ViewInject(R.id.total_ago_money)
	private TextView agoMoney;// ��Ʒԭ��
	// @ViewInject(R.id.total_acc_price)
	// private TextView accPrice;//��ǰ�۸�
	@ViewInject(R.id.total_special_price)
	private TextView spPrice;// �ؼ���Ʒ
	@ViewInject(R.id.total_vip_price)
	private TextView vipPrice;// vip��Ʒ
	@ViewInject(R.id.total_money)
	private TextView totalMoney;// ��ǰ���ܼ�

	@ViewInject(R.id.main_scan_etScanCodeBar)
	private EditText etCodeBar;
	private Editable etext;
	private String codebar;

	@ViewInject(R.id.main_scan_btn_sure)
	private Button scanBtnSure;
	@ViewInject(R.id.main_scan_btn_fkeys)
	private Button scanBtnFkeys;

	@ViewInject(R.id.main_f_keys)
	private View viewFkeys;

	@ViewInject(R.id.main_keys_others_vip)
	private Button vip;
	@ViewInject(R.id.main_keys_others_calcuate)
	private Button calcute;
	@ViewInject(R.id.main_keys_others_re_print)
	private Button rePrint;
	@ViewInject(R.id.main_keys_others_goods_back)
	private Button goodBack;
	@ViewInject(R.id.main_keys_others_amount)
	private Button amount;
	@ViewInject(R.id.main_keys_others_constume_back)
	private Button constumeBack;
	@ViewInject(R.id.main_keys_wait_form)
	private Button waitForm;
	@ViewInject(R.id.main_keys_go_form)
	private Button goForm;

	@ViewInject(R.id.main_keys_ln)
	private LinearLayout llayout;
	private boolean isShow = false;

	private List<Goods> list;
	private GoodsAdapter adapter;
	private ListView tableListView;
	private String codeBarStr;

	private MyOpenHelper goodsDataHelper;
	private MyOpenHelper tempDatahelper;
	private SQLiteDatabase goodsDataDb;
	private SQLiteDatabase tempDataDb;

	@ViewInject(R.id.main_keysF1)
	private Button btnf1;
	@ViewInject(R.id.main_keysF2)
	private Button btnf2;
	@ViewInject(R.id.main_keysF3)
	private Button btnf3;
	@ViewInject(R.id.main_keysF4)
	private Button btnf4;
	@ViewInject(R.id.main_keysF5)
	private Button btnf5;
	@ViewInject(R.id.main_keysF6)
	private Button btnf6;
	@ViewInject(R.id.main_keysF7)
	private Button btnf7;
	@ViewInject(R.id.main_keysF8)
	private Button btnf8;
	@ViewInject(R.id.main_keysF9)
	private Button btnf9;
	@ViewInject(R.id.main_keysF10)
	private Button btnf10;
	@ViewInject(R.id.main_keysF11)
	private Button btnf11;
	@ViewInject(R.id.main_keysF12)
	private Button btnf12;

	private User user = null;
	private int listViewCurrCilckPosition = 0; // ��¼��ǰ ����� item
	private boolean clickItem = false;// ��¼��ǰ listView��item�Ƿ�ɵ����
	private AlertDialog dialog;// �����
	
	@ViewInject(R.id.ic_scan)
	private ImageView cordBarScan;


	private static final int GET_VIPGOODS_DATA_AUTHORITY = 1;// ��ȡ VIP��Ʒ��ʶ��ʶ

	private static final int GET_SPECIALGOODS_DATA_AUTHORITY = 2;// ��ȡ �ؼ� ��Ʒ��ʶ

	private static final int UPDATE_ALLGOODS_DATA_COMPLATE = 3; // ��� ���ݿ� ����
																// ֪ͨ���߳� ����ui
	private static final int GET_CALCULATE_WAY_AUTHORITY = 4; // ��ȡ ���㷽ʽ
	
	private static final int UPDATE_VIP_SCRORE_AUTHORITY = 5;//ˢ��    ��������   �첽�����
	
	printer m_printer = new printer();// ���� ��ӡ����
	private Handler handler = new MainHandler();// ���ܴ�ӡ�� �ش�����������

	private class MainHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case Scanner.BARCODE_READ: { // ���� ɨ����   ��������Ϣ
				etCodeBar.setText("");
				// ��ʾ�������������ݣ�
				etCodeBar.setText((String) msg.obj);
				// ����ƶ��� �༭����һ�У�
				etCodeBar.setSelection(etCodeBar.getText().length());
				// ��������
				Scanner.play();
				
				break;
			}

			case Scanner.BARCODE_NOREAD: { // ����  ɨ����  ��������Ϣ
				Toast.makeText(MainActivity.this, "δɨ�赽������Ϣ,����ɨ�����豸", 0)
						.show();
				break;
			}

			case UPDATE_ALLGOODS_DATA_COMPLATE: { // ���� ���� ��Ʒ��Ϣ�� ui
				proDialog.dismiss();
				Toast.makeText(MainActivity.this, "��Ʒ��Ϣ�������...", 0).show();
				break;
			}

			default:
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (User) getIntent().getSerializableExtra("user");// ��ȡ��ǰ�û�����
		
		goodsDataHelper = new MyOpenHelper(MainActivity.this,
				Constants.GOODS_DB_NAME);
		tempDatahelper = new MyOpenHelper(MainActivity.this,
				Constants.TEMP_DATA_DB);

		ViewUtils.inject(this);

		etCodeBar.requestFocus(); // ȥ�������

		// ����   �������   ������ɫ
		ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
		tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));

		list = new ArrayList<Goods>();

		tableListView = (ListView) findViewById(R.id.list);

		initListener(this);

		adapter = new GoodsAdapter(this, list);
		adapter.registerDataSetObserver(adapterObserver);

		tableListView.setAdapter(adapter);
		tableListView.setOnItemClickListener(this);
		
		m_printer.Open();
		
	}

	private boolean vipInput = false;// ��¼��ǰ�Ƿ���VIP�û�
	
	private float vipScore;//��¼  ��Ա������Ϣ

	private DataSetObserver adapterObserver = new DataSetObserver() {
		/**
		 * �Զ��ص��÷�����
		 */
		public void onChanged() {

			if (list.size() == 0) {
				vipInput = false;// ��ǰ�û� ��Ϊ��ͨ�û�
			}

			listViewCurrCilckPosition = 0;// ��ǰ ��ѡ���� ��Ϊ0;
			clickItem = false;// ��Ϊ ���ɵ��

			float agoSum = 0;// ԭ��
			// float accSum = 0;//�ּ�
			float spSum = 0;// �ؼ�
			float vipSum = 0;// vip��Ʒ
			float totalSum = 0;// С��

			String nowTime = TimeUtils.getSystemNowTime("yyyy-MM-dd");

			goodsDataDb = goodsDataHelper.getReadableDatabase();

			for (Goods item : list) {
				// �ж� ��Ʒ�� �Ƿ����ؼ���Ʒ�� ���� ��Ʒ�ؼ���Ϣ ��� ���ؼ���Ʒ
				// �����ؼ���Ʒ��Ϣ �� vip��Ʒ�۸񣺣�
				Cursor spGoodsCursor = OperationDbTableUtils.getSpGoodsCursor(goodsDataDb,nowTime, item);
				Cursor vipGoodsCursor = OperationDbTableUtils.getVipCursor(goodsDataDb,item);
				
				// ��ѯ�� �ؼ���Ʒ��Ϣ�Ļ���ֱ��ʹ�� �� �ؼ���Ʒ�� ����۸�
				if (spGoodsCursor.moveToFirst()) {
					// ��ȡ      �ؼ���Ʒ������۸�
					float spPrice = Float.parseFloat(spGoodsCursor
							.getString(spGoodsCursor
									.getColumnIndex("fPrice_SO")));

					if (vipInput && vipGoodsCursor.moveToFirst()) {
						float vipPrice = Float.parseFloat(vipGoodsCursor
								.getString(vipGoodsCursor
										.getColumnIndex("fVipPrice")));
						if (vipPrice < spPrice) {
							item.setfNormalPrice(vipPrice);
							item.setPayMoney(vipPrice * item.getAmount());
							
							vipSum += (vipPrice * item.getAmount());
						}
					} else {
						item.setfNormalPrice(spPrice);
						item.setPayMoney(spPrice * item.getAmount());
					}

					spSum += (spPrice * item.getAmount());// ʹ�� ����۸�
					// vip �۸�ʹ�� �ؼۼ۸�
					totalSum += (spPrice * item.getAmount());
					
				} else if (vipGoodsCursor.moveToFirst()) {
					float vipPrice = Float.parseFloat(vipGoodsCursor
							.getString(vipGoodsCursor
									.getColumnIndex("fVipPrice")));
					if (vipInput) {// ��ǰ Ϊvip�û� ���� ui
						item.setfNormalPrice(vipPrice);
						item.setPayMoney(vipPrice * item.getAmount());

						totalSum += (vipPrice * item.getAmount()); // �ܼ�
						
					}else{  //��ǰ  ����vip�û�
						totalSum += item.getPayMoney();
					}
					
					vipSum += (vipPrice * item.getAmount()); // ��ȡ vip ��Ʒ�۸�
				} else {
					totalSum += item.getPayMoney(); // �ܼ� ʹ��������Ʒ�۸�
				}

				// accSum += item.getPayMoney(); //�ּۣ�
				agoSum += item.getPayMoney(); // ��ȡ ԭ��
				
				vipScore += (item.getfVipScore()*item.getAmount());
			}
			// accPrice.setText(accSum+""); //�ּ� ��ʱ ���ز�Ҫ��

			agoMoney.setText(agoSum + "");
			spPrice.setText(spSum + "");
			vipPrice.setText(vipSum + "");
			totalMoney.setText(totalSum + "");
			
		}

		/**
		 * �� Adpater ���� notifyDataSetInvalidate() ʱ��ص�
		 */
		@Override
		public void onInvalidated() {
		}
	};

	private void initListener(MainActivity mainActivity) {
		
		cordBarScan.setOnClickListener(this);
		
		keys_1.setOnClickListener(this);
		keys_2.setOnClickListener(this);
		keys_3.setOnClickListener(this);
		keys_4.setOnClickListener(this);
		keys_5.setOnClickListener(this);
		keys_6.setOnClickListener(this);
		keys_7.setOnClickListener(this);
		keys_8.setOnClickListener(this);
		keys_9.setOnClickListener(this);
		keys_0.setOnClickListener(this);
		keys_dot.setOnClickListener(this);
		keys_back_space.setOnClickListener(this);

		scanBtnSure.setOnClickListener(this);
		scanBtnFkeys.setOnClickListener(this);

		vip.setOnClickListener(this);
		calcute.setOnClickListener(this);
		rePrint.setOnClickListener(this);
		goodBack.setOnClickListener(this);
		amount.setOnClickListener(this);
		constumeBack.setOnClickListener(this);
		waitForm.setOnClickListener(this);
		goForm.setOnClickListener(this);

		btnf1.setOnClickListener(this);
		btnf2.setOnClickListener(this);
		btnf3.setOnClickListener(this);
		btnf4.setOnClickListener(this);
		btnf5.setOnClickListener(this);
		btnf6.setOnClickListener(this);
		btnf7.setOnClickListener(this);
		btnf8.setOnClickListener(this);
		btnf9.setOnClickListener(this);
		btnf10.setOnClickListener(this);
		btnf11.setOnClickListener(this);
		btnf12.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// ʵ�ֵ����¼���
		switch (v.getId()) {
		
		case R.id.ic_scan:
			
//			 ��ɨ���ά��� ������Ϣ��
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, Configs.TO_SCANACTIVITY_RESULT_CODE);
			
			break;

		case R.id.keys_1:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("1");
			break;
		case R.id.keys_2:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("2");
			break;
		case R.id.keys_3:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("3");
			break;
		case R.id.keys_4:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("4");
			break;
		case R.id.keys_5:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("5");
			break;
		case R.id.keys_6:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("6");
			break;
		case R.id.keys_7:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("7");
			break;
		case R.id.keys_8:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("8");
			break;
		case R.id.keys_9:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("9");
			break;
		case R.id.keys_0:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("0");
			break;
		case R.id.keys_dot:
			codebar = etCodeBar.getText().toString().trim();
			if (codebar.contains(".") || codebar.equals("") || codebar == null) {
				break;
			} else {
				appendCodeBar(".");
			}
			break;
		case R.id.keys_back_space:
			codebar = etCodeBar.getText().toString().trim();
			if (codebar != null && !codebar.equals("")) {
				char[] chCode = codebar.toCharArray();
				etCodeBar.setText(codebar.substring(0, chCode.length - 1));
			}
			etext = etCodeBar.getText();
			Selection.setSelection(etext, etext.length());
			break;

		// ��ѯ �������� �Ƿ���ڣ�
		case R.id.main_scan_btn_sure:
			codeBarStr = etCodeBar.getText().toString().trim();
			if (codeBarStr != null && !codeBarStr.equals("")) {
				MyProgressDialog.showProgress(this, "���Ժ�", "���ڲ�ѯ������...");
				queryCodeBarIsExists(codeBarStr);
			} else {
				Toast.makeText(this, "�����ʽ����ȷ������������", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.main_scan_btn_fkeys:
			if (!isShow) {
				viewFkeys.setVisibility(View.VISIBLE);
				llayout.setVisibility(View.GONE);
				isShow = true;
			} else {
				viewFkeys.setVisibility(View.GONE);
				llayout.setVisibility(View.VISIBLE);
				isShow = false;
			}
			break;

		case R.id.main_keys_others_vip:// vip����
			if (list.size() != 0) {
				if(!vipInput){
					// ��ʾ �����Ա ���ţ�
					VipFragment fragment = VipFragment.getInstance();
					fragment.show(getSupportFragmentManager(), "vipF");
				}else{
					Toast.makeText(this, "��ǰ�Ѿ��ǻ�Ա����׼�ظ�����", 1).show();
				}
			} else {
				Toast.makeText(this, "��ǰ�޽�����Ʒ", 0).show();
			}
			break;
		case R.id.main_keysF5:// ����
		case R.id.main_keys_others_calcuate:
			if (list.size() != 0) {
				// �� ���� ���ݿ��в�ѯ ������Ϣ��
				SQLiteDatabase ggodb = goodsDataHelper.getReadableDatabase();

				Cursor tempCursor = ggodb.query("t_"
						+ Constants.TABLE_JSTYPE_NAME, new String[] { "*" },
						null, null, null, null, null);

				payWaylist = new ArrayList<String>();

				if (tempCursor.getCount() != 0) {
					for (tempCursor.moveToFirst(); !tempCursor.isAfterLast(); tempCursor
							.moveToNext()) {
						payWaylist.add(tempCursor.getString(tempCursor
								.getColumnIndex("detail")));
					}
					// ���� ҳ�棺
					PayDialogFragment editNameDialog = PayDialogFragment
							.getInstance(payWaylist);
					editNameDialog.show(getSupportFragmentManager(),
							"PayDialog");
				} else {
					// ��ȡ ���� ��ʽ��
					new VolleyUtils(this).getVolleyDataInfo(
							Configs.SERVER_BASE_URL
									+ Configs.GET_PAY_CALCULATE_WAY, this,
							GET_CALCULATE_WAY_AUTHORITY);
				}
			} else {
				Toast.makeText(this, "��ǰ����Ʒ�ɽ���", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.main_keysF6:// ���´�ӡ
		case R.id.main_keys_others_re_print:
			if (list.size() != 0) {
				printSellForm();
			} else {
				Toast.makeText(this, "��ǰ����Ʒ���Դ�ӡ", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.main_keysF2:// ��Ʒ����
		case R.id.main_keys_others_goods_back:
			if (clickItem) {
				if (list.size() == 0) {
					Toast.makeText(this, "��ǰ�޿ɷ�������", 0).show();
				} else {
					// �� ��Ʒ ���� ��һ
					float amount = list.get(listViewCurrCilckPosition)
							.getAmount();
					if (amount == 1) {
						list.remove(list.get(listViewCurrCilckPosition));
					} else {
						list.get(listViewCurrCilckPosition).setAmount(
								amount - 1);
					}
					adapter.notifyDataSetChanged();
				}
			} else {
				Toast.makeText(this, "��ǰδѡ���κ���", 0).show();
			}
			break;
		case R.id.main_keysF4:// ����
		case R.id.main_keys_others_amount:
			// ѡ��һ�� listitem ���� ����������
			if (clickItem) {

				UpdateAmountFragment upFragment = UpdateAmountFragment
						.getInstance();
				upFragment.show(getSupportFragmentManager(), "upFragment");

			} else {
				Toast.makeText(this, "��ǰδѡ���κ���Ʒ��", 0).show();
			}
			break;
		case R.id.main_keysF3:// ����
		case R.id.main_keys_others_constume_back:
			// ���� ���۵��� ��ѯ ������Ʒ��
			BackFragment frag = BackFragment.getInstance();
			frag.show(getSupportFragmentManager(), "backFrag");
			break;
		case R.id.main_keysF8:// �ҵ�
		case R.id.main_keys_wait_form:
			// �ҵ�
			if (list.size() != 0) {
				//����  ����
				String tableName = TimeUtils.getSystemNowTime("HH_mm_ss_1_MM_dd");

				tempDataDb = tempDatahelper.getWritableDatabase();
				// ���� ���ݿ���洢 waitForm
				tempDataDb.execSQL(String.format(
						Constants.CREATE_TABLE_TEMP_ENTITY, tableName));
				/**
				 * ���뵽���ݿ�
				 */
				for (Goods good : list) {
					tempDataDb.execSQL(String.format(
							Constants.INSERT_TABLE_TEMP, tableName,
							good.getcBarcode(), good.getAmount(),
							good.getPayMoney()));
				}
				Toast.makeText(this, "�ҵ��ɹ�", Toast.LENGTH_LONG).show();
				list.clear();
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(this, "��ǰ�޵��ɹ�", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.main_keysF9:// ȡ��
		case R.id.main_keys_go_form:
			// ȡ��
			tempDataDb = tempDatahelper.getReadableDatabase();
			// ��ȡ���б�����
			Cursor cursor = tempDataDb.query("sqlite_master",
					new String[] { "name" }, "type = 'table'", null, null,
					null, null);
			if (cursor.getCount() != 0) {
				/**
				 * ��ѯ�� ���йҵ����� ����ʾ����
				 */
				selectTempTableNamesToShow(tempDataDb, cursor);
			} else {
				Toast.makeText(this, "��ǰû�����ڵȴ��ĵ�", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.main_keysF1:// �޸�����:
			// ��ȡ��ǰ user���� �޸ĵ�ǰuser����� ���룺
			UpdateUserPwdDialog updateUserPasswordDialog = UpdateUserPwdDialog
					.getInstance(user);
			updateUserPasswordDialog.show(getSupportFragmentManager(),
					"updateUserPasswordDialog");
			break;
		case R.id.main_keysF7:// ��Ǯ��

			break;

		case R.id.main_keysF10:// �����

			break;

		// �������ݿ�
		case R.id.main_keysF11:
			// ��ʾ�Ի��� ���������ݿ���Ϣ
			dialog = new AlertDialog.Builder(this)
					.setTitle("��ȷ��")
					.setMessage("ȷ�ϸ������ݿ���Ϣ��\n�������Ҫ�����ӵ�ʱ�䡣")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									proDialog = new ProgressDialog(
											MainActivity.this);
									proDialog.setMessage("���ڼ���...");
									proDialog.show();

									// �����߳� ���� ���ݿ���Ϣ
									new Thread() {
										public void run() {
											// ���� ������ ��ȡ VIP��Ʒ�ؼ���Ϣ��
											new VolleyUtils(MainActivity.this)
													.getVolleyDataInfo(
															Configs.SERVER_BASE_URL
																	+ Configs.QUERY_VIP_GOODS_DATA,
															MainActivity.this,
															GET_VIPGOODS_DATA_AUTHORITY);

											// ���� ������ ��ȡ �����ؼ���Ʒ��Ϣ��
											new VolleyUtils(MainActivity.this)
													.getVolleyDataInfo(
															Configs.SERVER_BASE_URL
																	+ Configs.QUERY_SPECIAL_GOODS,
															MainActivity.this,
															GET_SPECIALGOODS_DATA_AUTHORITY);

											// ʹ�� xutils���� ��Ʒ��Ϣ�����⣺
											updateGoodsDataWithX(Configs.SERVER_BASE_URL+ Configs.QUERY_ALL_GOODS_INFO);
											
										};
									}.start();
								}
							}).setNegativeButton("ȡ��", null).create();

			dialog.show();// ��ʾ�Ի���

			break;
		case R.id.main_keysF12:// ����
			// ������¼ҳ��
			Intent intent2 = new Intent();
			intent2.setClass(this, LoginActivity.class);
			startActivity(intent2);
			finish();

			break;
		}
	}
	
	/**
	 * ��ӡ   ������  ��Ϣ
	 */
	private void printSellForm() {
		// ��ӡ ������Ϣ��
		m_printer.PrintStringEx("\n��Ʒ�ۻ���\n", 40, false, true,
				printer.PrintType.Centering);
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString(
				"~~~~~~~~~~~~~~~~~~~~����~~~~~~~~~~~~~~~~~~~~", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(24);
		m_printer.PrintLineString("��Ʒ��      ����      �۸�      С�� ", 24,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
//		m_printer.PrintLineInit(24);
//		m_printer.PrintStringEx("��Ʒ��      ����      �۸�      С�� ", 24,
//				false, false, PrintType.Centering);
//		m_printer.PrintLineEnd();
		
		for (Goods goods : list) {
			m_printer.PrintLineInit(20);
			m_printer.PrintLineString(goods.getcGoodsName(), 20, 30,
					true);
			m_printer.PrintLineEnd();
			m_printer.PrintLineInit(20);
			m_printer.PrintLineString(
					goods.getAmount() + goods.getcUnit() + "   "
							+ goods.getfNormalPrice() + "Ԫ/"
							+ goods.getcUnit() + "  "
							+ goods.getPayMoney() + "Ԫ", 20,
					PrintType.Right, true);
			m_printer.PrintLineEnd();
		}
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("Ӧ����"
				+ totalMoney.getText().toString() + "Ԫ", 22,
				PrintType.Right, true);
		m_printer.PrintLineEnd();
		
		list.clear();//�������
		adapter.notifyDataSetChanged();
	}

	/**
	 * ʹ�� xutils ���� ��Ʒ��������Ϣ��
	 * @param url
	 */
	public void updateGoodsDataWithX(String url) {

		HttpUtils http = new HttpUtils();

		http.send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(MainActivity.this, "���������", 0).show();
						proDialog.dismiss();
					}

					@Override
					public void onSuccess(ResponseInfo<String> response) {
						
						if (response != null) {
							
							final JSONObject obj = JSON
									.parseObject(response.result);
							final int status = obj.getIntValue("resultStatus");
							if (status == 1) {// ������
								new Thread() {
									public void run() {

										ArrayList<Goods> gList = (ArrayList<Goods>) JSON
												.parseArray(
														obj.getJSONArray("data")
																.toJSONString(),
														Goods.class);

										goodsDataDb = goodsDataHelper
												.getWritableDatabase();

										// �������������Ϣ��
										goodsDataDb.execSQL("delete from t_"
												+ Constants.TABLE_FORMNALPRICE);

										goodsDataDb.beginTransaction();
										// ���� ������ ��ӽ� ���ڣ�
										for (Goods goods : gList) {
											OperationDbTableUtils
													.insertGoodsToTable(
															goodsDataDb,
															goods,
															Constants.TABLE_FORMNALPRICE);
										}
										goodsDataDb.setTransactionSuccessful();
										goodsDataDb.endTransaction();
										goodsDataDb.close();

//										 ����Ϣ ���͸����̣߳����� ui: ��� ���ݿ������Ϣ��
										Message message = handler
												.obtainMessage();
										message.what = UPDATE_ALLGOODS_DATA_COMPLATE;
										handler.sendMessage(message);
									};
								}.start();
							} else {
								Toast.makeText(MainActivity.this, "��ǰ������������", 0)
										.show();
							}
						}
						proDialog.dismiss();
					}
				});
	}

	private ProgressDialog proDialog;

	private ArrayList<String> payWaylist; // ��¼ ���㷽ʽ�� list
	private String cVipNo = null;
	private float fCurValue;

	/**
	 * ��ѯ�� ���йҵ��ı� ����ʾ����
	 */
	private void selectTempTableNamesToShow(SQLiteDatabase tempdb, Cursor cursor) {
		/**
		 * �����еı� ���뵽 ������
		 */
		ArrayList<String> tableNames = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String tableName = cursor.getString(cursor.getColumnIndex("name"));
			if (tableName.startsWith("t_")) {
				tableNames.add(tableName);
			}
		}

		/**
		 * �ж��йҵ����ݱ�����ʾ ��ֱ����ʾ ���û�ѡ��ҵ����ݱ� ����ʾ��
		 */
		if (tableNames.size() != 0) {
			// ������� list������
			list.clear();
			adapter.notifyDataSetChanged();
			// ��ʾ ѡ������û�ѡ�� �ҵ����ݣ�
			SelectFormTempFragment selectFragment = SelectFormTempFragment
					.getInstance(tableNames);
			selectFragment.show(getSupportFragmentManager(),
					"SelectFormFragment");
		} else if (tableNames.size() == 0) {
			Toast.makeText(this, "��ǰû�����ڵȴ��ĵ�", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * �� �ùҵ������� ֱ����ʾ�� list�����У�
	 * 
	 * @param tempdb
	 * @param tablename
	 */
	private void selectTempOneTableDataToShow(SQLiteDatabase tempdb,
			String tablename) {
		Cursor oneCursor = tempdb.query(tablename, new String[] { "*" }, null,
				null, null, null, null);
		goodsDataDb = goodsDataHelper.getReadableDatabase();
		while (oneCursor.moveToNext()) {
			String cBarcode = oneCursor.getString(oneCursor
					.getColumnIndex("cBarcode"));
			float amount = oneCursor.getFloat(oneCursor
					.getColumnIndex("amount"));
			double payMoney = oneCursor.getDouble(oneCursor
					.getColumnIndex("payMoney"));
			/**
			 * ���� ���� ��ѯ��Ʒ������Ϣ��
			 */
			Cursor cursor = OperationDbTableUtils.selectDataFromLocal(cBarcode,
					goodsDataHelper);
			Goods good = OperationDbTableUtils.goodsCursorToEntity(cursor);
			good.setAmount(amount);
			good.setPayMoney(payMoney);
			list.add(good);
		}
		adapter.notifyDataSetChanged();
		// ɾ�� ��ǰ ����
		tempdb.execSQL("drop table " + tablename);
	}

	/**
	 * ��ѯ������Ϣ��
	 */
	private void queryCodeBarIsExists(String codrBar) {

		goodsDataDb = goodsDataHelper.getWritableDatabase();

		/**
		 * ��������Ʒ��Ϣ���� ��ѯ������ ��
		 */
		Cursor goodsCursor = OperationDbTableUtils.selectDataFromLocal(codrBar,
				goodsDataHelper);

		if (goodsCursor.getCount() != 0) {// �����ݣ�

			Goods goods = OperationDbTableUtils
					.goodsCursorToEntity(goodsCursor);
			// ���� list �ж� ��ǰ �������Ƿ���� ���� �����õ�ǰ������һ ����������
			if (list.contains(goods)) {
				list.get(list.indexOf(goods)).setAmount(
						list.get(list.indexOf(goods)).getAmount() + 1);
			} else {
				list.add(goods);
			}
			adapter.notifyDataSetChanged();// ���� ������

			etCodeBar.setText("");

			MyProgressDialog.stopProgress();
		} else {
			/**
			 * ����û������ �� �������� ��ѯ�����Ƿ���ڣ� ������������ݵ��������ݱ� ��������ʾ�û�����Ʒ������ ��ʾ�û���
			 */
			new GetGoodsInfoAsyncTask(this).execute(Configs.SERVER_BASE_URL
					+ String.format(Configs.QUERY_ONE_GOODS_DATA, codrBar));
		}
	}

	/**
	 * ׷��
	 * 
	 * @param code
	 */
	private void appendCodeBar(String code) {
		StringBuffer strb = new StringBuffer(codebar);
		etCodeBar.setText(new String(strb.append(code)));
		etext = etCodeBar.getText();
		Selection.setSelection(etext, etext.length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ��ȡ��ǰ �������һ�����ݣ�
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		listViewCurrCilckPosition = position;
		clickItem = true;
	}

	/**
	 * ���� fragment�Ļص���ʶ��
	 */
	@Override
	public void fragmentCallback(String result, int fragmentAuthority) {

		switch (fragmentAuthority) {

		case Configs.SELECT_TEMP_FRAGMENT_AUTHORITY:// �û� ѡ��ҵ���� ����SelectFormTempFragment
			/**
			 * ��ȡ�� �û���� �� �Ա���в�����
			 */
			SQLiteDatabase tempdb = tempDatahelper.getReadableDatabase();
			selectTempOneTableDataToShow(tempdb,
					result.substring(0, result.length()));

			break;

		case Configs.UPDATE_FRAGMENT_AMOUNT: // ���� ���� ��Ʒ���� UpdateAmountFragment
			// ��ȡ Vip��Ϣ �������ݼ��ϣ� ����Ʒ���� �޸�Ϊ ����������
			if (Float.parseFloat(result) <= 0) {
				list.remove(listViewCurrCilckPosition);
			} else {
				list.get(listViewCurrCilckPosition).setAmount(
						Float.parseFloat(result));
			}
			adapter.notifyDataSetChanged();
			break;

		case Configs.CONSUME_BACK_FRAGMENT_AUTHORITY: // ����fragment���ŵ� backFragment�Ļص�

			System.out.println(result + "====");

			break;

		case Configs.VIP_FRAGMENT_QUHORITY: // Vip ����vip���Ų�ѯ �õ� ��Ա��Ϣ��VipFragment
											// �ص�������
			// {"resultStatus":1,"data":[{"cVipno":"120001","fCurValue":120.5000}]}
			// ������Ϣ
			JSONObject object = JSON.parseObject(result);
			JSONArray array = object.getJSONArray("data");
			
			//��ȡ  ��ǰ  vip����  ��   vip��  ���֣�
			JSONObject obj = array.getJSONObject(0);
			
			cVipNo = obj.getString("cVipno");
			fCurValue = obj.getFloatValue("fCurValue");

			vipInput = true; // ��¼��ǰ ������ Ϊ VIP�û�
			adapter.notifyDataSetChanged();
			
			break;

		case Configs.GET_CALCULATE_WAY_AUTHORITY: // ��õ� ���㷽ʽ ��Ϣ ��ȡ���㷽ʽ���д���

			// ���  ���㷽ʽ ��
			if (payWaylist.get(Integer.parseInt(result)).equals("�����")) {

				float payMoney = Float.parseFloat(totalMoney.getText()
						.toString().trim());

				// ���� ֧���Ի���
				PayBalanceFragmentDialog fragment = PayBalanceFragmentDialog
						.getInstance(payMoney+"-"+"�����");
				fragment.show(getSupportFragmentManager(), "payBalance");

			} else if (payWaylist.get(Integer.parseInt(result)).equals("΢��֧��")) {
				

			} else if (payWaylist.get(Integer.parseInt(result)).equals("������")) {

				
			}
 
			break;

		case Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY:
			// ������    �� fragment�� �ص�            ����¼�������       ���list������
			String[] payStrs = result.split("-");//��ȡ֧����ʽ 
			goodsDataDb = goodsDataHelper.getWritableDatabase();
			
			for(Goods goods : list){
				OperationDbTableUtils.sellGoodsInsertTable(goodsDataDb,payStrs, 
						goods,vipInput,cVipNo,(vipScore+fCurValue)+"",user);
			}
			
			if(vipInput){//��ǰΪ    vip�û�
				//��  vip �û���Ϣ  ��������������
				new VolleyUtils(this).getVolleyDataInfo(
						String.format(Configs.SERVER_BASE_URL+Configs.UPDATE_VIP_SCORE,cVipNo,vipScore+fCurValue+""),
						this,UPDATE_VIP_SCRORE_AUTHORITY);
			}
			
			Toast.makeText(this, "����ɹ������ڴ�ӡ�ۻ���...", 1).show();
			
			printSellForm();//��ӡ�ۻ���
			
			//��¼��ǰ��������     ��Ϣ��������������һ
			
			
//			����ǰ���۹���     ��Ʒ��Ϣ���͵�    ��������
			
			
			break;

		default:
			break;
		}
	}

	@Override
	public void volleyFinishedSuccess(final JSONArray array, int authority) {

		switch (authority) {
		case GET_VIPGOODS_DATA_AUTHORITY:// ��ȡ vip�û� ���е��ؼ���Ʒ�� ��Ϣ �浽���أ�

			new Thread() {
				public void run() {
					ArrayList<VIPGoods> vipGoods = FastJsonUtils
							.getListFromArray(array, VIPGoods.class);

					SQLiteDatabase goodsDb = goodsDataHelper
							.getWritableDatabase();

					// ��յ�ǰ����������Ϣ��
					goodsDb.execSQL("delete from t_"
							+ Constants.TABLE_VIPGOODS_PRICE);

					goodsDb.beginTransaction();
					for (VIPGoods vipGood : vipGoods) {
						// ������Ϣ �� �û� ���ݱ��ڣ�
						OperationDbTableUtils.insertVipGoodsTable(goodsDb,
								vipGood);
					}

					goodsDb.setTransactionSuccessful();
					goodsDb.endTransaction();
					goodsDb.close();
				};
			}.start();
			break;

		case GET_SPECIALGOODS_DATA_AUTHORITY: // ���� �ؼ���Ʒ��Ϣ��
			new Thread() {
				public void run() {
					ArrayList<SpecialGoods> spGoods = FastJsonUtils
							.getListFromArray(array, SpecialGoods.class);

					SQLiteDatabase goDb = goodsDataHelper.getWritableDatabase();

					// ��յ�ǰ����������Ϣ��
					goDb.execSQL("delete from t_"
							+ Constants.TABLE_SPECIALPRICE);

					goDb.beginTransaction();
					for (SpecialGoods vipGood : spGoods) {
						// ������Ϣ �� �û� ���ݱ��ڣ�
						OperationDbTableUtils.insertSpecialGoodsToTable(goDb,
								vipGood, Constants.TABLE_SPECIALPRICE);
					}
					goDb.setTransactionSuccessful();
					goDb.endTransaction();
					goDb.close();
				};
			}.start();
			break;

		case GET_CALCULATE_WAY_AUTHORITY:
			// ��ȡ���㷽ʽ�ɹ�
			if (array.size() != 0) {
				payWaylist = new ArrayList<String>();
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					payWaylist.add(obj.getString("detail"));
				}
				// ���� ҳ�棺
				PayDialogFragment editNameDialog = PayDialogFragment
						.getInstance(payWaylist);
				editNameDialog.show(getSupportFragmentManager(), "PayDialog");
			} else {
				// ��ȡʧ�ܣ�
				Toast.makeText(this, "��ǰ�޿ɽ������ͣ�����������Ƿ���...", 1).show();
			}
			break;
			
		default:
			break;
		}
	}

	@Override
	public void vollayFinishedFail(int authority) {
		switch (authority) {
		case GET_CALCULATE_WAY_AUTHORITY:
			Toast.makeText(this, "��ǰ�޽��㷽ʽ�������Ƿ���������...", 0).show();
			break;
		}
	}
	
	/**
	 * �첽 ����ص� ���� ���� �ص����������ݣ�
	 */
	@Override
	public void onTaskFinished(TaskResult result) {
		
		switch (result.task_id) {
		/**
		 * ��ѯ ����������Ϣ
		 */
		case Configs.GET_GOODS_INFO_AUTHORITY: // �ж��첽�����ʶ��
			if (result.resultStatus == 1) {

				JSONArray jsonArray = (JSONArray) result.resultData;

				if (jsonArray != null) {
					/**
					 * ������ ��ȡ�� ��Ʒ���ݼ� ���浽������
					 */
					ArrayList<Goods> gList = FastJsonUtils.getListFromArray(
							jsonArray, Goods.class);
					OperationDbTableUtils.insertGoodsToTable(goodsDataDb,
							gList.get(0), Constants.TABLE_FORMNALPRICE);

					/**
					 * �ٴ� �������ݿ��в�ѯ ������Ϣ��
					 */
					Cursor cursor = OperationDbTableUtils.selectDataFromLocal(
							codeBarStr, goodsDataHelper);
					Goods goods = OperationDbTableUtils
							.goodsCursorToEntity(cursor);
					// ������������
					list.add(goods);
					adapter.notifyDataSetChanged();// ���� ������
				} else {
					// ������ δ��������
					Toast.makeText(MainActivity.this, "����������Ƿ�����",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				// ������ �����ݣ�
				Toast.makeText(MainActivity.this, "��ǰ��Ʒ�����ڣ�������Ƿ����",
						Toast.LENGTH_SHORT).show();
			}
			etCodeBar.setText("");
			MyProgressDialog.stopProgress();
			break;

		default:
			break;
		}
	}

	/**
	 * ���� ��ά�� ɨ�� �����������ݣ�
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// �ж������룺
		switch (requestCode) {
		case Configs.TO_SCANACTIVITY_RESULT_CODE:

			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String cCodeBar = bundle
						.getString(Configs.ACTIVITY_BACK_RESULT);

				// �жϱ����䣺
				if (cCodeBar != null && !cCodeBar.equals("")
						&& cCodeBar.length() > 3) {
					// ��ʾɨ�赽���������ݣ�
					etCodeBar.setText(cCodeBar);

					MyProgressDialog.showProgress(this, "���Ժ�", "���ڲ�ѯ������...");

					queryCodeBarIsExists(cCodeBar);
				} else {
					Toast.makeText(this, "�����ʽ����ȷ������������", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (goodsDataDb != null && goodsDataDb.isOpen()) {
			goodsDataDb.close();
		}
		if (tempDataDb != null && tempDataDb.isOpen()) {
			tempDataDb.close();
		}
		adapter.unregisterDataSetObserver(adapterObserver);
	}

}

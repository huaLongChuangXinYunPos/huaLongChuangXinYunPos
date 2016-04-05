package com.mall.hlcloundposproject;


import hardware.print.printer;
import hardware.print.printer.PrintType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.activity.ConsumerInfoActivity;
import com.mall.hlcloundposproject.activity.LoginActivity;
import com.mall.hlcloundposproject.activity.QueryGoodsActivity;
import com.mall.hlcloundposproject.adapter.GoodsAdapter;
import com.mall.hlcloundposproject.db.MyOpenHelper;
import com.mall.hlcloundposproject.db.OperationDbTableUtils;
import com.mall.hlcloundposproject.entity.Consumer;
import com.mall.hlcloundposproject.entity.Goods;
import com.mall.hlcloundposproject.entity.SpecialGoods;
import com.mall.hlcloundposproject.entity.User;
import com.mall.hlcloundposproject.entity.VIPGoods;
import com.mall.hlcloundposproject.fragments.BackFragment;
import com.mall.hlcloundposproject.fragments.FragmentCallback;
import com.mall.hlcloundposproject.fragments.PayBalanceFragmentDialog;
import com.mall.hlcloundposproject.fragments.PayDialogFragment;
import com.mall.hlcloundposproject.fragments.SelectFormTempFragment;
import com.mall.hlcloundposproject.fragments.UpdateAmountFragment;
import com.mall.hlcloundposproject.fragments.UpdateUserPwdDialog;
import com.mall.hlcloundposproject.fragments.VipFragment;
import com.mall.hlcloundposproject.tasks.GetGoodsInfoAsyncTask;
import com.mall.hlcloundposproject.tasks.TaskCallBack;
import com.mall.hlcloundposproject.tasks.TaskResult;
import com.mall.hlcloundposproject.utils.FastJsonUtils;
import com.mall.hlcloundposproject.utils.MD5Util;
import com.mall.hlcloundposproject.utils.MapUtils;
import com.mall.hlcloundposproject.utils.MyProgressDialog;
import com.mall.hlcloundposproject.utils.SelledToServerUtils;
import com.mall.hlcloundposproject.utils.SocketToNet;
import com.mall.hlcloundposproject.utils.TimeUtils;
import com.mall.hlcloundposproject.utils.VolleyUtils;
import com.mall.hlcloundposproject.utils.VolleyUtils.VolleyCallback;
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
import android.os.Looper;
import android.os.Message;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.barcode.Scanner;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
	private TextView agoMoney;// 商品原价
	@ViewInject(R.id.total_special_price)
	private TextView spPrice;// 特价商品
	@ViewInject(R.id.total_vip_price)
	private TextView vipPrice;// vip商品
	@ViewInject(R.id.total_money)
	private TextView totalMoney;// 当前的总价

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
	@ViewInject(R.id.main_keys_others_consumer_info)
	private Button consumerInfo;
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
	
//	@ViewInject(R.id.main_keysF10)
//	private Button btnf10;
	
	@ViewInject(R.id.main_keysF11)
	private Button btnf11;
	@ViewInject(R.id.main_keysF12)
	private Button btnf12;

	private User user = null;
	private int listViewCurrCilckPosition = 0; // 记录当前 被点击 item
	private boolean clickItem = false;// 记录当前 listView的item是否可点击：
	private AlertDialog dialog;// 警告框
	
	@ViewInject(R.id.ic_scan)
	private ImageView cordBarScan;

	private static final int GET_VIPGOODS_DATA_AUTHORITY = 1;// 获取 VIP商品标识标识

	private static final int GET_SPECIALGOODS_DATA_AUTHORITY = 2;// 获取 特价 商品标识

	private static final int UPDATE_ALLGOODS_DATA_COMPLATE = 3; // 完成 数据库 更新
																// 通知主线程 更新ui
	private static final int GET_CALCULATE_WAY_AUTHORITY = 4; // 获取 结算方式
	
	private static final int UPDATE_VIP_SCRORE_AUTHORITY = 5;//刷新    服务器端   异步任务的
	
	printer m_printer = new printer();// 创建 打印机类
	private Handler handler = new MainHandler();// 接受打印机 回传回来的数据
	private static String sellAmount;
	private static String dayDate;
	private SharedPreferences sp;

	private class MainHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

				case Scanner.BARCODE_READ: { // 处理 扫描仪   发来的消息
						etCodeBar.setText("");
						// 显示读到的条码数据：
						etCodeBar.setText((String) msg.obj);
						// 光标移动到 编辑框下一行：
						etCodeBar.setSelection(etCodeBar.getText().length());
						// 播放声音
						Scanner.play();
					}
					break;
	
				case Scanner.BARCODE_NOREAD: { // 处理  扫描仪  发来的消息
					Toast.makeText(MainActivity.this, "未扫描到条码信息,请检查扫描仪设备", 0)
							.show();
					break;
				}
	
				case UPDATE_ALLGOODS_DATA_COMPLATE: { // 处理 更新 商品信息的 ui
					proDialog.dismiss();
					Toast.makeText(MainActivity.this, "商品信息更新完成...", 0).show();
					break;
				}
				
				case SCAN_PAY_THREAD_IS_OK: { //扫码支付成功
					MyProgressDialog.stopProgress();
					for(Goods goods : list){  //销售的  数据信息   插入数据库
						OperationDbTableUtils.sellGoodsInsertTable(goodsDataDb,
								jsWay.equals("人民币")?((String)msg.obj).split("-"):null, 
								goods,vipInput,cVipNo,(vipScore+fCurValue)+"",user,
								sellSheetNo,jsWay,getFormatNumber(totalMoney.getText().toString().trim()),shPayMoney);
					}
					
					//像服务器  发送数据  并更新本地数据信息：
					SelledToServerUtils.selledToServer(sellSheetNo,goodsDataHelper,sp);
					
					Toast.makeText(MainActivity.this, "正在打印售货单,请稍等...", 1).show();
					
					new Thread(new Runnable(){
	
						@Override
						public void run() {
							printSellForm();
						}
						
					}).start();
					break;
				}
				
				case SCAN_PAY_THREAD_IS_FAIL:{  //扫码支付失败
					MyProgressDialog.stopProgress();
					Toast.makeText(MainActivity.this, "支付失败,请判断当前网络", 1).show();
				}
				
				default:
					break;
			}
		}
	}

	@ViewInject(R.id.query_goods_btn)
	private Button queryGoodsBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (User) getIntent().getSerializableExtra("user");// 获取当前用户对象
		goodsDataHelper = new MyOpenHelper(MainActivity.this,
				Content.GOODS_DB_NAME);
		tempDatahelper = new MyOpenHelper(MainActivity.this,
				Content.TEMP_DATA_DB);

		ViewUtils.inject(this);
		
		// 设置   表格标题的   背景颜色
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
		
		sp = getSharedPreferences(Configs.APP_NAME, MODE_PRIVATE);
		dayDate = TimeUtils.getSystemNowTime("yyyy-MM-dd");//获取      当天数据
		
		//清除    前一天销售数量记录数据：
		String beforeDay = getBeforeDay();
		if(sp.contains(beforeDay)){
			Editor editor = sp.edit();
			editor.remove(beforeDay);
			editor.commit();
		}

	} 
	
	public static String getBeforeDay(){
		//获取  当天销售日期：
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());//将日历设置为当前  日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);//将  这月中当前一天时间  减一   获取  前一天时间
		
		Date beforeDay = calendar.getTime();//获取前一天时间：
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//格式化时间
		String form = format.format(beforeDay);
	    
		return form;
	}
	
	private boolean vipInput = false;// 记录当前是否是VIP用户
	
	private float vipScore;//记录  会员积分信息
	
	private float priceDiscount;

	private DataSetObserver adapterObserver = new DataSetObserver() {
		/**
		 * 自动回调该方法：
		 */
		public void onChanged() {

			if (list.size() == 0) {
				vipInput = false;// 当前用户 置为普通用户
			}

			listViewCurrCilckPosition = 0;// 当前 被选中项 置为0;
			clickItem = false;// 置为 不可点击

			float agoSum = 0;// 原价
			// float accSum = 0;//现价
			float spSum = 0;// 特价
			float vipSum = 0;// vip商品
			float totalSum = 0;// 小计

			String nowTime = TimeUtils.getSystemNowTime("yyyy-MM-dd");

			goodsDataDb = goodsDataHelper.getReadableDatabase();

			for (Goods item : list) {
				// 判断     商品中    是否有特价商品：   计算     商品特价信息 如果 有特价商品     计算特价商品信息 和 vip商品价格：：
				Cursor spGoodsCursor = OperationDbTableUtils.getSpGoodsCursor(goodsDataDb,nowTime, item);
				Cursor vipGoodsCursor = OperationDbTableUtils.getVipCursor(goodsDataDb,item);
				// 查询到 特价商品信息的话：直接使用 该 特价商品的 特殊价格：
				if (spGoodsCursor.moveToFirst()) {
					// 获取      特价商品的特殊价格：
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
					spSum += (spPrice * item.getAmount());// 使用 特殊价格
					//   vip   价格使用 特价价格
					totalSum += (spPrice * item.getAmount());
				} else if (vipGoodsCursor.moveToFirst()) {
					float vipPrice = Float.parseFloat(vipGoodsCursor
							.getString(vipGoodsCursor
									.getColumnIndex("fVipPrice")));
					if (vipInput) {// 当前 为vip用户 更新 ui
						item.setfNormalPrice(vipPrice);
						item.setPayMoney(vipPrice * item.getAmount());
						totalSum += (vipPrice * item.getAmount()); // 总价
					}else{  //当前  不是vip用户
						totalSum += item.getPayMoney();
					}
					vipSum += (vipPrice * item.getAmount()); // 获取 vip 商品价格
				} else {
					totalSum += item.getPayMoney(); // 总价 使用正常商品价格：
				}
				agoSum += item.getPayMoney(); // 获取 原价
				vipScore += (item.getfVipScore()*item.getAmount());
			}
			
			agoMoney.setText(getFormatNumber(getFormatFloat(agoSum+"").toString()));
			spPrice.setText(getFormatNumber(getFormatFloat(spSum + "").toString()));
			vipPrice.setText(getFormatNumber(getFormatFloat(vipSum + "").toString()));
			totalMoney.setText(getFormatNumber(getFormatFloat(totalSum + "").toString()));
			priceDiscount = getFormatFloat(spSum + "").floatValue()
					+getFormatFloat(vipSum + "").floatValue();
			
		}
		/**
		 * 当 Adpater 调用 notifyDataSetInvalidate() 时候回调
		 */
		@Override
		public void onInvalidated() {
		}
	};
	
	public BigDecimal getFormatFloat(String floatNum){
		BigDecimal bd = new BigDecimal(floatNum);
		bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);  
		return bd;
	}

	private void initListener(MainActivity mainActivity) {
		
		queryGoodsBtn.setOnClickListener(this);
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
		consumerInfo.setOnClickListener(this);
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
//		btnf10.setOnClickListener(this);
		btnf11.setOnClickListener(this);
		btnf12.setOnClickListener(this);
	}

	private static final int QUERY_GOODS_FRAGMENT_AUTHORITY = 87;
	
	@Override
	public void onClick(View v) {
		// 实现   单击  事件：
		switch (v.getId()) {
		
		case R.id.query_goods_btn:  //模糊查询   商品信息
			Intent in = new Intent(this,QueryGoodsActivity.class);
			startActivityForResult(in, Configs.QUERY_ACTIVITY_REQUEST_CODE);
			break;
		
		case R.id.ic_scan:
			
//			 打开扫描二维码的         数据信息：
			Intent intent = new Intent();
			intent.setClass(this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, Configs.TO_SCAN_CODEBAR_RESULT_CODE);
			
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

		// 查询 单个条码 是否存在：
		case R.id.main_scan_btn_sure:
			codeBarStr = etCodeBar.getText().toString().trim();
			if (codeBarStr != null && !codeBarStr.equals("")) {
				MyProgressDialog.showProgress(this, "请稍后", "正在查询该条码...");
				queryCodeBarIsExists(codeBarStr);
			} else {
				Toast.makeText(this, "条码格式不正确，请重新输入", Toast.LENGTH_SHORT)
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

		case R.id.main_keys_others_vip:// vip结算
			if (list.size() != 0) {
				if(!vipInput){
					// 提示    输入会员     卡号：
					VipFragment fragment = VipFragment.getInstance();
					fragment.show(getSupportFragmentManager(), "vipF");
				}else{
					Toast.makeText(this, "当前已经是会员，不准重复输入", 1).show();
				}
			} else {
				Toast.makeText(this, "当前无结算商品", 0).show();
			}
			break;
		case R.id.main_keysF5:// 结算
		case R.id.main_keys_others_calcuate:
			if (list.size() != 0) {
				// 从 本地 数据库中查询 结算信息：
				SQLiteDatabase ggodb = goodsDataHelper.getReadableDatabase();

				Cursor tempCursor = ggodb.query("t_"
						+ Content.TABLE_JSTYPE_NAME, new String[] { "*" },
						null, null, null, null, null);

				payWaylist = new ArrayList<String>();

				if (tempCursor.getCount() != 0) {
					for (tempCursor.moveToFirst(); !tempCursor.isAfterLast(); tempCursor
							.moveToNext()) {
						payWaylist.add(tempCursor.getString(tempCursor
								.getColumnIndex("detail")));
					}
					// 结算 页面：
					PayDialogFragment editNameDialog = PayDialogFragment
							.getInstance(payWaylist,totalMoney.getText().toString().trim());
					editNameDialog.show(getSupportFragmentManager(),
							"PayDialog");
				} else {
					// 获取    结算    方式：
					new VolleyUtils(this).getVolleyDataInfo(
							Configs.SERVER_BASE_URL
									+ Configs.GET_PAY_CALCULATE_WAY, this,
							GET_CALCULATE_WAY_AUTHORITY);
				}
			} else {
				Toast.makeText(this, "当前无商品可结算", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.main_keysF6:// 重新打印
		case R.id.main_keys_others_re_print:
			if (list.size() != 0) {
				Toast.makeText(this, "正在打印请稍等...", 1).show();
				
				new Thread(new Runnable(){

					@Override
					public void run() {
						printSellForm();
					}
					
				}).start();
				
			} else {
				Toast.makeText(this, "当前无商品可以打印", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.main_keysF2:// 输入用户信息：
		case R.id.main_keys_others_consumer_info:
			Intent inte = new Intent(this,ConsumerInfoActivity.class);
			startActivityForResult(inte,Configs.CONSUMER_INFO_REQUEST_AUTHORITY);
			break;
		case R.id.main_keysF4:// 数量
		case R.id.main_keys_others_amount:
			// 选中一项 listitem  数据 更新数量：
			if (clickItem) {
				UpdateAmountFragment upFragment = UpdateAmountFragment
						.getInstance();
				upFragment.show(getSupportFragmentManager(), "upFragment");
			} else {
				Toast.makeText(this, "当前未选中任何商品项", 0).show();
			}
			break;
		case R.id.main_keysF3:// 客退
		case R.id.main_keys_others_constume_back:
			// 根据 销售单号 查询 购买商品：
			BackFragment frag = BackFragment.getInstance();
			frag.show(getSupportFragmentManager(), "backFrag");
			break;
		case R.id.main_keysF8:// 挂单
		case R.id.main_keys_wait_form:
			// 挂单
			if (list.size() != 0) {
				//返回  表名
				String tableName = TimeUtils.getSystemNowTime("HH_mm_ss_1_MM_dd");

				tempDataDb = tempDatahelper.getWritableDatabase();
				// 创建 数据库表：存储 waitForm
				tempDataDb.execSQL(String.format(
						Content.CREATE_TABLE_TEMP_ENTITY, tableName));
				/**
				 * 插入到数据库
				 */
				for (Goods good : list) {
					tempDataDb.execSQL(String.format(
							Content.INSERT_TABLE_TEMP, tableName,
							good.getcBarcode(), good.getAmount(),
							good.getPayMoney()));
				}
				Toast.makeText(this, "挂单成功", Toast.LENGTH_LONG).show();
				list.clear();
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(this, "当前无单可挂", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.main_keysF9:// 取单
		case R.id.main_keys_go_form:
			// 取单
			tempDataDb = tempDatahelper.getReadableDatabase();
			// 获取所有表名：
			Cursor cursor = tempDataDb.query("sqlite_master",
					new String[] { "name" }, "type = 'table'", null, null,
					null, null);
			if (cursor.getCount() != 0) {
				/**
				 * 查询出 所有挂单数据 并显示出来
				 */
				selectTempTableNamesToShow(tempDataDb, cursor);
			} else {
				Toast.makeText(this, "当前没有正在等待的单", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.main_keysF1:// 修改密码:
			// 获取当前 user对象： 修改当前user对象的 密码：
			UpdateUserPwdDialog updateUserPasswordDialog = UpdateUserPwdDialog
					.getInstance(user);
			updateUserPasswordDialog.show(getSupportFragmentManager(),
					"updateUserPasswordDialog");
			break;
		case R.id.main_keysF7:// 开钱箱
			break;
			
//		case R.id.main_keysF10:// 条码秤
//			break;

		// 更新数据库
		case R.id.main_keysF11:
			// 显示对话框 并更新数据库信息
			dialog = new AlertDialog.Builder(this)
					.setTitle("请确认")
					.setMessage("确认更新数据库信息?\n\r这可能需要几分钟的时间。")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									proDialog = new ProgressDialog(
											MainActivity.this);
									proDialog.setMessage("正在更新数据库...");
									proDialog.show();
									proDialog.setCancelable(false);

									// 创建线程    更新 数据库信息
									new Thread() {
										public void run() {
											// 访问 服务器 获取 VIP商品特价信息：
											new VolleyUtils(MainActivity.this)
													.getVolleyDataInfo(
															Configs.SERVER_BASE_URL
																	+ Configs.QUERY_VIP_GOODS_DATA,
															MainActivity.this,
															GET_VIPGOODS_DATA_AUTHORITY);

											// 访问 服务器 获取 所有特价商品信息表：
											new VolleyUtils(MainActivity.this)
													.getVolleyDataInfo(
															Configs.SERVER_BASE_URL
																	+ Configs.QUERY_SPECIAL_GOODS,
															MainActivity.this,
															GET_SPECIALGOODS_DATA_AUTHORITY);
											// 使用 xutils更新 商品信息基本库：
											// 访问 服务器 获取 所有特价商品信息表：
											updateGoodsDataWithX(Configs.SERVER_BASE_URL+ 
													String.format(Configs.QUERY_ALL_GOODS_INFO,1+"",100000+""));
										};
									}.start();
								}
							}).setNegativeButton("取消", null).create();
			dialog.show(); //显示对话框
			break;
		case R.id.main_keysF12: //换班
			// 跳到登录页：
			Intent intent2 = new Intent();
			intent2.setClass(this, LoginActivity.class);
			startActivity(intent2);
			finish();
			break;
		}
	}
	
	private static String jsWay = "RMB";//记录收款方式：
	private static String shPayMoney = "0";// 记录用户应该支付总金额。
	private static String consumePayMoney = "0";//记录    收款金额(客户支付的总金额)
	private static String overPlus = "0";//记录    找零金额
	
	/**
	 * 打印   表单数据  信息
	 */
	private void printSellForm() {
		// 打印    数据信息：
		m_printer.PrintStringEx("\n华联生活超市\n", 38, false, false,
				printer.PrintType.Centering);
		m_printer.PrintStringEx("(顾客联)\n", 30, false, false,
				printer.PrintType.Centering);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(20);m_printer.PrintLineString("", 10,PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(24);
		m_printer.PrintLineString("商品名/编码", 24, PrintType.Left, false);
		m_printer.PrintLineString("       单价   *数量",  24, PrintType.Centering, false);
		m_printer.PrintLineString("小计    ", 24, PrintType.Right, false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		int i=0;
		for (Goods goods : list) {
			i++;
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString(i+","+goods.getcGoodsName(),22,
					printer.PrintType.Left,false);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString("  "+goods.getcBarcode(),22, PrintType.Left, false);
			m_printer.PrintLineString("             "+goods.getfNormalPrice()+" *"+goods.getAmount(), 22, PrintType.Centering, false);
			m_printer.PrintLineString(goods.getPayMoney()+"  ", 22, PrintType.Right, false);
			m_printer.PrintLineEnd();
		}
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("应收："+totalMoney.getText().toString(), 22,PrintType.Left, false);
		m_printer.PrintLineString("优惠："+priceDiscount, 22,PrintType.Centering, false);
		m_printer.PrintLineString("实收："+totalMoney.getText().toString().trim() , 22,PrintType.Right, false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("商品数量:"+ i , 22,PrintType.Left,false);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("金额分类：正价￥"+totalMoney.getText().toString().trim()
				+ "    特价："+ getFormatNumber(spPrice.getText().toString().trim()),
				22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(22);
		if(sellSheetNo==null){
			updateSaleSheetNo();
		}
		m_printer.PrintLineString("No: " + sellSheetNo,22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(15);
		m_printer.PrintLineString("", 15,PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("收银员:" + user.getUser(), 22,	printer.PrintType.Left,false);
		m_printer.PrintLineString(TimeUtils.getSystemNowTime("yyyy-MM-dd   hh:mm:ss"), 
				21,printer.PrintType.Right,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("交款方式  " , 22,printer.PrintType.Left,false);
		m_printer.PrintLineString("收款    找零 ", 22,printer.PrintType.Centering,false);
		m_printer.PrintLineString("实收   ",22,printer.PrintType.Right,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString(jsWay, 22,printer.PrintType.Left,false);
		m_printer.PrintLineString(consumePayMoney + "    " + overPlus, 24,PrintType.Centering,false);
		m_printer.PrintLineString(totalMoney.getText().toString().trim(),
				24,PrintType.Right,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("凭此票30天内换取发票" , 22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("服务台总联系电话:18513667437" ,22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		
		if(isAddConsumerInfo){
			m_printer.PrintLineInit(18);
			m_printer.PrintLineString("-------------------------------------------------", 18,
					PrintType.Centering, true);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString("客户信息" , 22,PrintType.Centering,false);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(18);
			m_printer.PrintLineString("-------------------------------------------------", 18,
					PrintType.Centering, true);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString("姓名:"+consumer.getName(), 22,PrintType.Left,false);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString("电话:" + consumer.getPhone(), 22,PrintType.Left,false);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString("送达地址:", 22,PrintType.Left,false);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString(consumer.getAddress(), 22,PrintType.Left,false);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(18);
			m_printer.PrintLineString("-------------------------------------------------", 18,
					PrintType.Centering, true);
			m_printer.PrintLineEnd();
			
			isAddConsumerInfo = false;
		}
		
		m_printer.PrintLineInit(100);
		m_printer.PrintLineString("", 100,PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		list.clear();//清空数据
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 使用 xutils 更新 商品基本库信息：
	 * @param url
	 */
	public void updateGoodsDataWithX(String url) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(MainActivity.this, "请检查服务器", 0).show();
						proDialog.dismiss();
					}
					@Override
					public void onSuccess(ResponseInfo<String> response) {
						if (response != null) {
							final JSONObject obj = JSON
									.parseObject(response.result);
							final int resuStatus = obj.getIntValue("resultStatus");
							if (resuStatus == 1) {// 有数据
								new Thread() {
									public void run() {
										Looper.prepare();
										ArrayList<Goods> gList = FastJsonUtils.getGoodsList(obj.getJSONArray("data"));
										goodsDataDb = goodsDataHelper
												.getWritableDatabase();
										// 清除表内所有信息：
										goodsDataDb.execSQL("delete from t_"
												+ Content.TABLE_FORMNALPRICE);

										goodsDataDb.beginTransaction();
										// 重新 将数据 添加进 表内：
										for (Goods goods : gList) {
											OperationDbTableUtils
													.insertGoodsToTable(
															goodsDataDb,
															goods,
															Content.TABLE_FORMNALPRICE);
										}
										
										goodsDataDb.setTransactionSuccessful();
										goodsDataDb.endTransaction();
										goodsDataDb.close();
//										 将信息 发送给主线程：更新 ui: 完成 数据库更新信息：
										Message message = handler
												.obtainMessage();
										message.what = UPDATE_ALLGOODS_DATA_COMPLATE;
										handler.sendMessage(message);
									};
								}.start();
							} else {
								Toast.makeText(MainActivity.this, "当前服务器无数据", 0)
										.show();
							}
						}
					}
				});
	}
	
	private ProgressDialog proDialog;

	private ArrayList<String> payWaylist; // 记录 结算方式的 list
	private String cVipNo = null;
	private float fCurValue;
	private String sellSheetNo;

	/**
	 * 查询出 所有挂单的表 并显示出来
	 */
	private void selectTempTableNamesToShow(SQLiteDatabase tempdb, Cursor cursor) {
		/**
		 * 将所有的表 放入到 数据内
		 */
		ArrayList<String> tableNames = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String tableName = cursor.getString(cursor.getColumnIndex("name"));
			if (tableName.startsWith("t_")) {
				tableNames.add(tableName);
			}
		}

		/**
		 * 判断有挂单数据表则显示 则直接显示 让用户选择挂单数据表 并显示：
		 */
		if (tableNames.size() != 0) {
			// 首先清除 list中内容
			list.clear();
			adapter.notifyDataSetChanged();
			// 显示 选择框让用户选择 挂单数据：
			SelectFormTempFragment selectFragment = SelectFormTempFragment
					.getInstance(tableNames);
			selectFragment.show(getSupportFragmentManager(),
					"SelectFormFragment");
		} else if (tableNames.size() == 0) {
			Toast.makeText(this, "当前没有正在等待的单", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 将 该挂单表数据   直接显示到  list集合中：
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
			 * 根据 条码 查询商品数据信息：
			 */
			Cursor cursor = OperationDbTableUtils.selectDataFromLocal(cBarcode,
					goodsDataHelper);
			Goods good = OperationDbTableUtils.goodsCursorToEntity(cursor);
			good.setAmount(amount);
			good.setPayMoney(payMoney);
			list.add(good);
		}
		adapter.notifyDataSetChanged();
		// 删除 当前 单据
		tempdb.execSQL("drop table " + tablename);
	}

	/**
	 * 查询条码信息：
	 */
	private void queryCodeBarIsExists(String codrBar) {

		goodsDataDb = goodsDataHelper.getWritableDatabase();

		/**
		 * 到本地商品信息表中 查询该条码 ：
		 */
		Cursor goodsCursor = OperationDbTableUtils.selectDataFromLocal(codrBar,
				goodsDataHelper);

		if (goodsCursor.getCount() != 0) {// 有数据：

			Goods goods = OperationDbTableUtils
					.goodsCursorToEntity(goodsCursor);
			// 遍历 list 判断 当前 条形码是否存在 存在 则设置当前数量加一 更新适配器
			if (list.contains(goods)) {
				list.get(list.indexOf(goods)).setAmount(
						list.get(list.indexOf(goods)).getAmount() + 1);
			} else {
				list.add(goods);
			}
			adapter.notifyDataSetChanged();// 更新 适配器

			etCodeBar.setText("");

			MyProgressDialog.stopProgress();
		} else {
			/**
			 * 本地没有数据 到 服务器端 查询数据是否存在， 存在则加载数据到本地数据表 不存在提示用户该商品不存在 提示用户：
			 */
			new GetGoodsInfoAsyncTask(this).execute(Configs.SERVER_BASE_URL
					+ String.format(Configs.QUERY_ONE_GOODS_DATA, codrBar));
		}
	}
	
	/**
	 * 追加
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
	 * 获取当前 被点击的一项数据：
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		listViewCurrCilckPosition = position;
		clickItem = true;
	}

	/**
	 * 所有 fragment的回调标识：
	 */
	@Override
	public void fragmentCallback(String result, int fragmentAuthority) {

		switch (fragmentAuthority) {

		case Configs.SELECT_TEMP_FRAGMENT_AUTHORITY:// 用户 选择挂单表的 回退SelectFormTempFragment
			/**
			 * 获取到   用户点击表    对表进行操作：
			 */
			SQLiteDatabase tempdb = tempDatahelper.getReadableDatabase();
			selectTempOneTableDataToShow(tempdb,
					result.substring(0, result.length()));

			break;

		case Configs.UPDATE_FRAGMENT_AMOUNT: // 更新 单个 商品数量 UpdateAmountFragment
			// 获取 Vip信息 遍历数据集合： 将商品数量 修改为 输入数量：
			if (Float.parseFloat(result) <= 0) {
				list.remove(listViewCurrCilckPosition);
			} else {
				list.get(listViewCurrCilckPosition).setAmount(
						Float.parseFloat(result));
			}
			adapter.notifyDataSetChanged();
			break;

		case Configs.CONSUME_BACK_FRAGMENT_AUTHORITY: // 客退fragment单号的 backFragment的回调

			//根据客退单号      查询商品信息：
			goodsDataDb = goodsDataHelper.getReadableDatabase();
			Cursor cursor  = goodsDataDb.query("t_"+Content.TABLE_SELL_FORM,
					new String[]{"*"}, " cSaleSheetNo='"+result+"'", 
					null, null, null, null);
			
			if(cursor.moveToFirst()){
				list.clear();
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
					String cBarcode = cursor.getString(cursor.getColumnIndex("cBarCode"));
					Cursor cur = OperationDbTableUtils.selectDataFromLocal(
							cBarcode,goodsDataHelper);
					Goods goo = OperationDbTableUtils.goodsCursorToEntity(cur);
					Goods good = OperationDbTableUtils.sellGoodsToGoodsEntity(cursor,goo);
					list.add(good);
				}
				goodsDataDb = goodsDataHelper.getWritableDatabase();
				goodsDataDb.delete("t_"+Content.TABLE_SELL_FORM,
						" cSaleSheetNo = '"+ result +"'", null);
			}else{
				Toast.makeText(this, "当前单号不存在", 1).show();
			}
			adapter.notifyDataSetChanged();
			break;

		case Configs.VIP_FRAGMENT_QUHORITY: // Vip 根据vip卡号查询 得到 会员信息的VipFragment
			JSONObject object = JSON.parseObject(result);
			JSONArray array = object.getJSONArray("data");
			JSONObject obj = array.getJSONObject(0);
			cVipNo = obj.getString("cVipno");
			fCurValue = obj.getFloatValue("fCurValue");
			vipInput = true; // 记录当前 消费者 为 VIP用户
			adapter.notifyDataSetChanged();
			break;

		case Configs.GET_CALCULATE_WAY_AUTHORITY: // 获得到 结算方式 信息 获取结算方式进行处理：
			// 输出  结算方式 ：
			if (payWaylist.get(Integer.parseInt(result)).equals("人民币")) {
				float payMoney = Float.parseFloat(totalMoney.getText()
						.toString().trim());
				PayBalanceFragmentDialog fragment = PayBalanceFragmentDialog
						.getInstance(payMoney+"-"+"人民币");
				fragment.show(getSupportFragmentManager(), "payBalance");
			} else if (payWaylist.get(Integer.parseInt(result)).equals("微信")) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, Configs.SCANNIN_WE_CHAT_REQUEST_CODE);
			} else if(payWaylist.get(Integer.parseInt(result)).equals("支付宝")){
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, Configs.SCANNIN_ALI_REQUEST_CODE);
			}else{
				Toast.makeText(this, "当前版本未集成该支付方式", 1).show();
			}
			break;

		case Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY:
			// 结算结果       的 fragment的       回调            并记录销售情况       清空list中数据
			String[] payStrs = result.split("-");//获取支付方式 
			goodsDataDb = goodsDataHelper.getWritableDatabase();
			
			jsWay = "人民币";
			shPayMoney = payStrs[0];//记录  应该支付的金额
			consumePayMoney = payStrs[1]; //记录   用户支付总金额
			overPlus = payStrs[2]; //记录用户    应该找零的金额
			updateSaleSheetNo();  //更新   销售数量和     获取单号
			
			//支付,成功    向    main 发送信息    更新ui:
			Message msg = Message.obtain(); 
			msg.what = SCAN_PAY_THREAD_IS_OK;
			msg.obj = result;
			handler.sendMessage(msg);
			
			if(vipInput){// 当前为    vip用户
				//将  vip 用户信息  发送至服务器：
				new VolleyUtils(this).getVolleyDataInfo(
						String.format(Configs.SERVER_BASE_URL+Configs.UPDATE_VIP_SCORE,cVipNo,vipScore+fCurValue+""),
						this,UPDATE_VIP_SCRORE_AUTHORITY);
			}
			break;
			
		case QUERY_GOODS_FRAGMENT_AUTHORITY://查询商品信息的   fragment
			break;
		default:
			break;
		}
	}
	
	private String getFormatNumber(String text){
		String str = text;
		if(str!=null&&!str.equals("")&&str.contains(".")){
			while(str.endsWith("0")){
				str = str.substring(0,str.length()-1);
			}
		}
		if(str.endsWith(".")){
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
	
	private void updateSaleSheetNo() {
		//获取当天已经    销售数量的信息 :  
		sellAmount = sp.getString(dayDate, 0+"");  //获取当天的销售数量信息
		
		//获取      当天销售数量       将销售数量加1
		int sellAm =  Integer.parseInt(sellAmount)+1;
		Editor ed = sp.edit();  //保存    当前销售数量
		ed.putString(dayDate, sellAm+"");//保存     销售数量
		ed.commit();

		sellSheetNo = sp.getString(Configs.POS_ID, "01") + 
				TimeUtils.getSystemNowTime("yyyyMMdd") + getFormatAmount(sellAm+"");
	}
	
	/**
	 * 获取  格式化后的   数量信息：
	 * @param string
	 * @return
	 */
	private static String getFormatAmount(String string) {
		//格式化    数量信息：
		StringBuffer str = new StringBuffer();
		if(string.length()<4){
			for(int i=0; i<4-string.length(); i++){
				str.append("0");
			}
			str.append(string);
		}
		return str.toString();
	}

	@Override
	public void volleyFinishedSuccess(final JSONArray array, int authority) {

		switch (authority) {
		case GET_VIPGOODS_DATA_AUTHORITY:// 获取 vip用户 享有的特价商品的 信息 存到本地：

			new Thread() {
				public void run() {
					ArrayList<VIPGoods> vipGoods = FastJsonUtils
							.getListFromArray(array, VIPGoods.class);

					SQLiteDatabase goodsDb = goodsDataHelper
							.getWritableDatabase();

					// 清空当前表内所有信息：
					goodsDb.execSQL("delete from t_"
							+ Content.TABLE_VIPGOODS_PRICE);

					goodsDb.beginTransaction();
					for (VIPGoods vipGood : vipGoods) {
						// 插入信息 到 用户 数据表内：
						OperationDbTableUtils.insertVipGoodsTable(goodsDb,
								vipGood);
					}

					goodsDb.setTransactionSuccessful();
					goodsDb.endTransaction();
					goodsDb.close();
				};
			}.start();
			break;

		case GET_SPECIALGOODS_DATA_AUTHORITY: // 处理 特价商品信息：
			new Thread() {
				public void run() {
					ArrayList<SpecialGoods> spGoods = FastJsonUtils
							.getListFromArray(array, SpecialGoods.class);

					SQLiteDatabase goDb = goodsDataHelper.getWritableDatabase();

					// 清空当前表内所有信息：
					goDb.execSQL("delete from t_"
							+ Content.TABLE_SPECIALPRICE);

					goDb.beginTransaction();
					for (SpecialGoods vipGood : spGoods) {
						// 插入信息 到 用户 数据表内：
						OperationDbTableUtils.insertSpecialGoodsToTable(goDb,
								vipGood, Content.TABLE_SPECIALPRICE);
					}
					goDb.setTransactionSuccessful();
					goDb.endTransaction();
					goDb.close();
				};
			}.start();
			break;

		case GET_CALCULATE_WAY_AUTHORITY:
			// 获取结算方式成功
			if (array.size() != 0) {
				payWaylist = new ArrayList<String>();
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					payWaylist.add(obj.getString("detail"));
				}
				// 结算 页面：
				PayDialogFragment editNameDialog = PayDialogFragment
						.getInstance(payWaylist,totalMoney.getText().toString().trim());
				editNameDialog.show(getSupportFragmentManager(), "PayDialog");
			} else {
				// 获取失败：
				Toast.makeText(this, "当前无可结算类型，请检查服务器是否开启...", 1).show();
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
			Toast.makeText(this, "当前无结算方式，请检查是否开启服务器...", 0).show();
			break;
		}
	}

	/**
	 * 异步 任务回调 方法 接受 回调回来的数据：
	 */
	@Override
	public void onTaskFinished(TaskResult result) {
		
		switch (result.task_id) {
		/**
		 * 查询 输入条码信息
		 */
		case Configs.GET_GOODS_INFO_AUTHORITY: // 判断异步任务标识：
			if (result.resultStatus == 1) {

				JSONArray jsonArray = (JSONArray) result.resultData;

				if (jsonArray != null) {
					/**
					 * 服务器 获取到 商品数据集 保存到集合中
					 */
					ArrayList<Goods> gList = FastJsonUtils.getListFromArray(
							jsonArray, Goods.class);
					OperationDbTableUtils.insertGoodsToTable(goodsDataDb,
							gList.get(0), Content.TABLE_FORMNALPRICE);

					/**
					 * 再从 本地数据库中查询 条码信息：
					 */
					Cursor cursor = OperationDbTableUtils.selectDataFromLocal(
							codeBarStr, goodsDataHelper);
					Goods goods = OperationDbTableUtils
							.goodsCursorToEntity(cursor);
					// 更新适配器：
					list.add(goods);
					adapter.notifyDataSetChanged();// 更新 适配器
				} else {
					// 服务器 未返回数据
					Toast.makeText(MainActivity.this, "请检查服务器是否连接",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				// 区间内 无数据：
				Toast.makeText(MainActivity.this, "当前商品不存在，请决定是否出售",
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
	 * 接受 二维码 扫描 传回来的数据：
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(requestCode != RESULT_CANCELED){
				if(data!=null){
					// 判断请求码：
					switch (requestCode) {
					 
					 case Configs.TO_SCAN_CODEBAR_RESULT_CODE:{
						 Bundle bundle = data.getExtras();
						 String cCodeBar = bundle
								 .getString("result");
						 // 判断表区间：
						 if (cCodeBar != null && !cCodeBar.equals("")
								 && cCodeBar.length() > 3) {
							 // 显示扫描到的内容数据：
							 etCodeBar.setText(cCodeBar);
							 MyProgressDialog.showProgress(this, "请稍后", "正在查询该条码...");
							 queryCodeBarIsExists(cCodeBar);
						 } else {
							 Toast.makeText(this, "条码格式不正确，请重新输入", Toast.LENGTH_SHORT)
							 .show();
						 }
					 }
						break;
						
					case Configs.SCANNIN_ALI_REQUEST_CODE:{
						
						MyProgressDialog.showProgress(this, "请稍后", "正在付款...");
						
						updateSaleSheetNo();  //更新销售数量和     获取单号
						jsWay = "支付宝";
						consumePayMoney = totalMoney.getText().toString().trim();
						overPlus = "0.00";
						
						Bundle bundle = data.getExtras();
						//显示扫描到的内容
						String payId = bundle.getString("result");
						
						//拼接请求参数  ：data:
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
						String timestamp = format.format(new Date());    //时间
						
						JSONObject testStr = new JSONObject();
						
						testStr.put("service", "alipay_pay_2");
						testStr.put("terminal_no", sp.getString(Configs.POS_ID, "01"));  // 本机终端号
						testStr.put("subject", "消费");  //消费主题
						testStr.put("store_id", "001");
						testStr.put("undiscountable_amount", "");
						testStr.put("total_fee", "0.01");//totalMoney.getText().toString().trim()
						testStr.put("out_trade_no", sellSheetNo);  //商户流水号
						
						testStr.put("timestamp", timestamp);
						testStr.put("dynamic_id", payId); //扫描到的条形码
						testStr.put("oto_pid",Content.SHOP_PID);
						/**
						 * 将数据  转换成键值对的形式      
						 */
						Map<String, String> map = MapUtils.getParamsFromJson(JSON.toJSONString(testStr));
						// 然后对数据按键的字母顺序     进行排序     
						String prestr = MapUtils.createLinkString(map); 
						// 获取 排序后的字符串的摘要信息
						String sign = MD5Util.GetMD5Code(prestr+ Content.PRIVATE_KEY);
						
						testStr.put("sign", sign);  //最后将    摘要信息  跟在   参数后面
						
						toServer(testStr); //开始支付
					}
						break;
								    	
					case Configs.SCANNIN_WE_CHAT_REQUEST_CODE:{
						
						MyProgressDialog.showProgress(this, "请稍后", "正在付款...");
						
						updateSaleSheetNo();  //更新销售数量和     获取单号
						jsWay = "微信";
						consumePayMoney = totalMoney.getText().toString().trim();
						overPlus = "0.00";
						
						Bundle bundle = data.getExtras();
						//显示扫描到的内容
						String payId = bundle.getString("result");
						//拼接请求参数：data:
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
						String timestamp = format.format(new Date());    //时间
						JSONObject testStr = new JSONObject();
						testStr.put("service", "wx_pay");
						testStr.put("terminal_no", sp.getString(Configs.POS_ID, "01"));  // 本机终端号
						testStr.put("subject", "消费");  //消费主题
						testStr.put("total_fee", "0.01");//totalMoney.getText().toString().trim()
						testStr.put("out_trade_no", sellSheetNo);  //商户流水号
						
						testStr.put("timestamp", timestamp);
						testStr.put("dynamic_id", payId); //扫描到的条形码
						testStr.put("oto_pid",Content.SHOP_PID); 
						
						/**
						 * 将数据  转换成键值对的形式      
						 */
						Map<String, String> map = MapUtils.getParamsFromJson(JSON.toJSONString(testStr));
						// 然后对数据按键的字母顺序     进行排序     
						String prestr = MapUtils.createLinkString(map); 
						// 获取 排序后的字符串的摘要信息
						String sign = MD5Util.GetMD5Code(prestr+ Content.PRIVATE_KEY);
						testStr.put("sign", sign);  //最后将    摘要信息  跟在   参数后面
						
						toServer(testStr);//开始支付
					}
					break;
					case Configs.QUERY_ACTIVITY_REQUEST_CODE:{ //请求查询     商品信息数据的   activity返回数据：
						if(resultCode ==Configs.QUERY_ACTIVITY_RESULT_CODE){
							Goods goods = (Goods) data.getExtras().getSerializable("goods");
							list.add(goods);
							adapter.notifyDataSetChanged();
						}
					}
					break;
					
					case Configs.CONSUMER_INFO_REQUEST_AUTHORITY:{
						if(resultCode ==Configs.CONSUMER_INFO_RESULT_AUTHORITY){
							consumer = (Consumer) data.getExtras().getSerializable("consumer");
							//获取到      客户信息
							isAddConsumerInfo = true;
							Toast.makeText(this, "客户信息添加成功", 1).show();
							System.out.println(consumer.toString());
						}
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private static Consumer consumer;
	
	private static boolean isAddConsumerInfo = false;

	@Override
	protected void onDestroy() {
		if (goodsDataDb != null && goodsDataDb.isOpen()) {
			goodsDataDb.close();
		}
		if (tempDataDb != null && tempDataDb.isOpen()) {
			tempDataDb.close();
		}
		m_printer.Close();
		adapter.unregisterDataSetObserver(adapterObserver);
		super.onDestroy();
	}

	private static final int SCAN_PAY_THREAD_IS_OK = 2;
	
	/**
	 * 创建线程   访问网络
	 * @param testStr
	 */
	private void toServer(final JSONObject testStr) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();  //调用   循环对象   准备接受消息
				
				String resultStr = SocketToNet.socketTcpRequset(Content.SERVER_IP, Content.SERVER_PORT,
						testStr);
				
				if(resultStr!=null&&!resultStr.equals("")){
						JSONObject objj = JSON.parseObject(resultStr);
						if(objj.getString("result_code").equals("SUCCESS")){
							//支付,成功    向    main 发送信息    更新ui:
							Message msg = Message.obtain(); 
							msg.what = SCAN_PAY_THREAD_IS_OK;
							msg.obj = jsWay.equals("微信") ? "微信":"支付宝";
							handler.sendMessage(msg);
						}
				}else{
					//支付失败    向    main 发送信息    更新ui:
					Message msg = Message.obtain(); 
					msg.what = SCAN_PAY_THREAD_IS_FAIL;
					handler.sendMessage(msg);
				}
			}
		}).start();		
	}
	
	private static final int SCAN_PAY_THREAD_IS_FAIL = 19;//扫码支付失败

}

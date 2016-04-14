package com.mall.hlcloundposproject.utils;


import com.mall.hlcloundposproject.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * com.shop.hlcloundposproject.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-4-12
 */
public class NumKeysUtils extends Dialog implements
		android.view.View.OnClickListener,TextWatcher{
	private Context context;
	private TextView bt_one,bt_two,bt_three,bt_four,bt_five,bt_six,bt_seven,bt_eight,bt_nine,bt_zero,bt_dot,bt_clean,bt_delete,bt_sure;
	private int newbackgroud = 0;
	public TextChangeListener textChangelistener;
	public EditText textView;
	public EditText et_value;
	public String type;

	public static final String INT = "INT"; //值类型int
	public static final String FLOAT = "FLOAT";//值类型float
	StringBuffer num = new StringBuffer();
	
	public interface TextChangeListener {
		public void onTextChange(String value);
	}

	public NumKeysUtils(Context context) {
		super(context);
		this.context = context;
	}
	
	/**
	* @Description: TODO
	* @param @param context
	* @param @param theme
	* @param @param textView 要进行赋值的textView组件   为什么不用EditText呢？  因为那玩意还要屏蔽软键盘的弹出 关闭等等 还能粘贴进去内容
	* @param @param type 值类型（整型 浮点型等）此处只做了 整型和两位小数的浮点型  如有需要自行添加处理
	* @param @param textChangelistener    监听事件
	* @throws
	 */
	public NumKeysUtils(Context context, int theme,EditText textView,String type ,
			TextChangeListener textChangelistener) {
		super(context, theme);
		this.context = context;
		this.textView = textView;
		this.type = type;
		this.textChangelistener = textChangelistener;
	}
	public NumKeysUtils(Context context, int theme,EditText textView,String type ,
			TextChangeListener textChangelistener,int newbackgroud) {
		super(context, theme);
		this.context = context;
		this.textView = textView;
		this.type = type;
		this.textChangelistener = textChangelistener;
		this.newbackgroud = newbackgroud;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.key_num);
		WindowManager winManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = (int) context.getResources().getDimension(R.dimen.dimens_130_160);
		params.height = (int) context.getResources().getDimension(R.dimen.dimens_100_160);
		params.gravity = Gravity.LEFT|Gravity.TOP;
		//判断键盘显示位置   不让其挡住textView框
		if (winManager.getDefaultDisplay().getHeight()-getLocation(textView)[1]-textView.getHeight()<=params.height) {
			if (params.width>=winManager.getDefaultDisplay().getWidth()-getLocation(textView)[0]-textView.getWidth()) {
				params.x = getLocation(textView)[0]-params.height-textView.getWidth()-10;
				params.y = getLocation(textView)[1]+20;
			}else {
				params.x = getLocation(textView)[0]+textView.getWidth()+10;
				params.y = getLocation(textView)[1]+20;
			}
		}else {
			params.x = getLocation(textView)[0];
			params.y = getLocation(textView)[1]+20;
		}
		getWindow().setAttributes(params);
		textView.setBackground(context.getResources().getDrawable(R.drawable.shape_addsubtract_sc_bule));
//		num.append(textView.getText());
		et_value = (EditText) findViewById(R.id.et_value);
		bt_one = (TextView) findViewById(R.id.bt_one);
		bt_two = (TextView) findViewById(R.id.bt_two);
		bt_three = (TextView) findViewById(R.id.bt_three);
		bt_four = (TextView) findViewById(R.id.bt_four);
		bt_five = (TextView) findViewById(R.id.bt_five);
		bt_six = (TextView) findViewById(R.id.bt_six);
		bt_seven = (TextView) findViewById(R.id.bt_seven);
		bt_eight = (TextView) findViewById(R.id.bt_eight);
		bt_nine = (TextView) findViewById(R.id.bt_nine);
		bt_zero = (TextView) findViewById(R.id.bt_zero);
		bt_clean = (TextView) findViewById(R.id.bt_clean);
		bt_dot = (TextView) findViewById(R.id.bt_dot);
		bt_delete = (TextView) findViewById(R.id.bt_delete);
		bt_sure = (TextView) findViewById(R.id.bt_sure);
		bt_one.setOnClickListener(this);
		bt_two.setOnClickListener(this);
		bt_three.setOnClickListener(this);
		bt_four.setOnClickListener(this);
		bt_five.setOnClickListener(this);
		bt_six.setOnClickListener(this);
		bt_seven.setOnClickListener(this);
		bt_eight.setOnClickListener(this);
		bt_nine.setOnClickListener(this);
		bt_zero.setOnClickListener(this);
		bt_dot.setOnClickListener(this);
		bt_delete.setOnClickListener(this);
		bt_sure.setOnClickListener(this);
		bt_clean.setOnClickListener(this);
		et_value.addTextChangedListener(this);
		
		//获取键盘框消失事件
		this.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(newbackgroud==0)
					textView.setBackground(context.getResources().getDrawable(R.drawable.shape_addsubtract_sc));
				else
					textView.setBackground(context.getResources().getDrawable(newbackgroud));
			}
		});
		// 设置屏幕外可以关闭Dialog
		this.setCanceledOnTouchOutside(true);
		if (textView.getText()!=null||!textView.getText().equals("")) {
			num.append(textView.getText().toString());
		}
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_one:
			if (checkNum(num)) {
				num.append("1");
			}
			break;
		case R.id.bt_two:
			if (checkNum(num)) {
				num.append("2");
			}
			break;
		case R.id.bt_three:
			if (checkNum(num)) {
				num.append("3");
			}
			break;
		case R.id.bt_four:
			if (checkNum(num)) {
				num.append("4");
			}
			break;
		case R.id.bt_five:
			if (checkNum(num)) {
				num.append("5");
			}
			break;
		case R.id.bt_six:
			if (checkNum(num)) {
				num.append("6");
			}
			break;
		case R.id.bt_seven:
			if (checkNum(num)) {
				num.append("7");
			}
			break;
		case R.id.bt_eight:
			if (checkNum(num)) {
				num.append("8");
			}
			break;
		case R.id.bt_nine:
			if (checkNum(num)) {
				num.append("9");
			}
			break;
		case R.id.bt_zero:
			if (checkNum(num)) {
				if (type.equals("FLOAT")) {
					num.append("0");
				}else {
					if (num.length()>0) {
						num.append("0");
					}
				}
			}
			break;
		case R.id.bt_dot:
			if (type.equals("FLOAT")&&!num.toString().contains(".")&&num.length()>0) {
				num.append(".");
			}
			break;
		case R.id.bt_delete:
			if (num.length()>0) {
				num.delete(num.length()-1, num.length());
			}
			break;
		case R.id.bt_clean:
			if (num.length()>0) {
				num.delete(0, num.length());
			}
			break;
		case R.id.bt_sure:
			this.dismiss();
			break;
		default :
			break;
		}
		if (v.getId()!=R.id.bt_sure) {
			et_value.setText(num);
		}
		
	}
	/**
	 * 验证数字格式及范围  此方法是在生成数字之前判断将要生成的数字是否符合范围      与输入法的方式不同
	 * @param num
	 * @return
	 */
	private boolean checkNum(StringBuffer num){
		if (!num.toString().equals("")) {
			if (num.toString().contains(".")) {
				if (num.toString().contains(".")&&num.length() - 1 - num.toString().indexOf(".") > 1) { //如果小数点后已有两位小数 return false;
					return false;
				}else {
					return true;
				}
			}
		}
		return true;
	}
	// View宽，高
	public int[] getLocation(View view) {
		int[] location = new  int[2] ;
		view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
		view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
	    //base = computeWH();
	    return location;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		textChangelistener.onTextChange(s.toString());
	}
}

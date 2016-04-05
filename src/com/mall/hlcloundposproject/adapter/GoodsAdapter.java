package com.mall.hlcloundposproject.adapter;

import java.util.List;

import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.entity.Goods;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public final class GoodsAdapter extends BaseAdapter {
	
	private List<Goods> list;
	private LayoutInflater inflater;
	
	public GoodsAdapter(Context context, List<Goods> list){
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		int ret = 0;
		if(list!=null){
			ret = list.size();
		}
		return ret;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Goods goods = (Goods) this.getItem(position);
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.goods_list_item, null);
			viewHolder.goodId = (TextView) convertView.findViewById(R.id.text_id);
			viewHolder.goodName = (TextView) convertView.findViewById(R.id.text_goods_name);
			viewHolder.goodCodeBar = (TextView) convertView.findViewById(R.id.text_codeBar);
			viewHolder.goodNum = (TextView) convertView.findViewById(R.id.text_num);
			viewHolder.goodCurrPrice = (TextView) convertView.findViewById(R.id.text_curPrice);
			viewHolder.goodMoney = (TextView) convertView.findViewById(R.id.text_money);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		viewHolder.goodId.setText((position+1)+"");//设置序号：
		viewHolder.goodId.setTextSize(15);
		
		viewHolder.goodName.setText(goods.getcGoodsName());
		viewHolder.goodName.setTextSize(15);
		viewHolder.goodCodeBar.setText(goods.getcBarcode());
		viewHolder.goodCodeBar.setTextSize(15);
		
		viewHolder.goodNum.setText(getFormatNumber("" + goods.getAmount()));  //设置数量：
		viewHolder.goodNum.setTextSize(15);
		
		viewHolder.goodCurrPrice.setText(goods.getfNormalPrice()+"");
		viewHolder.goodCurrPrice.setTextSize(15);
		
		viewHolder.goodMoney.setText(getFormatNumber(goods.getPayMoney()+""));//总金额：
		viewHolder.goodMoney.setTextSize(13);

		return convertView;
	}
	
	public static class ViewHolder{
		public TextView goodId;
		public TextView goodName;
		public TextView goodCodeBar;
		public TextView goodNum;
		public TextView goodCurrPrice;
		public TextView goodMoney;
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
}

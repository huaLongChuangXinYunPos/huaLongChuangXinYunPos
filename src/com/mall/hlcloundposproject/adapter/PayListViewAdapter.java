package com.mall.hlcloundposproject.adapter;

import java.util.ArrayList;

import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.entity.PayWayMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public final class PayListViewAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<PayWayMap> payWays;
	
	public PayListViewAdapter(Context context, ArrayList<PayWayMap> payWays) {
		super();
		this.context = context;
		this.payWays = payWays;
	}

	@Override
	public int getCount() {
		int ret = 0;
		if(payWays!=null){
			ret = payWays.size();
		}
		return ret;
	}

	@Override
	public Object getItem(int position) {
		return payWays.get(position).getTextView();
	}
	

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			
		    viewHolder = new ViewHolder();
			
		    convertView = LayoutInflater.from(context).inflate(R.layout.pay_calcuate_listview_item,
					null);
		    viewHolder.imageView =  (ImageView) convertView.findViewById(R.id.pay_way_item_imageView);
		    viewHolder.textView = (TextView) convertView.findViewById(R.id.pay_way_item_textView);
		    
		    convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.imageView.setBackgroundResource(payWays.get(position).getImageView());
		viewHolder.textView.setText(payWays.get(position).getTextView());		
		
		return convertView;
	}

	class ViewHolder{
		ImageView imageView;
		TextView textView;
	}
}

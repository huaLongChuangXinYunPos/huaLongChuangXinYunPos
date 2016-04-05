package com.mall.hlcloundposproject.adapter;

import java.util.ArrayList;

import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.entity.Consumer;
import com.mall.hlcloundposproject.entity.Goods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public final class QueryFragmentListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<?> obj;
	
	//设置删除图标的单击事件：
	private OnClickListener onClickDeleteListener;
	public void setDeleteOnClickListener(OnClickListener onClickDeleteListener){
		this.onClickDeleteListener = onClickDeleteListener;
	}
	
	public QueryFragmentListAdapter(Context context, ArrayList<?> obj) {
		super();
		this.context = context;
		this.obj = obj;
	}

	@Override
	public int getCount() {
		int ret = 0;
		if(obj!=null){
			ret = obj.size();
		}
		return ret;
	}

	@Override
	public Object getItem(int position) {
		return obj.get(position);
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
			
		    convertView = LayoutInflater.from(context).inflate(R.layout.query_fragment_list_item,
					null);
		    viewHolder.textView1 =  (TextView) convertView.findViewById(R.id.query_fragment_item_goodsName);
		    viewHolder.textView2 = (TextView) convertView.findViewById(R.id.query_fragment_item_goodsPrice);
		    viewHolder.deleteTv = (TextView) convertView.findViewById(R.id.add_consumer_delete_info);
		    
		    convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(obj.get(position) instanceof Goods){
			Goods goods  = (Goods)obj.get(position);
			viewHolder.textView1.setText((position+1) +","+"品名："+goods.getcGoodsName());
			viewHolder.textView2.setText("条码："+goods.getcBarcode()+
					"  单价："+goods.getfNormalPrice()+"元"+((goods.getcUnit()==null)?"":"/")
					+goods.getcUnit());		
			 viewHolder.deleteTv.setVisibility(View.GONE);
		}else if(obj.get(position) instanceof Consumer){
			Consumer consumer  = (Consumer)obj.get(position);
			viewHolder.textView1.setText((position+1) +","+"姓名："+consumer.getName()+"("+consumer.getSex()+")  "+consumer.getPhone());
			viewHolder.textView2.setText("  住址："+consumer.getAddress());
			
			viewHolder.deleteTv.setOnClickListener(onClickDeleteListener);
			
			//设置删除事件的     回调标签
			viewHolder.deleteTv.setTag(position);
			
		}
		return convertView;
	}

	class ViewHolder{
		TextView textView1,textView2;
		
		TextView deleteTv;
	}
}

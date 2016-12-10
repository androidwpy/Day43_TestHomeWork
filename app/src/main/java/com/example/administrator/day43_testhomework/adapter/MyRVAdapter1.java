package com.example.administrator.day43_testhomework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.day43_testhomework.R;
import com.example.administrator.day43_testhomework.bean.DataEntrity1;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016-12-7.
 */
public class MyRVAdapter1 extends RecyclerView.Adapter {
    private Context context;
    private List<DataEntrity1.ListBean> datas;

    public MyRVAdapter1(Context context, List<DataEntrity1.ListBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_recycler_view1,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.highlight.setText(datas.get(position).getHighlight());

        String logo = datas.get(position).getLogo();
        Picasso .with(context)
                .load(logo)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.logo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView highlight;
        private ImageView logo;

        public MyViewHolder(View itemView) {
            super(itemView);
            highlight= ((TextView) itemView.findViewById(R.id.item_recycler_view1_highlight));
            logo= ((ImageView) itemView.findViewById(R.id.item_recycler_view1_logo));
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
}

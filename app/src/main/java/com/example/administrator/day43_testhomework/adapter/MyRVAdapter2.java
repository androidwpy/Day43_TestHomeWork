package com.example.administrator.day43_testhomework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.day43_testhomework.R;
import com.example.administrator.day43_testhomework.bean.DataEntrity2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-12-7.
 */
public class MyRVAdapter2 extends RecyclerView.Adapter {
    private Context context;
    private List<DataEntrity2.DataBean> datas;
    private static final int BODY_VIEW=0;
    private static final int FOOT_VIEW=1;

    private View footView;

    public MyRVAdapter2(Context context) {
        this.context=context;
        datas=new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1==getItemCount()){
            return FOOT_VIEW;
        }else{
            return BODY_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null;
        switch (viewType) {
            case BODY_VIEW:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_view2,parent,false);
                break;
            case FOOT_VIEW:
                itemView=footView;
                break;
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;

        switch (getItemViewType(position)) {
            case BODY_VIEW:
                viewHolder.title.setText(datas.get(position).getTitle());

                String image = datas.get(position).getImage();
                Picasso .with(context)
                        .load(image)
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(viewHolder.image);
                break;
            case FOOT_VIEW:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size()+1;
    }

    public void addFooterView(View footView) {
        this.footView=footView;
    }

    public void addAll(List<DataEntrity2.DataBean> dd){
        datas.addAll(dd);
        notifyDataSetChanged();
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            image= ((ImageView) itemView.findViewById(R.id.item_recycler_view2_image));
            title=((TextView) itemView.findViewById(R.id.item_recycler_view2_title));
        }
    }
}

package com.example.administrator.day43_testhomework.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.day43_testhomework.R;
import com.example.administrator.day43_testhomework.adapter.MyRVAdapter2;
import com.example.administrator.day43_testhomework.bean.DataEntrity2;
import com.example.administrator.day43_testhomework.uri.AppInterface;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MyRVAdapter2 adapter;
    private int pager=1;
    private View footView;

    private boolean isScrollToBottom=false;
    private TextView textView;

    public FragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_two, container, false);

        recyclerView = ((RecyclerView) view.findViewById(R.id.fragment_two_recyclerViewId));
        swipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.fragment_two_swipeRefreshLayoutId));
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                pager=1;
                loadData(pager);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //实例化适配器
        adapter = new MyRVAdapter2(getContext());

        //添加尾部局
        footView = inflater.inflate(R.layout.item_footview,null);
        initFootView();
        adapter.addFooterView(footView);

        //设置适配器
        recyclerView.setAdapter(adapter);
        //加载数据
        loadData(pager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isScrollToBottom&&newState==RecyclerView.SCROLL_STATE_IDLE){

                    pager+=1;
                    loadData(pager);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = linearLayoutManager.getItemCount();

                int lastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (totalItemCount==lastCompletelyVisibleItemPosition+1){
                    isScrollToBottom=true;
                }
            }
        });

        return view;
    }

    private void initFootView() {
        textView = ((TextView) footView.findViewById(R.id.item_footview_moreId));
    }


    private void loadData(int pager) {
        String url=String.format(AppInterface.MOVIE_LIST_URL,pager);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                DataEntrity2 dataEntrity2 = new Gson().fromJson(response, DataEntrity2.class);
                List<DataEntrity2.DataBean> data = dataEntrity2.getData();
                adapter.addAll(data);
                swipeRefreshLayout.setRefreshing(false);

                isScrollToBottom=false;
            }
        });
    }
}

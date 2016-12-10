package com.example.administrator.day43_testhomework.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.day43_testhomework.R;
import com.example.administrator.day43_testhomework.adapter.MyRVAdapter1;
import com.example.administrator.day43_testhomework.bean.DataEntrity1;
import com.example.administrator.day43_testhomework.ui.DetailActivity;
import com.example.administrator.day43_testhomework.uri.AppInterface;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {


    private RecyclerView recyclerView;
    private List<DataEntrity1.ListBean> datas;
    private MyRVAdapter1 adapter;

    public FragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);

        datas=new ArrayList<>();

        recyclerView = ((RecyclerView) view.findViewById(R.id.fragment_one_recyclerViewId));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //实例化适配器
        adapter = new MyRVAdapter1(getContext(),datas);
        //设置适配器
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyRVAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });
        //加载数据
        loadData();

        return view;
    }

    private void loadData() {
        OkHttpUtils.get().url(AppInterface.MOVIE_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                DataEntrity1 dataEntrity1 = new Gson().fromJson(response, DataEntrity1.class);
                List<DataEntrity1.ListBean> list = dataEntrity1.getList();
                datas.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

}

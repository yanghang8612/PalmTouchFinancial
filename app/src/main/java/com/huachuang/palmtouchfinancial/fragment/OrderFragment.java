package com.huachuang.palmtouchfinancial.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.OrderAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Asuka on 2017/3/2.
 */

@ContentView(R.layout.fragment_order)
public class OrderFragment extends BaseFragment{

    @ViewInject(R.id.order_list_view)
    RecyclerView orderListView;

    OrderAdapter adapter;

    @Override
    protected void initFragment() {
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 1);
        orderListView.setLayoutManager(layoutManager);
        adapter = new OrderAdapter();
        orderListView.setAdapter(adapter);
    }
}

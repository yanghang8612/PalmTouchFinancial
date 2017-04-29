package com.huachuang.palmtouchfinancial.adapter;

import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.BaseActivity;
import com.huachuang.palmtouchfinancial.backend.bean.ProfitRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/3/13.
 */

public class ProfitViewPagerAdapter extends PagerAdapter {

    private BaseActivity currentActivity;
    private String[] tabs;
    private LayoutInflater inflater;
    private List<ProfitRecord> records;

    private RecyclerView profitRecyclerList;
    private SwipeRefreshLayout profitSwipeContainer;
    private TextView profitEmptyView;
    //private ProgressBar profitProgressBar;

    public ProfitViewPagerAdapter(BaseActivity activity, String[] tabs, List<ProfitRecord> records) {
        this.currentActivity = activity;
        this.tabs = tabs;
        this.inflater = this.currentActivity.getLayoutInflater();
        this.records = records;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        List<ProfitRecord> showingRecords = new ArrayList<>();
        for (ProfitRecord record : records) {
            if (position == 0 && record.getType() < 4) {
                showingRecords.add(record);
            }
        }
        RecyclerView.Adapter adapter;
        adapter = new ProfitRecordAdapter(showingRecords);
        View view =  inflater.inflate(R.layout.item_profit_tab, container, false);
        profitSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.profit_swipe_container);
        profitRecyclerList = (RecyclerView) view.findViewById(R.id.profit_list);
        profitEmptyView = (TextView) view.findViewById(R.id.profit_empty_view);
        if (showingRecords.size() == 0) {
            profitEmptyView.setVisibility(View.VISIBLE);
        }
        else {
            profitEmptyView.setVisibility(View.GONE);
        }
        //profitProgressBar = (ProgressBar) view.findViewById(R.id.profit_progressBar);

        profitRecyclerList.setAdapter(adapter);
        profitSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new InitializeProfitLoadTask().execute();
            }
        });
        profitSwipeContainer.setDistanceToTriggerSync(80);
        profitRecyclerList.setLayoutManager(new LinearLayoutManager(currentActivity));
        profitSwipeContainer.setRefreshing(false);
        //profitProgressBar.setVisibility(View.VISIBLE);
        //new InitializeprofitLoadTask().execute();
        adapter.notifyDataSetChanged();
        if (view.getParent() == null) {
            container.addView(view);
        }
        else {
            ((ViewGroup)view.getParent()).removeView(view);
            container.addView(view);
        }
        return view;
    }

    //    异步任务开启
    private class InitializeProfitLoadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            profitSwipeContainer.setRefreshing(false);
            //profitProgressBar.setVisibility(View.GONE);
            super.onPostExecute(result);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabs == null ? "" : tabs[position];
    }

    @Override
    public int getCount() {
        return tabs == null ? 0 : tabs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

package com.huachuang.palmtouchfinancial.adapter;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.BaseActivity;

import org.xutils.http.HttpMethod;

/**
 * Created by Asuka on 2017/3/13.
 */

public class ProfitViewPagerAdapter extends PagerAdapter {

    private BaseActivity currentActivity;
    private String[] tabs;
    private LayoutInflater inflater;

    private RecyclerView profitRecyclerList;
    private SwipeRefreshLayout profitSwipeContainer;
    private ProgressBar profitProgressBar;

    public ProfitViewPagerAdapter(BaseActivity activity, String[] tabs){
        this.currentActivity = activity;
        this.tabs = tabs;
        this.inflater = this.currentActivity.getLayoutInflater();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final RecyclerView.Adapter adapter;
        adapter = new ProfitAdapter();
        View view =  inflater.inflate(R.layout.item_profit_tab, container, false);
        profitRecyclerList = (RecyclerView) view.findViewById(R.id.profit_list);
        profitSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.profit_swipe_container);
        profitProgressBar = (ProgressBar) view.findViewById(R.id.profit_progressBar);

        profitRecyclerList.setAdapter(adapter);
        profitSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                开始启动线程任务
                new InitializeprofitLoadTask().execute();
            }
        });
        profitSwipeContainer.setDistanceToTriggerSync(80);
        profitRecyclerList.setLayoutManager(new LinearLayoutManager(currentActivity));
        profitSwipeContainer.setRefreshing(true);
        profitProgressBar.setVisibility(View.VISIBLE);
        new InitializeprofitLoadTask().execute();
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
    private class InitializeprofitLoadTask extends AsyncTask<Void, Void, Void> {

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
            profitProgressBar.setVisibility(View.GONE);
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

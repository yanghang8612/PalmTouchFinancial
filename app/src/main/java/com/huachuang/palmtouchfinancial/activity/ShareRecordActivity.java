package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.RecommendRecordAdapter;
import com.huachuang.palmtouchfinancial.backend.bean.RecommendRecord;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetRecommendRecord;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

@ContentView(R.layout.activity_share_record)
public class  ShareRecordActivity extends BaseSwipeActivity {

    private static String TAG = ShareRecordActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShareRecordActivity.class);
        context.startActivity(intent);
    }

    private RecommendRecordAdapter adapter;
    private List<RecommendRecord> records = new ArrayList<>();

    @ViewInject(R.id.share_record_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.share_record_ptr_frame)
    private PtrFrameLayout ptrFrame;

    @ViewInject(R.id.share_record_list)
    private RecyclerView recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        adapter = new RecommendRecordAdapter(records);
        adapter.openLoadAnimation();
        recordList.setLayoutManager(new LinearLayoutManager(this));
        recordList.setAdapter(adapter);

        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshShareRecord();
            }
        });

        refreshShareRecord();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshShareRecord() {
        x.http().post(new GetRecommendRecord(), new NetCallbackAdapter(this, false) {
            @Override
            public void onSuccess(String result) {
                JSONObject resultJsonObject;
                try {
                    resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        String recordString = resultJsonObject.getString("Records");
                        records.clear();
                        records.addAll(JSON.parseArray(recordString, RecommendRecord.class));
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                ptrFrame.refreshComplete();
            }
        });
    }
}

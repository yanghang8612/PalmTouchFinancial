package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_share_record)
public class ShareRecordActivity extends BaseSwipeActivity {

    private static String TAG = ShareRecordActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShareRecordActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.share_record_toolbar)
    private Toolbar toolbar;

//    @ViewInject(R.id.share_record_swipe_layout)
//    private SwipeRefreshLayout swipeLayout;

    @ViewInject(R.id.share_record_scroll_view)
    private ScrollView scrollView;

    @ViewInject(R.id.share_record_list)
    private RecyclerView recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveY = (int) event.getY();
                        int delayY = moveY - startY ;
                        if (delayY > 0) {
                        }
                        Log.d("shabi", "" + delayY);
                        break ;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return false;
            }
        });
//        swipeLayout.setProgressViewOffset(true, -100, -100);
//        //swipeLayout.setRefreshing(true);
//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeLayout.setRefreshing(false);
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

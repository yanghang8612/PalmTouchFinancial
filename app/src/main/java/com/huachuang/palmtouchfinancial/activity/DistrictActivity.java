package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.DistrictAdapter;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_district)
public class DistrictActivity extends BaseSwipeActivity {

    public static final String TAG = DistrictActivity.class.getSimpleName();

    public static void actionStart(Context context, int requestCode) {
        Intent intent = new Intent(context, DistrictActivity.class);
        ((BaseActivity) context).startActivityForResult(intent, requestCode);
    }

    private DistrictAdapter adapter;

    @ViewInject(R.id.district_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.district_final_name)
    private TextView districtFinalNameView;

    @ViewInject(R.id.district_detail)
    private TextView districtDetailView;

    @ViewInject(R.id.district_list)
    private RecyclerView districtRecyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        districtRecyclerList.setLayoutManager(layoutManager);
        List<String[]> districts = CommonUtils.getDistricts(this, "0");
        adapter = new DistrictAdapter(districts, this, R.layout.item_district);
        districtRecyclerList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_district, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_district_confirm) {
            confirmButtonClicked();
        }
        else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.district_card)
    private void districtCardClicked(View view) {
        adapter.backToUpLevel();
    }

    private void confirmButtonClicked() {
        String finalName = districtFinalNameView.getText().toString();
        String detailName = districtDetailView.getText().toString();
        String district = "";
        if (!finalName.equals(getResources().getString(R.string.district_default))) {
            if (TextUtils.isEmpty(detailName)) {
                finalName = finalName.substring(0, finalName.length() - 1);
            }
            district = finalName + detailName;
        }
        Intent intent = new Intent();
        intent.putExtra("district", district);
        setResult(RESULT_OK, intent);
        finish();
    }
}

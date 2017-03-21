package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class DistrictActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DistrictActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.district_final_name)
    private TextView districtFinalNameView;

    @ViewInject(R.id.district_detail)
    private TextView districtDetailView;

    @ViewInject(R.id.district_list)
    private RecyclerView districtRecyclerList;

    private DistrictAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Event(value = R.id.district_card)
    private void districtCardClicked(View view) {
        adapter.backToUpLevel();
    }

    private void confirmButtonClicked() {
        String finalName = districtFinalNameView.getText().toString();
        String detailName = districtDetailView.getText().toString();
        String district = "";
        if (!finalName.equals(getResources().getString(R.string.district_default))) {
            if (detailName.equals("")) {
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

package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetUserWallet;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_my_points)
public class MyPointsActivity extends BaseSwipeActivity {

    private static final String TAG = MyPointsActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyPointsActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.my_points_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.points_amount)
    private TextView pointsAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initPoints();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPoints() {
        x.http().post(new GetUserWallet(), new NetCallbackAdapter(this, false) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        pointsAmount.setText(resultJsonObject.getString("Points"));
                    }
                    else {
                        showToast(resultJsonObject.getString("Info"));
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

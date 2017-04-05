package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.CreditCardAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_card_manager)
public class CardManagerActivity extends BaseSwipeActivity {

    public static final String TAG = CardManagerActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CardManagerActivity.class);
        context.startActivity(intent);
    }

    private CreditCardAdapter adapter;

    @ViewInject(R.id.card_manager_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.card_list)
    private RecyclerView cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        adapter = new CreditCardAdapter();
        adapter.openLoadAnimation();
        View footerView = this.getLayoutInflater().inflate(
                R.layout.credit_card_list_footer_view,
                (ViewGroup) cardList.getParent(),
                false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardApplyActivity.actionStart(CardManagerActivity.this);
            }
        });
        adapter.addFooterView(footerView);
        cardList.setLayoutManager(new GridLayoutManager(this, 1));
        cardList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

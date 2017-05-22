package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.BankCardAdapter;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.BankCard;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetAllBankCardsParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_card_manager)
public class CardManagerActivity extends BaseSwipeActivity {

    private static final String TAG = CardManagerActivity.class.getSimpleName();

    public static final int REQUEST_CODE_CARD_INFO = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CardManagerActivity.class);
        context.startActivity(intent);
    }

    private BankCardAdapter adapter;
    private List<BankCard> cards = new ArrayList<>();

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

        adapter = new BankCardAdapter(cards);
        adapter.openLoadAnimation();
        View footerView = this.getLayoutInflater().inflate(
                R.layout.credit_card_list_footer_view,
                (ViewGroup) cardList.getParent(),
                false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.getCurrentUser().getCertificationState()) {
                    new MaterialDialog.Builder(CardManagerActivity.this)
                            .content("请先进行实名认证")
                            .contentColorRes(R.color.black)
                            .positiveText("确认")
                            .autoDismiss(false)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                else {
                    CreditCardApplyActivity.actionStart(CardManagerActivity.this);
                }
            }
        });
        adapter.addFooterView(footerView);
        cardList.setLayoutManager(new GridLayoutManager(this, 1));
        cardList.setAdapter(adapter);

        x.http().post(new GetAllBankCardsParams(), new NetCallbackAdapter(this, false) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        String cardsString = resultJsonObject.getString("Cards");
                        cards.addAll(JSON.parseArray(cardsString, BankCard.class));
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == REQUEST_CODE_CARD_INFO) {
                Bundle bundle = data.getExtras();
                cards.add((BankCard) bundle.getSerializable("card"));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Event(R.id.card_manager_add_button)
    private void addButtonClicked(View view) {
        AddBankCardActivity.actionStart(this, REQUEST_CODE_CARD_INFO);
    }
}

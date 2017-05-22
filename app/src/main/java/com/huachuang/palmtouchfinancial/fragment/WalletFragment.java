package com.huachuang.palmtouchfinancial.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CardManagerActivity;
import com.huachuang.palmtouchfinancial.activity.MyBalanceActivity;
import com.huachuang.palmtouchfinancial.activity.MyPointsActivity;
import com.huachuang.palmtouchfinancial.activity.ProfitActivity;
import com.huachuang.palmtouchfinancial.adapter.ProfitRecordAdapter;
import com.huachuang.palmtouchfinancial.backend.bean.ProfitRecord;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetUserWallet;
import com.huachuang.palmtouchfinancial.backend.net.params.GetWalletBalanceRecords;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_wallet)
public class WalletFragment extends BaseFragment {

    private ProfitRecordAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<ProfitRecord> records = new ArrayList<>();
    private View header;
    private TextView balanceAmountView;
    private TextView pointsAmountView;

    @ViewInject(R.id.wallet_fragment_ptr_frame)
    private PtrFrameLayout ptrFrame;

    @ViewInject(R.id.wallet_profit_list)
    private RecyclerView walletProfitList;

    @ViewInject(R.id.wallet_profit_empty_view)
    private TextView emptyView;

    @Override
    protected void initFragment() {
        header = getActivity().getLayoutInflater().inflate(R.layout.header_fragment_wallet, (ViewGroup) walletProfitList.getParent(), false);
        balanceAmountView = (TextView) header.findViewById(R.id.wallet_balance_amount);
        pointsAmountView = (TextView) header.findViewById(R.id.wallet_points_amount);

        refreshWallet();
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshWallet();
            }
        });

        header.findViewById(R.id.wallet_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardManagerActivity.actionStart(WalletFragment.this.getContext());
            }
        });
        header.findViewById(R.id.wallet_recharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultDialog();
            }
        });
        header.findViewById(R.id.wallet_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBalanceActivity.actionStart(WalletFragment.this.getContext());
            }
        });
        header.findViewById(R.id.wallet_points).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPointsActivity.actionStart(WalletFragment.this.getContext());
            }
        });
        header.findViewById(R.id.wallet_profit_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfitActivity.actionStart(WalletFragment.this.getContext());
            }
        });

        adapter = new ProfitRecordAdapter(records);
        adapter.openLoadAnimation();
        adapter.addHeaderView(header);
        //adapter.setEmptyView(R.layout.empty_view, (ViewGroup) walletProfitList.getParent());
        layoutManager = new LinearLayoutManager(this.getContext());
        walletProfitList.setLayoutManager(layoutManager);
        walletProfitList.setAdapter(adapter);

    }

    private void showDefaultDialog() {
        new MaterialDialog.Builder(getContext())
                .content("敬请期待")
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

    private void refreshWallet() {
        x.http().post(new GetUserWallet(), new NetCallbackAdapter(WalletFragment.this.getContext(), false) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        balanceAmountView.setText("￥ " + resultJsonObject.getString("Balance"));
                        pointsAmountView.setText(resultJsonObject.getString("Points"));
                    }
                    else {
                        showToast(resultJsonObject.getString("Info"));
                    }
                    ptrFrame.refreshComplete();
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

        x.http().post(new GetWalletBalanceRecords(), new NetCallbackAdapter(WalletFragment.this.getContext(), false) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        records.clear();
                        records.addAll(JSON.parseArray(resultJsonObject.getString("Records"), ProfitRecord.class));
                        adapter.notifyDataSetChanged();
                        if (records.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        else {
                            emptyView.setVisibility(View.GONE);
                        }
                    }
                    else {
                        showToast(resultJsonObject.getString("Info"));
                    }
                    ptrFrame.refreshComplete();
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

package com.huachuang.palmtouchfinancial.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CardManagerActivity;
import com.huachuang.palmtouchfinancial.activity.MainMallActivity;
import com.huachuang.palmtouchfinancial.activity.MyBalanceActivity;
import com.huachuang.palmtouchfinancial.activity.MyPointsActivity;
import com.huachuang.palmtouchfinancial.adapter.WalletMallAdapter;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetRecommendCount;
import com.huachuang.palmtouchfinancial.backend.net.params.GetUserWallet;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_wallet)
public class WalletFragment extends BaseFragment {

    private WalletMallAdapter adapter;
    private View header;
    private TextView balanceAmountView;
    private TextView pointsAmountView;

    @ViewInject(R.id.wallet_fragment_ptr_frame)
    private PtrFrameLayout ptrFrame;

    @ViewInject(R.id.wallet_mall_list)
    private RecyclerView walletMallList;

    @Override
    protected void initFragment() {
        header = getActivity().getLayoutInflater().inflate(R.layout.header_fragment_wallet, (ViewGroup) walletMallList.getParent(), false);
        balanceAmountView = (TextView) header.findViewById(R.id.wallet_balance_amount);
        pointsAmountView = (TextView) header.findViewById(R.id.wallet_points_amount);

        initWallet();
        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initWallet();
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
        header.findViewById(R.id.wallet_mall_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMallActivity.actionStart(WalletFragment.this.getContext());
            }
        });

        adapter = new WalletMallAdapter(this.getContext());
        adapter.openLoadAnimation();
        adapter.addHeaderView(header);
        walletMallList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //walletMallList.addItemDecoration(new WalletMallGoodsDecoration(1, R.color.divider));
        walletMallList.setAdapter(adapter);
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

    private void initWallet() {
        x.http().post(new GetUserWallet(), new NetCallbackAdapter(WalletFragment.this.getContext(), false) {
            @Override
            public void onSuccess(String result) {
                JSONObject resultJsonObject;
                try {
                    resultJsonObject = new JSONObject(result);
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
    }
}

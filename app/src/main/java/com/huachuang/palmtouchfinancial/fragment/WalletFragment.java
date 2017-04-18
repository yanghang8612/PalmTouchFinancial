package com.huachuang.palmtouchfinancial.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CardManagerActivity;
import com.huachuang.palmtouchfinancial.activity.MainMallActivity;
import com.huachuang.palmtouchfinancial.activity.MyBalanceActivity;
import com.huachuang.palmtouchfinancial.activity.MyPointsActivity;
import com.huachuang.palmtouchfinancial.adapter.WalletMallAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_wallet)
public class WalletFragment extends BaseFragment {

    private WalletMallAdapter adapter;

    @ViewInject(R.id.wallet_mall_list)
    private RecyclerView walletMallList;

    @Override
    protected void initFragment() {
        View header = getActivity().getLayoutInflater().inflate(R.layout.header_fragment_wallet, (ViewGroup) walletMallList.getParent(), false);

        header.findViewById(R.id.wallet_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultDialog();
            }
        });
        header.findViewById(R.id.wallet_pay_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultDialog();
            }
        });
        header.findViewById(R.id.wallet_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardManagerActivity.actionStart(WalletFragment.this.getContext());
            }
        });
        header.findViewById(R.id.wallet_pay_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultDialog();
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
}

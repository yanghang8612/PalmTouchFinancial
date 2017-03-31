package com.huachuang.palmtouchfinancial.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CreditCardApplyActivity;
import com.huachuang.palmtouchfinancial.adapter.CreditCardAdapter;
import com.huachuang.palmtouchfinancial.adapter.WalletMallAdapter;
import com.huachuang.palmtouchfinancial.decoration.WalletMallGoodsDecoration;

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
//        adapter = new CreditCardAdapter();
//        adapter.openLoadAnimation();
//        View footerView = getActivity().getLayoutInflater().inflate(
//                R.layout.credit_card_list_footer_view,
//                (ViewGroup) creditCardList.getParent(),
//                false);
//        footerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreditCardApplyActivity.actionStart(getContext());
//            }
//        });
//        adapter.addFooterView(footerView);
//        creditCardList.setLayoutManager(new GridLayoutManager(getContext(), 1));
//        creditCardList.setAdapter(adapter);

        adapter = new WalletMallAdapter();
        adapter.openLoadAnimation();
        walletMallList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        walletMallList.addItemDecoration(new WalletMallGoodsDecoration(1, R.color.divider));
        walletMallList.setAdapter(adapter);
    }
}

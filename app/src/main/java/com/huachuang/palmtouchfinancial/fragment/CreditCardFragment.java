package com.huachuang.palmtouchfinancial.fragment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CreditCardApplyActivity;
import com.huachuang.palmtouchfinancial.adapter.CreditCardAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_credit_card)
public class CreditCardFragment extends BaseFragment {

    private CreditCardAdapter adapter;

    @ViewInject(R.id.credit_card_list)
    private RecyclerView creditCardList;

    @Override
    protected void initFragment() {
        adapter = new CreditCardAdapter();
        adapter.openLoadAnimation();
        View footerView = getActivity().getLayoutInflater().inflate(
                R.layout.credit_card_list_footer_view,
                (ViewGroup) creditCardList.getParent(),
                false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardApplyActivity.actionStart(getContext());
            }
        });
        adapter.addFooterView(footerView);
        creditCardList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        creditCardList.setAdapter(adapter);
    }
}

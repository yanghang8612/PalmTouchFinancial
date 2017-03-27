package com.huachuang.palmtouchfinancial.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.CreditCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/3/24.
 */

public class CreditCardAdapter extends BaseQuickAdapter<CreditCard, BaseViewHolder> {

    private static List<CreditCard> cards = new ArrayList<>();

    static {
        cards.add(new CreditCard("中国银行", "信用卡", "6222020200079068785"));
        cards.add(new CreditCard("兴业银行", "信用卡", "6222020200079065423"));
        cards.add(new CreditCard("招商银行", "贷记卡", "6222020200079061769"));
        cards.add(new CreditCard("交通银行", "信用卡", "6222020200079065784"));
        cards.add(new CreditCard("中国邮政储蓄银行", "信用卡", "6222020200079066591"));
        cards.add(new CreditCard("中国工商银行", "储蓄卡", "6222020200079062146"));
        cards.add(new CreditCard("广发银行", "信用卡", "6222020200079068612"));
    }

    public CreditCardAdapter() {
        super(R.layout.item_credit_card, cards);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CreditCard creditCard) {
        baseViewHolder.setText(R.id.credit_card_item_bank_name, creditCard.getBankName())
                .setText(R.id.credit_card_item_card_type, creditCard.getCardType())
                .setText(R.id.credit_card_item_card_number, creditCard.getCardNumber().substring(creditCard.getCardNumber().length() - 4))
                .setImageResource(R.id.credit_card_item_bank_icon, GlobalParams.bankNameIconMap.get(creditCard.getBankName()));
    }
}

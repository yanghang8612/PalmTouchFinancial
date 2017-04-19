package com.huachuang.palmtouchfinancial.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.BankCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/3/24.
 */

public class BankCardAdapter extends BaseQuickAdapter<BankCard, BaseViewHolder> {

    private List<BankCard> cards;

//    static {
//        cards.add(new CreditCard("中国银行", "信用卡", "6222020200079068785"));
//        cards.add(new CreditCard("兴业银行", "信用卡", "6222020200079065423"));
//        cards.add(new CreditCard("招商银行", "贷记卡", "6222020200079061769"));
//        cards.add(new CreditCard("交通银行", "信用卡", "6222020200079065784"));
//        cards.add(new CreditCard("中国邮政储蓄银行", "信用卡", "6222020200079066591"));
//        cards.add(new CreditCard("中国工商银行", "储蓄卡", "6222020200079062146"));
//        cards.add(new CreditCard("广发银行", "信用卡", "6222020200079068612"));
//    }

    public BankCardAdapter(List<BankCard> cards) {
        super(R.layout.item_bank_card, cards);
        this.cards = cards;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BankCard bankCard) {
        baseViewHolder.setText(R.id.bank_card_item_bank_name, bankCard.getBankName())
                .setText(R.id.bank_card_item_card_type, bankCard.getCardType())
                .setText(R.id.bank_card_item_card_number, bankCard.getCardNumber().substring(bankCard.getCardNumber().length() - 4));
        if (GlobalParams.bankNameIconMap.containsKey(bankCard.getBankName())) {
            baseViewHolder.setImageResource(R.id.bank_card_item_bank_icon, GlobalParams.bankNameIconMap.get(bankCard.getBankName()));
        }
    }
}

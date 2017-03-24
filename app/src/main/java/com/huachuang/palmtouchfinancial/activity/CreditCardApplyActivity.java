package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_credit_card_apply)
public class CreditCardApplyActivity extends BaseActivity {


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CreditCardApplyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    @Event(R.id.credit_card_apply_xingye)
    private void xingyeClicked(View view) {
        SpecificApplyActivity.actionStart(this, "https://wm.cib.com.cn/application/cardapp/Fast/TwoBar/view?id=9bedfe6b25ca47dcae22ad30931e680e&from=timeline&isappinstalled=0");
    }

    @Event(R.id.credit_card_apply_zhongxin)
    private void zhongxinClicked(View view) {
        SpecificApplyActivity.actionStart(this, "http://creditcard.ecitic.com/h5/shenqing/list.html?foot_s=0&sid=SJRSYXQ4");
    }

    @Event(R.id.credit_card_apply_pufa)
    private void pufaClicked(View view) {
        SpecificApplyActivity.actionStart(this, "https://ecentre.spdbccc.com.cn/creditcard/indexActivity.htm?data=P1127187");
    }

    @Event(R.id.credit_card_apply_shanghai)
    private void shanghaiClicked(View view) {
        SpecificApplyActivity.actionStart(this, "https://mbank.bankofshanghai.com/pweixin/static/index.html?_TransactionId=CreditCardApply&_CardType=0300001720&YLLink=620019");
    }

    @Event(R.id.credit_card_apply_jiaotong)
    private void jiaotongClicked(View view) {
        SpecificApplyActivity.actionStart(this, "https://creditcardapp.bankcomm.com/applynew/front/apply/track/record.html?trackCode=A101208503136");
    }

    @Event(R.id.credit_card_apply_zhaoshang)
    private void zhaoshangClicked(View view) {
        SpecificApplyActivity.actionStart(this, "https://ccclub.cmbchina.com/mca/MPreContract.aspx?cardSel=uc&swbrief=Y&WT.mc_id=N3700MM2061I392500ZH");
    }

    @Event(R.id.credit_card_apply_guangda)
    private void guangdaClicked(View view) {
        SpecificApplyActivity.actionStart(this, "https://xyk.cebbank.com/cebmms/apply/ps/card-index.htm?req_card_id=3341&pro_code=FHTG023556SJ20RONG");
    }

    @Event(R.id.credit_card_apply_pingan)
    private void pinganClicked(View view) {
        SpecificApplyActivity.actionStart(this, "https://c.pingan.com/apply/mobile/apply/index.html?scc=920000515&ccp=1a2a8a9a5&showt=0");
    }
}

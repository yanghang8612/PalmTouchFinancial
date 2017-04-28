package com.huachuang.palmtouchfinancial;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asuka on 2017/2/27.
 */

public class GlobalParams {

    private GlobalParams(){}

    //public static final String SERVER_URL_HEAD = "http://47.89.47.200:8080/AppServer";
    public static final String SERVER_URL_HEAD = "http://192.168.1.106:8080/AppServer";
    //public static final String SERVER_URL_HEAD = "http://10.0.2.2:8080/AppServer";
    public static final String YUNPIAN_URL_HEAD = "https://sms.yunpian.com/v2";
    public static final String WECHAT_APP_ID = "wxffb25beeebed0544";
    public static final String TENCENT_APP_ID = "1105997397";
    public static final String YUNPIAN_APP_ID = "0cbc31053ed959bba435f03633e0777e";

    public static final Map<String, Integer> bankNameIconMap = new HashMap<>();

    static {
        bankNameIconMap.put("北京银行", R.drawable.bank_beijing);bankNameIconMap.put("中国工商银行", R.drawable.bank_gongshang);
        bankNameIconMap.put("中国光大银行", R.drawable.bank_guangda);bankNameIconMap.put("广发银行", R.drawable.bank_guangfa);
        bankNameIconMap.put("华夏银行", R.drawable.bank_huaxia);bankNameIconMap.put("中国建设银行", R.drawable.bank_jianshe);
        bankNameIconMap.put("交通银行", R.drawable.bank_jiaotong);bankNameIconMap.put("中国民生银行", R.drawable.bank_minsheng);
        bankNameIconMap.put("宁波银行", R.drawable.bank_ningbo);bankNameIconMap.put("中国农业银行", R.drawable.bank_nongye);
        bankNameIconMap.put("平安银行", R.drawable.bank_pingan);bankNameIconMap.put("上海浦东发展银行", R.drawable.bank_pufa);
        bankNameIconMap.put("上海银行", R.drawable.bank_shanghai);bankNameIconMap.put("深圳发展银行", R.drawable.bank_shenzhenfazhan);
        bankNameIconMap.put("兴业银行", R.drawable.bank_xingye);bankNameIconMap.put("中国邮政储蓄银行", R.drawable.bank_youzheng);
        bankNameIconMap.put("招商银行", R.drawable.bank_zhaoshang);bankNameIconMap.put("中国银行", R.drawable.bank_zhongguo);
        bankNameIconMap.put("中信银行", R.drawable.bank_zhongxin);
    }
}

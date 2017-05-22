package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Asuka on 2017/5/19.
 */

public class CommonPayHeaderParams extends RequestParams {

    private String version = "01";
    private String auth_inst_no = GlobalParams.PAY_MID;
    private String dest_chnl = GlobalParams.PAY_KEY;
    private String dest_sub_chnl;
    protected String trans_code;
    private String mch_id = GlobalParams.PAY_MID;
    private String device_info;
    private String trans_time;
    private String nonce_str;
    private String appid;

    public CommonPayHeaderParams() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        this.trans_time = fmt.format(Calendar.getInstance().getTime());
        this.nonce_str = CommonUtils.generateRandomString(16);
    }
}

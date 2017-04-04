package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.io.File;

/**
 * Created by Asuka on 2017/4/4.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "Picture/UploadHeader")
public class UploadHeaderParams extends RequestParams {
    private String userID;
    private File header;

    public UploadHeaderParams(File header) {
        this.userID = "1";
        this.header = header;
    }
}

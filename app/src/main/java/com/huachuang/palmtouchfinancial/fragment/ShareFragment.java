package com.huachuang.palmtouchfinancial.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.GlobalVariable;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.ShareQrCodeActivity;
import com.huachuang.palmtouchfinancial.activity.ShareRecordActivity;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetRecommendCount;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_share)
public class ShareFragment extends BaseFragment {

    private static final String TAG = ShareFragment.class.getSimpleName();

    @ViewInject(R.id.share_fragment_ptr_frame)
    private PtrFrameLayout ptrFrame;

    @ViewInject(R.id.share_fragment_bottomsheet)
    private BottomSheetLayout bottomSheet;

    @ViewInject(R.id.share_base_count)
    private TextView baseCountView;

    @ViewInject(R.id.share_derive_count)
    private TextView deriveCountView;

    @ViewInject(R.id.share_third_count)
    private TextView thirdCountView;

    @ViewInject(R.id.share_weixin_view)
    private View shareWeiXin;

    @ViewInject(R.id.share_qq_view)
    private View shareQQ;

    @ViewInject(R.id.share_qr_code_view)
    private View shareQrCode;

    @Override
    protected void initFragment() {
        initCountViews();
        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initCountViews();
            }
        });

        shareWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuSheetView menuSheetView =
                        new MenuSheetView(ShareFragment.this.getContext(), MenuSheetView.MenuType.GRID, "分享到...", new MenuSheetView.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // 初始化一个WXTextObject对象
                                WXWebpageObject webpage = new WXWebpageObject();
                                webpage.webpageUrl = GlobalParams.SERVER_URL_HEAD
                                        + "/register_step_one.html?identifyCode="
                                        + (UserManager.getCurrentUser().getUserType() == 0 ?
                                        UserManager.getUserPhoneNumber() : UserManager.getCurrentUser().getInvitationCode())
                                        + "&shareType="
                                        + ((item.getItemId() == R.id.share_wechat_session) ? "1" : "2");

                                WXMediaMessage msg = new WXMediaMessage(webpage);
                                msg.title = "下载注册掌触金控APP，推荐好友就送现金大礼";
                                msg.description = "信用卡申请、办理贷款，掌触金控为您提供一站式解决方案，更有推荐现金大礼，刷卡返佣等优惠活动等你来，赶快加入吧！";
                                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                                msg.thumbData = CommonUtils.bmpToByteArray(thumb, true);

                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = buildTransaction("webpage");
                                req.message = msg;
                                req.scene = (item.getItemId() == R.id.share_wechat_session) ?
                                        SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                                GlobalVariable.api.sendReq(req);
                                if (bottomSheet.isSheetShowing()) {
                                    bottomSheet.dismissSheet();
                                }
                                return true;
                            }
                        });
                menuSheetView.inflateMenu(R.menu.fragment_share_weixin);
                bottomSheet.showWithSheetView(menuSheetView);
            }
        });

        shareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuSheetView menuSheetView =
                        new MenuSheetView(ShareFragment.this.getContext(), MenuSheetView.MenuType.GRID, "分享到...", new MenuSheetView.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (GlobalVariable.tencent.isSessionValid() && GlobalVariable.tencent.getOpenId() == null) {
                                    showToast("未安装QQ客户端");
                                }

                                if (item.getItemId() == R.id.share_qq_friend) {
                                    Bundle params = new Bundle();
                                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                                    params.putString(QQShare.SHARE_TO_QQ_TITLE, "推荐好友注册送礼");
                                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "信用卡申请、办理贷款，掌触金控为您提供一站式解决方案，更有推荐现金大礼，刷卡返佣等优惠活动等你来，赶快加入吧！");
                                    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "掌触金控");
                                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, GlobalParams.SERVER_URL_HEAD + "/imgs/icon.jpg");
                                    String url = GlobalParams.SERVER_URL_HEAD
                                            + "/register_step_one.html?identifyCode="
                                            + (UserManager.getCurrentUser().getUserType() == 0 ?
                                            UserManager.getUserPhoneNumber() : UserManager.getCurrentUser().getInvitationCode())
                                            + "&shareType="
                                            + ((item.getItemId() == R.id.share_qq_friend) ? "3" : "4");
                                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
                                    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                                    GlobalVariable.tencent.shareToQQ(ShareFragment.this.getActivity(), params, null);
                                }
                                else {
                                    Bundle params = new Bundle();
                                    params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                                    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "推荐好友注册送礼");
                                    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "信用卡申请、办理贷款，掌触金控为您提供一站式解决方案，更有推荐现金大礼，刷卡返佣等优惠活动等你来，赶快加入吧！");
                                    params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "掌触金控");
                                    String url = GlobalParams.SERVER_URL_HEAD
                                            + "/register_step_one.html?identifyCode="
                                            + (UserManager.getCurrentUser().getUserType() == 0 ?
                                            UserManager.getUserPhoneNumber() : UserManager.getCurrentUser().getInvitationCode())
                                            + "&shareType="
                                            + ((item.getItemId() == R.id.share_qq_friend) ? "3" : "4");
                                    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
                                    ArrayList<String> images = new ArrayList<>();
                                    images.add(GlobalParams.SERVER_URL_HEAD + "/imgs/icon.jpg");
                                    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
                                    GlobalVariable.tencent.shareToQzone(ShareFragment.this.getActivity(), params, null);
                                }

                                if (bottomSheet.isSheetShowing()) {
                                    bottomSheet.dismissSheet();
                                }
                                return true;
                            }
                        });
                menuSheetView.inflateMenu(R.menu.fragment_share_qq);
                bottomSheet.showWithSheetView(menuSheetView);
            }
        });
    }

    @Event(R.id.share_record_layout)
    private void shareRecordLayoutClicked(View view) {
        ShareRecordActivity.actionStart(this.getContext());
    }

    @Event(R.id.share_qr_code_view)
    private void shareQrCodeViewClicked(View view) {
        ShareQrCodeActivity.actionStart(this.getContext());
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void initCountViews() {
        x.http().post(new GetRecommendCount(), new NetCallbackAdapter(ShareFragment.this.getContext(), false) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        baseCountView.setText(resultJsonObject.getString("BaseCount") + "人");
                        deriveCountView.setText(resultJsonObject.getString("DeriveCount") + "人");
                        thirdCountView.setText(resultJsonObject.getString("ThirdCount") + "人");
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

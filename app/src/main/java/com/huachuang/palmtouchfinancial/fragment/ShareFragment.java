package com.huachuang.palmtouchfinancial.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.huachuang.palmtouchfinancial.GlobalVariable;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.ShareRecordActivity;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_share)
public class ShareFragment extends BaseFragment {

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
        shareWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuSheetView menuSheetView =
                        new MenuSheetView(ShareFragment.this.getContext(), MenuSheetView.MenuType.GRID, "分享到...", new MenuSheetView.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // 初始化一个WXTextObject对象
                                WXWebpageObject webpage = new WXWebpageObject();
                                webpage.webpageUrl = "www.baidu.com";

                                WXMediaMessage msg = new WXMediaMessage(webpage);
                                msg.title = "分享app";
                                msg.description = "快点击下载吧";
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

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}

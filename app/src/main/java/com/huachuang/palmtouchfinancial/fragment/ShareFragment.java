package com.huachuang.palmtouchfinancial.fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.huachuang.palmtouchfinancial.GlobalVariable;
import com.huachuang.palmtouchfinancial.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_share)
public class ShareFragment extends BaseFragment {

    @ViewInject(R.id.share_fragment_bottomsheet)
    private BottomSheetLayout bottomSheet;

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
                                if (item.getItemId() == 0) {
                                    // 初始化一个WXTextObject对象
                                    String text = "share our application";
                                    WXTextObject textObj = new WXTextObject();
                                    textObj.text = text;

                                    WXMediaMessage msg = new WXMediaMessage(textObj);
                                    msg.mediaObject = textObj;
                                    msg.description = text;

                                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                                    req.transaction = String.valueOf(System.currentTimeMillis());
                                    req.message = msg;

                                    GlobalVariable.api.sendReq(req);
                                }
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
}

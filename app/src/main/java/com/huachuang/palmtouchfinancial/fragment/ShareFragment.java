package com.huachuang.palmtouchfinancial.fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Asuka on 2017/3/7.
 */

@ContentView(R.layout.fragment_share)
public class ShareFragment extends BaseFragment {

    @ViewInject(R.id.share_fragment_bottomsheet)
    BottomSheetLayout bottomSheet;

    @ViewInject(R.id.share_weixin_view)
    View shareWeiXin;

    @ViewInject(R.id.share_qq_view)
    View shareQQ;

    @ViewInject(R.id.share_qr_code_view)
    View shareQrCode;

    @Override
    protected void initFragment() {
        shareWeiXin.setOnClickListener(new View.OnClickListener() {
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

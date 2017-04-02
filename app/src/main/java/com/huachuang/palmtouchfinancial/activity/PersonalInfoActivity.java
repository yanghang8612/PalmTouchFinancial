package com.huachuang.palmtouchfinancial.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.huachuang.palmtouchfinancial.R;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_personal_info)
public class PersonalInfoActivity extends BaseActivity {

    public static final String TAG = PersonalInfoActivity.class.getSimpleName();

    public static int REQUEST_CODE_IMAGE = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.personal_info_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.person_layout)
    private BottomSheetLayout personLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.person_header)
    private void personHeaderClicked(View view) {
        SImagePicker
                .from(PersonalInfoActivity.this)
                .maxCount(9)
                .rowCount(3)
                .pickMode(SImagePicker.MODE_IMAGE)
                .forResult(REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE) {
            final ArrayList<String> pathList =
                    data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            final boolean original =
                    data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
        }
    }
}

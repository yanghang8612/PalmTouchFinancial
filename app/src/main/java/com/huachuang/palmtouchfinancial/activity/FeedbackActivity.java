package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.CommitFeedback;
import com.huachuang.palmtouchfinancial.util.ActivityCollector;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_feedback)
public class FeedbackActivity extends BaseSwipeActivity {

    private static String TAG = FeedbackActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.feedback_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.feedback_message)
    private EditText messageEditText;

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

    @Event(R.id.feedback_commit_button)
    private void commitButtonClicked(View view) {
        String message = messageEditText.getText().toString();
        if (TextUtils.isEmpty(message)) {
            showToast("请输入反馈的意见或建议");
        }
        else if(message.length() > 256) {
            showToast("输入字数过多");
        }
        else {
            RequestParams params = new CommitFeedback(message);
            x.http().post(params, new NetCallbackAdapter(this, true) {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJsonObject = new JSONObject(result);
                        if (resultJsonObject.getBoolean("Status")) {
                            showToast("提交成功，感谢您的反馈");
                            finish();
                        }
                        else {
                            showToast(resultJsonObject.getString("Info"));
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.AgentManagerAdapter;
import com.huachuang.palmtouchfinancial.adapter.entity.AgentItem;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.User;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetSubUserParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ContentView(R.layout.activity_agent_manager)
public class AgentManagerActivity extends BaseActivity {

    private static final String TAG = AgentManagerActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AgentManagerActivity.class);
        context.startActivity(intent);
    }

    private List<MultiItemEntity> firstItemList = new ArrayList<>();
    private List<MultiItemEntity> secondItemList = new ArrayList<>();
    private AgentManagerAdapter adapter;

    @ViewInject(R.id.agent_manager_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.agent_list)
    private RecyclerView agentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        adapter = new AgentManagerAdapter(this, firstItemList);
        agentList.setLayoutManager(new LinearLayoutManager(this));
        agentList.setAdapter(adapter);
//        user = new User();user.setUserPhoneNumber("18511838501");user.setUserType((short) 2);
//        AgentItem level01 = new AgentItem(user, 0);
//        user = new User();user.setUserPhoneNumber("18511838502");user.setUserType((short) 3);
//        AgentItem level11 = new AgentItem(user, 1);
//        user = new User();user.setUserPhoneNumber("18511838500");user.setUserType((short) 0);
//        AgentItem level1_user = new AgentItem(user, 1);
//        level01.addSubItem(level11);level01.addSubItem(level1_user);
//        user = new User();user.setUserPhoneNumber("18511838500");user.setUserType((short) 0);user.setVip(true);
//        AgentItem level21 = new AgentItem(user, 2);
//        user = new User();user.setUserPhoneNumber("18511838500");user.setUserType((short) 0);
//        AgentItem level22 = new AgentItem(user, 2);
//        level11.addSubItem(level21);level11.addSubItem(level22);
//        user = new User();user.setUserPhoneNumber("18511838500");user.setUserType((short) 0);
//        AgentItem level0_user = new AgentItem(user, 0);
//        list.add(level01);list.add(level0_user);
//        for (int i = 0; i < 2; i++) {
//            user = new User();user.setUserPhoneNumber(String.valueOf((long) (Math.random() * 100000000000L)));user.setUserType((short) 2);
//            AgentItem level0 = new AgentItem(user, 0);list.add(level0);
//            for (int j = 0; j < 2; j++) {
//                user = new User();user.setUserPhoneNumber(String.valueOf((long) (Math.random() * 100000000000L)));user.setUserType((short) 3);
//                AgentItem level1 = new AgentItem(user, 1);level0.addSubItem(level1);
//                for (int k = 0; k < 2; k++) {
//                    user = new User();user.setUserPhoneNumber(String.valueOf((long) (Math.random() * 100000000000L)));user.setUserType((short) 0);
//                    user.setVip(Math.random() > 0.5);
//                    AgentItem level2 = new AgentItem(user, 2);level1.addSubItem(level2);
//                }
//            }
//        }


        x.http().post(new GetSubUserParams(), new NetCallbackAdapter(this, false) {
            @Override
            public void onSuccess(String result) {
                JSONObject resultJsonObject;
                try {
                    resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        List<User> users = JSON.parseArray(resultJsonObject.getString("Users"), User.class);
                        if (UserManager.getCurrentUser().getUserType() != 0 && users != null) {
                            Iterator<User> iterator = users.iterator();
                            while (iterator.hasNext()) {
                                User user = iterator.next();
                                if (user.getSuperiorUserId() == UserManager.getUserID()) {
                                    AgentItem firstAgent = new AgentItem(user, 0);
                                    firstItemList.add(firstAgent);
                                    iterator.remove();
                                }
                            }
                            if (UserManager.getCurrentUser().getUserType() < 3) {
                                for (MultiItemEntity entity : firstItemList) {
                                    AgentItem firstAgent = (AgentItem) entity;
                                    iterator = users.iterator();
                                    while (iterator.hasNext()) {
                                        User user = iterator.next();
                                        if (user.getSuperiorUserId() == firstAgent.getUser().getUserId()) {
                                            AgentItem secondAgent = new AgentItem(user, 1);
                                            firstAgent.addSubItem(secondAgent);
                                            secondItemList.add(secondAgent);
                                            iterator.remove();
                                        }
                                    }
                                }
                            }
                            if (UserManager.getCurrentUser().getUserType() < 2) {
                                for (MultiItemEntity entity : secondItemList) {
                                    AgentItem secondAgent = (AgentItem) entity;
                                    iterator = users.iterator();
                                    while (iterator.hasNext()) {
                                        User user = iterator.next();
                                        if (user.getSuperiorUserId() == secondAgent.getUser().getUserId()) {
                                            secondAgent.addSubItem(new AgentItem(user, 2));
                                            iterator.remove();
                                        }
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

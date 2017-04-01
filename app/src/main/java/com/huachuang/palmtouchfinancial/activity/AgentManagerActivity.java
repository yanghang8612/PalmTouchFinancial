package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.AgentManagerAdapter;
import com.huachuang.palmtouchfinancial.adapter.entity.AgentItem;
import com.huachuang.palmtouchfinancial.backend.bean.User;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_agent_manager)
public class AgentManagerActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AgentManagerActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.agent_list)
    private RecyclerView agentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        agentList.setLayoutManager(new LinearLayoutManager(this));
        List<MultiItemEntity> list = new ArrayList<>();
        User user;
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
        for (int i = 0; i < 2; i++) {
            user = new User();user.setUserPhoneNumber(String.valueOf((long) (Math.random() * 100000000000L)));user.setUserType((short) 2);
            AgentItem level0 = new AgentItem(user, 0);list.add(level0);
            for (int j = 0; j < 2; j++) {
                user = new User();user.setUserPhoneNumber(String.valueOf((long) (Math.random() * 100000000000L)));user.setUserType((short) 3);
                AgentItem level1 = new AgentItem(user, 1);level0.addSubItem(level1);
                for (int k = 0; k < 2; k++) {
                    user = new User();user.setUserPhoneNumber(String.valueOf((long) (Math.random() * 100000000000L)));user.setUserType((short) 0);
                    user.setVip(Math.random() > 0.5);
                    AgentItem level2 = new AgentItem(user, 2);level1.addSubItem(level2);
                }
            }
        }
        AgentManagerAdapter adapter = new AgentManagerAdapter(list);
        agentList.setAdapter(adapter);
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

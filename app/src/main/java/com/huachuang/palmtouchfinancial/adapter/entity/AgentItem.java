package com.huachuang.palmtouchfinancial.adapter.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huachuang.palmtouchfinancial.adapter.AgentManagerAdapter;
import com.huachuang.palmtouchfinancial.backend.bean.User;

/**
 * Created by Asuka on 2017/3/27.
 */

public class AgentItem extends AbstractExpandableItem<AgentItem> implements MultiItemEntity {

    private User user;
    private int level;

    public AgentItem(User user, int level) {
        this.user = user;
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getItemType() {
        return (user.getUserType() == 0) ? AgentManagerAdapter.TYPE_PERSON : AgentManagerAdapter.TYPE_AGENT;
    }

    public User getUser() {
        return user;
    }
}

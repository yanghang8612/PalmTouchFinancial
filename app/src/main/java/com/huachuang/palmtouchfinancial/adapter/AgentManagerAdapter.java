package com.huachuang.palmtouchfinancial.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.entity.AgentItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Asuka on 2017/3/27.
 */

public class AgentManagerAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_PERSON = 0;
    public static final int TYPE_AGENT = 1;

    private int[] preExpandedPos = new int[4];

    public AgentManagerAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_AGENT, R.layout.item_agent);
        addItemType(TYPE_PERSON, R.layout.item_user);
        Arrays.fill(preExpandedPos, -1);
    }

    @Override
    protected void convert(final BaseViewHolder holder, MultiItemEntity item) {
        final AgentItem agent = (AgentItem) item;
        if (agent.getLevel() == 1) {
            holder.getView(R.id.first_divider).setVisibility(View.VISIBLE);
            holder.getView(R.id.second_divider).setVisibility(View.GONE);
        }
        else if (agent.getLevel() == 2) {
            holder.getView(R.id.first_divider).setVisibility(View.VISIBLE);
            holder.getView(R.id.second_divider).setVisibility(View.VISIBLE);
        }
        else {
            holder.getView(R.id.first_divider).setVisibility(View.GONE);
            holder.getView(R.id.second_divider).setVisibility(View.GONE);
        }
        switch (holder.getItemViewType()) {
            case TYPE_AGENT:
                holder.setText(R.id.agent_item_phone_number, agent.getUser().getUserPhoneNumber())
                        .setImageResource(R.id.agent_item_vip, (agent.getUser().getUserType() == 2) ? R.drawable.ic_level_two : R.drawable.ic_level_three);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int curPos = holder.getAdapterPosition();
                        int prePos = preExpandedPos[agent.getUser().getUserType()];
                        if (agent.isExpanded()) {
                            collapse(curPos);
                            preExpandedPos[agent.getUser().getUserType()] = -1;
                        }
                        else {
                            if (prePos >= 0) {
                                collapse(prePos);
                            }
                            expand(curPos);
                            preExpandedPos[agent.getUser().getUserType()] = curPos;
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                AgentItem user = (AgentItem) item;
                if (user.getUser().isVip()) {
                    holder.getView(R.id.user_item_vip).setVisibility(View.GONE);
                    holder.getView(R.id.user_item_vip_activated).setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}

package com.huachuang.palmtouchfinancial.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.entity.AgentItem;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Asuka on 2017/3/27.
 */

public class AgentManagerAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_PERSON = 0;
    public static final int TYPE_AGENT = 1;

    private Context context;
    private int[] preExpandedPos = new int[4];

    public AgentManagerAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.context = context;
        addItemType(TYPE_AGENT, R.layout.item_agent);
        addItemType(TYPE_PERSON, R.layout.item_user);
        Arrays.fill(preExpandedPos, -1);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        final AgentItem agentItem = (AgentItem) item;
        if (agentItem.getLevel() == 1) {
            holder.getView(R.id.first_divider).setVisibility(View.GONE);
            holder.getView(R.id.second_divider).setVisibility(View.VISIBLE);
        }
        else if (agentItem.getLevel() == 2) {
            holder.getView(R.id.first_divider).setVisibility(View.VISIBLE);
            holder.getView(R.id.second_divider).setVisibility(View.VISIBLE);
        }
        else {
            holder.getView(R.id.first_divider).setVisibility(View.GONE);
            holder.getView(R.id.second_divider).setVisibility(View.GONE);
        }
        switch (holder.getItemViewType()) {
            case TYPE_AGENT:
                holder.setText(R.id.agent_item_phone_number, agentItem.getUser().getUserPhoneNumber())
                        .setImageResource(R.id.agent_item_level, (agentItem.getUser().getUserType() == 2) ? R.drawable.ic_level_two : R.drawable.ic_level_three);
                if (agentItem.getUser().getHeaderState()) {
                    Glide.with(context)
                            .load(CommonUtils.getHeaderUrl(agentItem.getUser().getUserId()))
                            .into((ImageView) holder.getView(R.id.agent_item_image));
                }
                else {
                    holder.setImageResource(R.id.agent_item_image, R.drawable.default_header_image);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (agentItem.isExpanded()) {
                            collapse(holder.getAdapterPosition());
//                            if (agentItem.getUser().getUserType() == 3) {
//                                collapse(holder.getAdapterPosition());
//                            }
//                            else {
//                                int parentPos = holder.getAdapterPosition();
//                                for (AgentItem brother : agentItem.getSubItems()) {
//                                    int fuck = agentItem.getSubItemPosition(brother);
//                                    int a = fuck;
//                                    if (brother.isExpanded()) {
//                                    }
//                                }
//                            }
                        }
                        else {
                            expand(holder.getAdapterPosition());
                        }
                        notifyDataSetChanged();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                AgentManagerAdapter.this.notifyDataSetChanged();
//                            }
//                        }, 500);
                    }
                });
                break;
            case TYPE_PERSON:
                AgentItem userItem = (AgentItem) item;
                if (userItem.getUser().isVip()) {
                    holder.getView(R.id.user_item_vip).setVisibility(View.GONE);
                    holder.getView(R.id.user_item_vip_activated).setVisibility(View.VISIBLE);
                }
                else {
                    holder.getView(R.id.user_item_vip).setVisibility(View.VISIBLE);
                    holder.getView(R.id.user_item_vip_activated).setVisibility(View.GONE);
                }
                holder.setText(R.id.user_item_phone_number, userItem.getUser().getUserPhoneNumber());
                if (userItem.getUser().getHeaderState()) {
                    Glide.with(context)
                            .load(CommonUtils.getHeaderUrl(userItem.getUser().getUserId()))
                            .into((ImageView) holder.getView(R.id.user_item_image));
                }
                else {
                    holder.setImageResource(R.id.user_item_image, R.drawable.default_header_image);
                }
                break;
        }
    }
}

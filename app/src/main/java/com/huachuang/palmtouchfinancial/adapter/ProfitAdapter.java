package com.huachuang.palmtouchfinancial.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;

/**
 * Created by Asuka on 2017/3/13.
 */

public class ProfitAdapter extends RecyclerView.Adapter<ProfitAdapter.ViewHolder>{

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView profitName;
        TextView profitWriteTime;
        TextView profitDetail;
        ImageView profitHeadPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            profitName = (TextView) itemView.findViewById(R.id.profit_name);
            profitWriteTime = (TextView) itemView.findViewById(R.id.profit_generate_time);
            profitDetail = (TextView) itemView.findViewById(R.id.profit_detail);
            profitHeadPicture = (ImageView) itemView.findViewById(R.id.profit_head_picture);
        }
    }
}

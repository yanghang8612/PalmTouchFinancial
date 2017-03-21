package com.huachuang.palmtouchfinancial.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import java.util.List;
import java.util.Stack;

/**
 * Created by Asuka on 2017/3/21.
 */

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.ViewHolder> {

    private List<String[]> districts;
    private Activity currentActivity;
    private int currentLayout;
    private Stack<String> preDistrictIds = new Stack<>();
    private Stack<String> preDistrictNames = new Stack<>();
    private String currentDistrictId = "0";
    private TextView currentPlaceView;

    public DistrictAdapter(List<String[]> districts, Activity currentActivity, int currentLayout) {
        this.districts = districts;
        this.currentActivity = currentActivity;
        this.currentLayout = currentLayout;
        this.currentPlaceView = (TextView) currentActivity.findViewById(R.id.district_final_name);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(currentLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] district = districts.get(position);
        String districtNameTemp = "";
        if (CommonUtils.validateNumber(district[3])) {
            districtNameTemp = district[1];
        }
        else {
            districtNameTemp = district[1] + district[3];
        }
        final String districtName = districtNameTemp;
        final String districtId = district[0];
        holder.district.setText(districtName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPlaceName = currentPlaceView.getText().toString();
                preDistrictNames.push(currentPlaceName);
                if (currentPlaceName.equals(currentActivity.getResources().getString(R.string.district_default))) {
                    currentPlaceName = "";
                }
                currentPlaceView.setText(currentPlaceName + districtName + "/");
                preDistrictIds.push(currentDistrictId);
                currentDistrictId = districtId;
                districts = CommonUtils.getDistricts(currentActivity, currentDistrictId);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return districts == null ? 0 : districts.size();
    }

    public void backToUpLevel() {
        if (preDistrictIds.size() == 0) {
            return;
        }
        currentPlaceView.setText(preDistrictNames.pop());
        currentDistrictId = preDistrictIds.pop();
        districts = CommonUtils.getDistricts(currentActivity, currentDistrictId);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView district;
        ViewHolder(View itemView) {
            super(itemView);
            district = (TextView) itemView.findViewById(R.id.district_name);
        }
    }
}

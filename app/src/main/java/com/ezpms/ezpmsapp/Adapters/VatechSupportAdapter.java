package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Activities.ViewSupportDetailsActivity;
import com.ezpms.ezpmsapp.Models.SupportData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ViewHolders.VatechSupportViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VatechSupportAdapter extends RecyclerView.Adapter<VatechSupportViewHolder> {

    private Context mContext;
    private List<SupportData> supportData;
    private SimpleDateFormat formatter;
    private String date;

    public VatechSupportAdapter(Context mContext, List<SupportData> supportData) {
        this.mContext = mContext;
        this.supportData = supportData;
    }

    @NonNull
    @Override
    public VatechSupportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vatech_support_layout, parent, false);
        return new VatechSupportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VatechSupportViewHolder holder, int position) {
        holder.reasonType_Tv.setText(supportData.get(position).getSupportTypeValue());
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        date = formatter.format(Date.parse(supportData.get(position).getRcdInsTs()));
        holder.supportDate_Tv.setText(date);
        holder.reasonDetails.setText(supportData.get(position).getSupportDetails());
        holder.statusSupport.setText(supportData.get(position).getStrReasonStatus());

        setClickEventForViewSupport(holder, position);
    }

    private void setClickEventForViewSupport(VatechSupportViewHolder holder, int position) {
        try {
            holder.viewSupport_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewSupportDetailsActivity.class);
                    intent.putExtra("SupportId", supportData.get(position).getSupportId());
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return supportData.size();
    }

    public void updateSupportList(List<SupportData> list) {
        supportData = list;
        notifyDataSetChanged();
    }
}
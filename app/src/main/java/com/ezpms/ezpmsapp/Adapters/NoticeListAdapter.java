package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Activities.NoticeDetailsActivity;
import com.ezpms.ezpmsapp.Models.AllAppNoticesData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ViewHolders.NoticeListViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListViewHolder> {

    private Context mContext;
    private List<AllAppNoticesData> appNoticesData;
    private SimpleDateFormat formatter;
    private String date;

    public NoticeListAdapter(Context mContext, List<AllAppNoticesData> appNoticesData) {
        this.mContext = mContext;
        this.appNoticesData = appNoticesData;
    }

    @NonNull
    @Override
    public NoticeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notice_list_layout, parent, false);
        return new NoticeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeListViewHolder holder, int position) {
        holder.noticeTitle_Tv.setText(appNoticesData.get(position).getNoticeTitle());
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        date = formatter.format(Date.parse(appNoticesData.get(position).getNoticeDate()));
        holder.noticeDate_Tv.setText(date);
        holder.noticeDetails.setText(appNoticesData.get(position).getNoticeDetails());

        getActionViewForNotice(holder, position);
    }

    private void getActionViewForNotice(NoticeListViewHolder holder, int position) {
        try {
            holder.viewNotice_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NoticeDetailsActivity.class);
                    intent.putExtra("NoticeId", appNoticesData.get(position).getNoticeId());
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return appNoticesData.size();
    }

    public void updateNoticeList(List<AllAppNoticesData> list) {
        appNoticesData = list;
        notifyDataSetChanged();
    }
}

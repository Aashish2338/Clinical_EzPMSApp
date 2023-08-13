package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class NoticeListViewHolder extends RecyclerView.ViewHolder {

    public TextView noticeTitle_Tv, noticeDate_Tv, noticeDetails, viewNotice_Btn;

    public NoticeListViewHolder(@NonNull View itemView) {
        super(itemView);

        noticeTitle_Tv = (TextView) itemView.findViewById(R.id.noticeTitle_Tv);
        noticeDate_Tv = (TextView) itemView.findViewById(R.id.noticeDate_Tv);
        noticeDetails = (TextView) itemView.findViewById(R.id.noticeDetails);
        viewNotice_Btn = (TextView) itemView.findViewById(R.id.viewNotice_Btn);

    }
}

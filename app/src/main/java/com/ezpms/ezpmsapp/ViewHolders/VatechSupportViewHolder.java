package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class VatechSupportViewHolder extends RecyclerView.ViewHolder {

    public TextView reasonType_Tv, supportDate_Tv, reasonDetails, statusSupport, viewSupport_Btn;

    public VatechSupportViewHolder(@NonNull View itemView) {
        super(itemView);

        reasonType_Tv = (TextView) itemView.findViewById(R.id.reasonType_Tv);
        supportDate_Tv = (TextView) itemView.findViewById(R.id.supportDate_Tv);
        reasonDetails = (TextView) itemView.findViewById(R.id.reasonDetails);
        statusSupport = (TextView) itemView.findViewById(R.id.statusSupport);
        viewSupport_Btn = (TextView) itemView.findViewById(R.id.viewSupport_Btn);
    }
}

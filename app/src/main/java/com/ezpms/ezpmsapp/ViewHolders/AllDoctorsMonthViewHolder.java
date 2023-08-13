package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class AllDoctorsMonthViewHolder extends RecyclerView.ViewHolder {

    public ImageView doctorImageP;
    public TextView doctorName, doctorProfession;

    public AllDoctorsMonthViewHolder(@NonNull View itemView) {
        super(itemView);
        doctorImageP = (ImageView) itemView.findViewById(R.id.doctorImageP);
        doctorName = (TextView) itemView.findViewById(R.id.doctorName);
        doctorProfession = (TextView) itemView.findViewById(R.id.doctorProfession);
    }
}

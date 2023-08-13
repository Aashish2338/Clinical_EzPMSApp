package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;
import com.google.android.material.card.MaterialCardView;

public class AvailableDoctorViewHolder extends RecyclerView.ViewHolder {

    public ImageView doctorImageP;
    public TextView doctorName, doctorProfession, doctorAssignment;
    public MaterialCardView assignementAppoint;

    public AvailableDoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        doctorImageP = (ImageView) itemView.findViewById(R.id.doctorImageP);
        doctorName = (TextView) itemView.findViewById(R.id.doctorName);
        doctorProfession = (TextView) itemView.findViewById(R.id.doctorProfession);
        doctorAssignment = (TextView) itemView.findViewById(R.id.doctorAssignment);
        assignementAppoint = (MaterialCardView) itemView.findViewById(R.id.assignementAppoint);
    }
}

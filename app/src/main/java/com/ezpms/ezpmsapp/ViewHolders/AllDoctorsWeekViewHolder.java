package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;
import com.google.android.material.card.MaterialCardView;

public class AllDoctorsWeekViewHolder extends RecyclerView.ViewHolder {

    public ImageView doctorImageP, viewDetails;
    public TextView doctorName, doctorProfession, patientName, date, time;
    public MaterialCardView id_card;

    public AllDoctorsWeekViewHolder(@NonNull View itemView) {
        super(itemView);

        doctorImageP = (ImageView) itemView.findViewById(R.id.doctorImageP);
        viewDetails = (ImageView) itemView.findViewById(R.id.viewDetails);
        doctorName = (TextView) itemView.findViewById(R.id.doctorName);
        doctorProfession = (TextView) itemView.findViewById(R.id.doctorProfession);
        id_card = (MaterialCardView) itemView.findViewById(R.id.id_card);
        patientName =  itemView.findViewById(R.id.patientName);
        date =  itemView.findViewById(R.id.date);
        time =  itemView.findViewById(R.id.time);
    }
}

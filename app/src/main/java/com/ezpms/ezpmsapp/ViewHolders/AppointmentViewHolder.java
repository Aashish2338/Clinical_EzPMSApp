package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {

    public TextView patientName_Dp, doctorName_Dp, appointmentTime_Dp;
    public TextView patientId_Dp, patientMobileNo_Dp, appointmentDate_Dp;

    public AppointmentViewHolder(View itemView) {
        super(itemView);
        patientName_Dp = (TextView) itemView.findViewById(R.id.patientName_Dp);
        doctorName_Dp = (TextView) itemView.findViewById(R.id.doctorName_Dp);
        appointmentTime_Dp = (TextView) itemView.findViewById(R.id.appointmentTime_Dp);
        patientId_Dp = (TextView) itemView.findViewById(R.id.patientId_Dp);
        patientMobileNo_Dp = (TextView) itemView.findViewById(R.id.patientMobileNo_Dp);
        appointmentDate_Dp = (TextView) itemView.findViewById(R.id.appointmentDate_Dp);

    }
}

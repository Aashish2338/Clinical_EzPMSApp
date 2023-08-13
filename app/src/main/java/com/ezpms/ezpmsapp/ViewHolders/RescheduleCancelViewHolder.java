package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class RescheduleCancelViewHolder extends RecyclerView.ViewHolder {

    public TextView patientName_Tv, doctorName_Tv, patientId_Tv, patientMobileNo_Tv, doctorMobileNo_Tv;
    public TextView cosultationType_Tv, appointmentDate_Tv, appointmentTime_Tv;
    public RelativeLayout rescheduleCancel_Rl;

    public RescheduleCancelViewHolder(@NonNull View itemView) {
        super(itemView);

        rescheduleCancel_Rl = (RelativeLayout) itemView.findViewById(R.id.rescheduleCancel_Rl);
        patientName_Tv = (TextView) itemView.findViewById(R.id.patientName_Tv);
        doctorName_Tv = (TextView) itemView.findViewById(R.id.doctorName_Tv);
        patientId_Tv = (TextView) itemView.findViewById(R.id.patientId_Tv);
        patientMobileNo_Tv = (TextView) itemView.findViewById(R.id.patientMobileNo_Tv);
        doctorMobileNo_Tv = (TextView) itemView.findViewById(R.id.doctorMobileNo_Tv);
        cosultationType_Tv = (TextView) itemView.findViewById(R.id.cosultationType_Tv);
        appointmentDate_Tv = (TextView) itemView.findViewById(R.id.appointmentDate_Tv);
        appointmentTime_Tv = (TextView) itemView.findViewById(R.id.appointmentTime_Tv);

    }
}

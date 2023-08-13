package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class NextVisitAppointmentViewHolder extends RecyclerView.ViewHolder {

    public TextView patientName_Ap, doctorName_Ap, patientId_Ap, patientMobileNo_Ap;
    public TextView doctorMobileNo_Ap, cosultationType_Ap, appointmentDate_Ap, appointmentTime_Ap;
    public RelativeLayout updateAppointment;

    public NextVisitAppointmentViewHolder(@NonNull View itemView) {
        super(itemView);

        updateAppointment = (RelativeLayout) itemView.findViewById(R.id.updateAppointment);
        patientName_Ap = (TextView) itemView.findViewById(R.id.patientName_Ap);
        doctorName_Ap = (TextView) itemView.findViewById(R.id.doctorName_Ap);
        patientId_Ap = (TextView) itemView.findViewById(R.id.patientId_Ap);
        patientMobileNo_Ap = (TextView) itemView.findViewById(R.id.patientMobileNo_Ap);
        doctorMobileNo_Ap = (TextView) itemView.findViewById(R.id.doctorMobileNo_Ap);
        cosultationType_Ap = (TextView) itemView.findViewById(R.id.cosultationType_Ap);
        appointmentDate_Ap = (TextView) itemView.findViewById(R.id.appointmentDate_Ap);
        appointmentTime_Ap = (TextView) itemView.findViewById(R.id.appointmentTime_Ap);

    }
}

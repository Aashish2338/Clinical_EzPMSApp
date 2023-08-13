package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class TreatmentUpdateViewHolder extends RecyclerView.ViewHolder {

    public TextView patientName_Tr, doctorName_Tr, patientId_Tr, mobileNo_Tr;
    public TextView consultationType_Tr, appointmentDate_Tr, appointmentTime_Tr, treatmentDate_Tr;
    public RelativeLayout updateDetails;

    public TreatmentUpdateViewHolder(@NonNull View itemView) {
        super(itemView);

        updateDetails = (RelativeLayout) itemView.findViewById(R.id.updateDetails);
        patientName_Tr = (TextView) itemView.findViewById(R.id.patientName_Tr);
        doctorName_Tr = (TextView) itemView.findViewById(R.id.doctorName_Tr);
        patientId_Tr = (TextView) itemView.findViewById(R.id.patientId_Tr);
        mobileNo_Tr = (TextView) itemView.findViewById(R.id.mobileNo_Tr);
        consultationType_Tr = (TextView) itemView.findViewById(R.id.consultationType_Tr);
        appointmentDate_Tr = (TextView) itemView.findViewById(R.id.appointmentDate_Tr);
        appointmentTime_Tr = (TextView) itemView.findViewById(R.id.appointmentTime_Tr);
        treatmentDate_Tr = (TextView) itemView.findViewById(R.id.treatmentDate_Tr);
    }
}

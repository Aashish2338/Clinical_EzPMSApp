package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class PatientListViewHolder extends RecyclerView.ViewHolder {

    public TextView patientName_Pt, patientId_Pts, mobileNo_Pt, maleFemale_Pt;
    public TextView dateOfBirth_Pt, lastVisitDate_Pt, registrationDate_Pt;
    public RelativeLayout patientList_RC;

    public PatientListViewHolder(@NonNull View itemView) {
        super(itemView);

        patientList_RC = (RelativeLayout) itemView.findViewById(R.id.patientList_RC);
        patientName_Pt = (TextView) itemView.findViewById(R.id.patientName_Pt);
        patientId_Pts = (TextView) itemView.findViewById(R.id.patientId_Pts);
        mobileNo_Pt = (TextView) itemView.findViewById(R.id.mobileNo_Pt);
        maleFemale_Pt = (TextView) itemView.findViewById(R.id.maleFemale_Pt);
        dateOfBirth_Pt = (TextView) itemView.findViewById(R.id.dateOfBirth_Pt);
        lastVisitDate_Pt = (TextView) itemView.findViewById(R.id.lastVisitDate_Pt);
        registrationDate_Pt = (TextView) itemView.findViewById(R.id.registrationDate_Pt);
    }
}

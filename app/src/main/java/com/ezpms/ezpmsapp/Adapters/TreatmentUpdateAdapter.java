package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Models.AllTreatmentData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Treatments.NewTreatmentsActivity;
import com.ezpms.ezpmsapp.Treatments.TreatmentsHistoryActivity;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.TreatmentUpdateViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TreatmentUpdateAdapter extends RecyclerView.Adapter<TreatmentUpdateViewHolder> {

    private Context mContext;
    private List<AllTreatmentData> allTreatmentData;
    private SessionManager sessionManager;
    private SimpleDateFormat formatter, formatters;
    private String date, dates;

    public TreatmentUpdateAdapter(Context mContext, List<AllTreatmentData> allTreatmentData) {
        this.mContext = mContext;
        this.allTreatmentData = allTreatmentData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public TreatmentUpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.treatment_update_layout, parent, false);
        return new TreatmentUpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentUpdateViewHolder holder, int position) {
        try {
            if (allTreatmentData.get(position).getPatientFirstName() == null && allTreatmentData.get(position).getPatientLastName() == null) {
                holder.patientName_Tr.setText(" ");
            } else {
                holder.patientName_Tr.setText(allTreatmentData.get(position).getPatientFirstName() + " " + allTreatmentData.get(position).getPatientLastName());
            }
            if (allTreatmentData.get(position).getDoctorName() != null) {
                holder.doctorName_Tr.setText(allTreatmentData.get(position).getDoctorName());
            } else {
                holder.doctorName_Tr.setText(" ");
            }
            int lenght = String.valueOf(allTreatmentData.get(position).getPatientId()).length();
            if (lenght == 6) {
                holder.patientId_Tr.setText("0" + allTreatmentData.get(position).getPatientId().toString());
            } else if (lenght == 5) {
                holder.patientId_Tr.setText("00" + allTreatmentData.get(position).getPatientId().toString());
            } else if (lenght == 4) {
                holder.patientId_Tr.setText("000" + allTreatmentData.get(position).getPatientId().toString());
            } else if (lenght == 3) {
                holder.patientId_Tr.setText("0000" + allTreatmentData.get(position).getPatientId().toString());
            } else if (lenght == 2) {
                holder.patientId_Tr.setText("00000" + allTreatmentData.get(position).getPatientId().toString());
            } else {
                holder.patientId_Tr.setText("000000" + allTreatmentData.get(position).getPatientId().toString());
            }

            if (allTreatmentData.get(position).getPhoneNumber() != null) {
                holder.mobileNo_Tr.setText(allTreatmentData.get(position).getPhoneNumber());
            } else {
                holder.mobileNo_Tr.setText(" ");
            }
            holder.consultationType_Tr.setText(allTreatmentData.get(position).getConsultationTypeName());
            formatter = new SimpleDateFormat("dd-MMM-yyyy");
            date = formatter.format(Date.parse(allTreatmentData.get(position).getAppointmentDate()));
            holder.appointmentDate_Tr.setText(date);
            if (allTreatmentData.get(position).getTimeFrom() != null && allTreatmentData.get(position).getTimeTo() != null) {
                holder.appointmentTime_Tr.setText(allTreatmentData.get(position).getTimeFrom() + " - " + allTreatmentData.get(position).getTimeTo());
            } else {
                holder.appointmentTime_Tr.setText(" ");
            }

            if (allTreatmentData.get(position).getTreatmentDate() != null) {
                String str = allTreatmentData.get(position).getTreatmentDate();
                String treatmentDate = str.substring(0, 10);
                holder.treatmentDate_Tr.setText(treatmentDate);
            } else {
                holder.treatmentDate_Tr.setText(" ");
            }

            setClickEventForUpdate(holder, position);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setClickEventForUpdate(TreatmentUpdateViewHolder holder, int position) {
        try {
            holder.updateDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (allTreatmentData.get(position).getTreatmentDate() != null) {
                        Intent intent = new Intent(mContext, TreatmentsHistoryActivity.class);
                        intent.putExtra("PatientId", allTreatmentData.get(position).getPatientId());
                        intent.putExtra("AppAppointmentId", allTreatmentData.get(position).getAppAppointmentId());
                        intent.putExtra("TreatmentId", allTreatmentData.get(position).getTreatmentId());
                        intent.putExtra("AppointmentId", allTreatmentData.get(position).getAppointmentId());
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, NewTreatmentsActivity.class);
                        intent.putExtra("PatientId", allTreatmentData.get(position).getPatientId());
                        intent.putExtra("AppAppointmentId", allTreatmentData.get(position).getAppAppointmentId());
                        intent.putExtra("TreatmentId", allTreatmentData.get(position).getTreatmentId());
                        intent.putExtra("AppointmentId", allTreatmentData.get(position).getAppointmentId());
                        mContext.startActivity(intent);
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allTreatmentData.size();
    }

    public void updateTreatmentUpdateList(List<AllTreatmentData> list) {
        allTreatmentData = list;
        notifyDataSetChanged();
    }
}
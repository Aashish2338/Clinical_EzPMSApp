package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Models.AppointmentBydateData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ViewHolders.AppointmentViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentAdapters extends RecyclerView.Adapter<AppointmentViewHolder> {

    private Context mContext;
    private List<AppointmentBydateData> appointmentBydateData;
    private SimpleDateFormat formatter;
    private String date;

    public AppointmentAdapters(Context mContext, List<AppointmentBydateData> appointmentBydateData) {
        this.mContext = mContext;
        this.appointmentBydateData = appointmentBydateData;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dayview_data_layout, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        if (appointmentBydateData.get(position).getPatientFirstName() != null && appointmentBydateData.get(position).getPatientLastName() != null) {
            holder.patientName_Dp.setText(appointmentBydateData.get(position).getPatientFirstName() + " " + appointmentBydateData.get(position).getPatientLastName());
        } else {
            holder.patientName_Dp.setText(" ");
        }
        if (appointmentBydateData.get(position).getDoctorName() != null) {
            holder.doctorName_Dp.setText(appointmentBydateData.get(position).getDoctorName().toString());
        } else {
            holder.doctorName_Dp.setText(" ");
        }
        if (appointmentBydateData.get(position).getTimeFrom() != null) {
            holder.appointmentTime_Dp.setText(appointmentBydateData.get(position).getTimeFrom());
        } else {
            holder.appointmentTime_Dp.setText(" ");
        }
        if (appointmentBydateData.get(position).getPatientId() != null) {
            int lenght = String.valueOf(appointmentBydateData.get(position).getPatientId()).length();
            if (lenght == 6) {
                holder.patientId_Dp.setText("0" + appointmentBydateData.get(position).getPatientId().toString());
            } else if (lenght == 5) {
                holder.patientId_Dp.setText("00" + appointmentBydateData.get(position).getPatientId().toString());
            } else if (lenght == 4) {
                holder.patientId_Dp.setText("000" + appointmentBydateData.get(position).getPatientId().toString());
            } else if (lenght == 3) {
                holder.patientId_Dp.setText("0000" + appointmentBydateData.get(position).getPatientId().toString());
            } else if (lenght == 2) {
                holder.patientId_Dp.setText("00000" + appointmentBydateData.get(position).getPatientId().toString());
            } else {
                holder.patientId_Dp.setText("000000" + appointmentBydateData.get(position).getPatientId().toString());
            }
        } else {
            holder.patientId_Dp.setText(" ");
        }
        if (appointmentBydateData.get(position).getPhoneNumber() != null) {
            holder.patientMobileNo_Dp.setText(appointmentBydateData.get(position).getPhoneNumber());
        } else {
            holder.patientMobileNo_Dp.setText(" ");
        }
        if (appointmentBydateData.get(position).getAppointmentDate() != null) {
            formatter = new SimpleDateFormat("dd-MMM-yyyy");
            date = formatter.format(Date.parse(appointmentBydateData.get(position).getAppointmentDate()));
            holder.appointmentDate_Dp.setText(date);
        } else {
            holder.appointmentDate_Dp.setText(" ");
        }
    }

    @Override
    public int getItemCount() {
        return appointmentBydateData.size();
    }
}

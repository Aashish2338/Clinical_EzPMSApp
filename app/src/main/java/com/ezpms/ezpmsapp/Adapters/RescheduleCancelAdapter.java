package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Appointments.UpdateAppointmentsActivity;
import com.ezpms.ezpmsapp.Models.AllAppointmentData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.RescheduleCancelViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RescheduleCancelAdapter extends RecyclerView.Adapter<RescheduleCancelViewHolder> {

    private Context mContext;
    private List<AllAppointmentData> allAppointmentData;
    private SimpleDateFormat formatter;
    private String date;
    private SessionManager sessionManager;

    public RescheduleCancelAdapter(Context mContext, List<AllAppointmentData> allAppointmentData) {
        this.mContext = mContext;
        this.allAppointmentData = allAppointmentData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public RescheduleCancelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reschedule_cancel_layout, parent, false);
        return new RescheduleCancelViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RescheduleCancelViewHolder holder, int position) {
        holder.patientName_Tv.setText(allAppointmentData.get(position).getPatientFirstName() + " " + allAppointmentData.get(position).getPatientLastName());
        if (allAppointmentData.get(position).getDoctorName() != null) {
            holder.doctorName_Tv.setText(allAppointmentData.get(position).getDoctorName().toString());
        } else {
            holder.doctorName_Tv.setText("");
        }

        int lenght = String.valueOf(allAppointmentData.get(position).getPatientId()).length();
        if (lenght == 6) {
            holder.patientId_Tv.setText("0" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 5) {
            holder.patientId_Tv.setText("00" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 4) {
            holder.patientId_Tv.setText("000" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 3) {
            holder.patientId_Tv.setText("0000" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 2) {
            holder.patientId_Tv.setText("00000" + allAppointmentData.get(position).getPatientId().toString());
        } else {
            holder.patientId_Tv.setText("000000" + allAppointmentData.get(position).getPatientId().toString());
        }

        if (allAppointmentData.get(position).getPhoneNumber() != null) {
            holder.patientMobileNo_Tv.setText(allAppointmentData.get(position).getPhoneNumber());
        } else {
            holder.patientMobileNo_Tv.setText("");
        }

        if (allAppointmentData.get(position).getDrPhoneNumber() != null) {
            holder.doctorMobileNo_Tv.setText(allAppointmentData.get(position).getDrPhoneNumber());
        } else {
            holder.doctorMobileNo_Tv.setText(" ");
        }

        if (allAppointmentData.get(position).getConsultationTypeName() != null) {
            holder.cosultationType_Tv.setText(allAppointmentData.get(position).getConsultationTypeName().toString());
        } else {
            holder.cosultationType_Tv.setText(" ");
        }
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        date = formatter.format(Date.parse(allAppointmentData.get(position).getAppointmentDate()));
        holder.appointmentDate_Tv.setText(date);

        if (allAppointmentData.get(position).getTimeFrom() != null && allAppointmentData.get(position).getTimeTo() != null) {
            holder.appointmentTime_Tv.setText(allAppointmentData.get(position).getTimeFrom() + " - " + allAppointmentData.get(position).getTimeTo());
        } else {
            holder.appointmentTime_Tv.setText(" ");
        }

        getClickEventsForRescheduleCancel(holder, position);

    }

    private void getClickEventsForRescheduleCancel(RescheduleCancelViewHolder holder, int positions) {
        try {
            holder.rescheduleCancel_Rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UpdateAppointmentsActivity.class);
                    intent.putExtra("AppointmentId", allAppointmentData.get(positions).getAppointmentId());
                    intent.putExtra("PatientId", allAppointmentData.get(positions).getPatientId());
                    intent.putExtra("ClinicId", allAppointmentData.get(positions).getClinicId());
                    intent.putExtra("AppointmentStatus", allAppointmentData.get(positions).getAppointmentStatus());
                    formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String dates = formatter.format(Date.parse(allAppointmentData.get(positions).getAppointmentDate()));
                    intent.putExtra("Date", dates);
                    intent.putExtra("KeyNotes", allAppointmentData.get(positions).getKeyNotes());
                    mContext.startActivity(intent);

                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allAppointmentData.size();
    }

    public void updateRescheduleCancelList(List<AllAppointmentData> list) {
        allAppointmentData = list;
        notifyDataSetChanged();
    }
}

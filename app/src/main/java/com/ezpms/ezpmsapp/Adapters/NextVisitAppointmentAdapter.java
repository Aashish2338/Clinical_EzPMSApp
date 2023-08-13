package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Appointments.UpdateAppointmentsActivity;
import com.ezpms.ezpmsapp.Models.AllAppointmentData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.NextVisitAppointmentViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NextVisitAppointmentAdapter extends RecyclerView.Adapter<NextVisitAppointmentViewHolder> {

    private Context mContext;
    private List<AllAppointmentData> allAppointmentData;
    private SimpleDateFormat formatter;
    private String date;
    private SessionManager sessionManager;
    private String formattedDate;

    public NextVisitAppointmentAdapter(Context mContext, List<AllAppointmentData> allAppointmentData) {
        this.mContext = mContext;
        this.allAppointmentData = allAppointmentData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public NextVisitAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.next_visit_appointment_layout, parent, false);
        return new NextVisitAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NextVisitAppointmentViewHolder holder, int position) {
        setCurrentDate();
        holder.patientName_Ap.setText(allAppointmentData.get(position).getPatientFirstName()
                + " " + allAppointmentData.get(position).getPatientLastName());
        if (allAppointmentData.get(position).getDoctorName() != null) {
            holder.doctorName_Ap.setText(allAppointmentData.get(position).getDoctorName().toString());
        } else {
            holder.doctorName_Ap.setText(" ");
        }
        int lenght = String.valueOf(allAppointmentData.get(position).getPatientId()).length();
        if (lenght == 6) {
            holder.patientId_Ap.setText("0" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 5) {
            holder.patientId_Ap.setText("00" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 4) {
            holder.patientId_Ap.setText("000" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 3) {
            holder.patientId_Ap.setText("0000" + allAppointmentData.get(position).getPatientId().toString());
        } else if (lenght == 2) {
            holder.patientId_Ap.setText("00000" + allAppointmentData.get(position).getPatientId().toString());
        } else {
            holder.patientId_Ap.setText("000000" + allAppointmentData.get(position).getPatientId().toString());
        }

        if (allAppointmentData.get(position).getPhoneNumber() != null) {
            holder.patientMobileNo_Ap.setText(allAppointmentData.get(position).getPhoneNumber());
        } else {
            holder.patientMobileNo_Ap.setText("");
        }
        if (allAppointmentData.get(position).getDrPhoneNumber() != null) {
            holder.doctorMobileNo_Ap.setText(allAppointmentData.get(position).getDrPhoneNumber());
        } else {
            holder.doctorMobileNo_Ap.setText(" ");
        }
        if (allAppointmentData.get(position).getConsultationTypeName() != null) {
            holder.cosultationType_Ap.setText(allAppointmentData.get(position).getConsultationTypeName().toString());
        } else {
            holder.cosultationType_Ap.setText(" ");
        }
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        date = formatter.format(Date.parse(allAppointmentData.get(position).getAppointmentDate()));
        holder.appointmentDate_Ap.setText(date);

        if (allAppointmentData.get(position).getTimeFrom() != null && allAppointmentData.get(position).getTimeTo() != null) {
            holder.appointmentTime_Ap.setText(allAppointmentData.get(position).getTimeFrom()
                    + " - " + allAppointmentData.get(position).getTimeTo());
        } else {
            holder.appointmentTime_Ap.setText(" ");
        }

        getClickEventsForRescheduleCancel(holder, position);
    }

    private void getClickEventsForRescheduleCancel(NextVisitAppointmentViewHolder holder, int positions) {
        try {
            holder.updateAppointment.setOnClickListener(new View.OnClickListener() {
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
//                    sessionManager.setAppointmentId(allAppointmentData.get(positions).getAppointmentId());
//                    sessionManager.setPatientId(allAppointmentData.get(positions).getPatientId());
//                    sessionManager.setClinicId(allAppointmentData.get(positions).getClinicId());
//                    mContext.startActivity(new Intent(mContext, UpdateAppointmentsActivity.class));
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setCurrentDate() {
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            formattedDate = df.format(c);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allAppointmentData.size();
    }

    public void updateAppointmentList(List<AllAppointmentData> list) {
        allAppointmentData = list;
        notifyDataSetChanged();
    }
}

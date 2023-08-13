package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Appointments.NewAppointmentActivity;
import com.ezpms.ezpmsapp.Interfaces.ClickListener;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.AvailableDoctorViewHolder;

import java.util.List;

public class AvailableDoctorAdapter extends RecyclerView.Adapter<AvailableDoctorViewHolder> {

    private Context mContext;
    private List<DoctorAvailableData> doctorAvailableData = null;
    private SessionManager sessionManager;

    public AvailableDoctorAdapter(Context mContext, List<DoctorAvailableData> doctorAvailableData) {
        this.mContext = mContext;
        this.doctorAvailableData = doctorAvailableData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public AvailableDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.availbale_doctor_layout, parent, false);
        return new AvailableDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableDoctorViewHolder holder, int position) {
        holder.doctorName.setText(doctorAvailableData.get(position).getDoctorName());

        setClickEventForAssignment(holder, position);
    }

    private void setClickEventForAssignment(AvailableDoctorViewHolder holder, int position) {
        try {
            holder.assignementAppoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sessionManager.setDoctorId(doctorAvailableData.get(position).getDoctorId());
                    mContext.startActivity(new Intent(mContext, NewAppointmentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
        } catch (Exception xp) {
            xp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return doctorAvailableData.size();
    }
}

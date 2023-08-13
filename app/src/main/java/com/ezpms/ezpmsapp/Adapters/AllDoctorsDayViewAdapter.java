package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Appointments.NewAppointmentActivity;
import com.ezpms.ezpmsapp.Models.AllAppointmentData;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.AllDoctorsDayViewHolder;

import java.util.List;

public class AllDoctorsDayViewAdapter extends RecyclerView.Adapter<AllDoctorsDayViewHolder> {

    private Context mContext;
    private List<DoctorAvailableData> doctorAvailableData;
    private SessionManager sessionManager;

    public AllDoctorsDayViewAdapter(Context mContext, List<DoctorAvailableData> doctorAvailableData) {
        this.mContext = mContext;
        this.doctorAvailableData = doctorAvailableData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public AllDoctorsDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alldoctors_dayviews_layout, parent, false);
        return new AllDoctorsDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDoctorsDayViewHolder holder, int position) {
        holder.doctorName.setText(doctorAvailableData.get(position).getDoctorName());

        setClickEventForAssignment(holder, position);
    }

    private void setClickEventForAssignment(AllDoctorsDayViewHolder holder, int position) {

//        sessionManager.setDoctorId(doctorAvailableData.get(position).getDoctorId());
//        mContext.startActivity(new Intent(mContext, NewAppointmentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public int getItemCount() {
        return doctorAvailableData.size();
    }

    public void updateDoctorsDayViewList(List<DoctorAvailableData> doctor) {
        doctorAvailableData = doctor;
        notifyDataSetChanged();
    }
}

package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.AllDoctorsDayViewHolder;
import com.ezpms.ezpmsapp.ViewHolders.AllDoctorsWeekViewHolder;

import java.util.List;

public class AllDoctorsWeekViewAdapter extends RecyclerView.Adapter<AllDoctorsWeekViewHolder> {

    private Context mContext;
    private List<DoctorAvailableData> doctorAvailableData;
    private SessionManager sessionManager;

    public AllDoctorsWeekViewAdapter(Context mContext, List<DoctorAvailableData> doctorAvailableData) {
        this.mContext = mContext;
        this.doctorAvailableData = doctorAvailableData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public AllDoctorsWeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alldoctors_weekviews_layout, parent, false);
        return new AllDoctorsWeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDoctorsWeekViewHolder holder, int position) {
        holder.doctorName.setText(doctorAvailableData.get(position).getDoctorName());

        setClickEventForAssignment(holder, position);
    }

    private void setClickEventForAssignment(AllDoctorsWeekViewHolder holder, int position) {
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

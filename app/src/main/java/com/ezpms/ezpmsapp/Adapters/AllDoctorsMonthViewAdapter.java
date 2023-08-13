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
import com.ezpms.ezpmsapp.ViewHolders.AllDoctorsMonthViewHolder;

import java.util.List;

public class AllDoctorsMonthViewAdapter extends RecyclerView.Adapter<AllDoctorsMonthViewHolder> {

    private Context mContext;
    private List<DoctorAvailableData> doctorAvailableData;
    private SessionManager sessionManager;

    public AllDoctorsMonthViewAdapter(Context mContext, List<DoctorAvailableData> doctorAvailableData) {
        this.mContext = mContext;
        this.doctorAvailableData = doctorAvailableData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public AllDoctorsMonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alldoctors_monthviews_layout, parent, false);
        return new AllDoctorsMonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDoctorsMonthViewHolder holder, int position) {
        holder.doctorName.setText(doctorAvailableData.get(position).getDoctorName());

        setClickEventForAssignment(holder, position);
    }

    private void setClickEventForAssignment(AllDoctorsMonthViewHolder holder, int position) {
        //        sessionManager.setDoctorId(doctorAvailableData.get(position).getDoctorId());
//        mContext.startActivity(new Intent(mContext, NewAppointmentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public int getItemCount() {
        return doctorAvailableData.size() ;
    }

    public void updateDoctorsDayViewList(List<DoctorAvailableData> doctor) {
        doctorAvailableData = doctor;
        notifyDataSetChanged();
    }
}

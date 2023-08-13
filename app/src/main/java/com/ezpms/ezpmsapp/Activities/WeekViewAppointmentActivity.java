package com.ezpms.ezpmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AppointmentBydateData;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentBydateResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.AllDoctorsWeekViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekViewAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView backMenu;
    private TextView Tv_Title;
    private EditText et_SearchingByName;
    private String fromDate, toDate, title;
    private int userId;
    private RecyclerView doctorsWeekViewsRc;
    private AllDoctorsWeekViewAdapter allDoctorsWeekViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<AppointmentBydateData> appointmentBydateData = null;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view_appointment);

        mContext = this;
        sessionManager = new SessionManager(mContext);
        getLayouUiId();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            fromDate = extra.getString("FromDate");
            toDate = extra.getString("Todate");
            title = extra.getString("Day");
//            userId = extra.getInt("UserID");
        }

        Tv_Title.setText(title);

        backMenu.setOnClickListener(this);
        getAllAppointmentByDateMethod(fromDate, toDate);
        getSearchDataByName();
    }

    private void getRecyclerForWeekViews(List<AppointmentBydateData> doctorAvailableData) {
        try {
            allDoctorsWeekViewAdapter = new AllDoctorsWeekViewAdapter(mContext, doctorAvailableData);
            linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            doctorsWeekViewsRc.setLayoutManager(linearLayoutManager);
            doctorsWeekViewsRc.setAdapter(allDoctorsWeekViewAdapter);
            allDoctorsWeekViewAdapter.notifyDataSetChanged();

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayouUiId() {
        try {
            backMenu = (ImageView) findViewById(R.id.backMenu);
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            et_SearchingByName = (EditText) findViewById(R.id.et_SearchingByName);
            doctorsWeekViewsRc = (RecyclerView) findViewById(R.id.doctorsWeekViewsRc);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllAppointmentByDateMethod(String fromDate, String todate) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllAppointmentBydateResponse> allAppointmentBydateResponseCall = apiClientNetwork.getAllAppointmentBydate(fromDate, todate);
            allAppointmentBydateResponseCall.enqueue(new Callback<AllAppointmentBydateResponse>() {
                @Override
                public void onResponse(Call<AllAppointmentBydateResponse> call, Response<AllAppointmentBydateResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getAppointmentBydateData() != null) {
                                appointmentBydateData = response.body().getAppointmentBydateData();
                                if (appointmentBydateData.size() > 0) {
                                    getRecyclerForWeekViews(appointmentBydateData);
                                } else {
                                    Toast.makeText(mContext, "No Appointment found !", Toast.LENGTH_SHORT).show();
                                }
                              /*  if (NetworkStatus.isNetworkAvailable(mContext)) {
                                    getAllAppointmentCountMethod(fromDate, todate);
                                } else {
                                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                }*/
                            } else {
//                                allAppointmentdata.setVisibility(View.VISIBLE);
//                                appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "No Appointment found !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
//                            allAppointmentdata.setVisibility(View.VISIBLE);
//                            appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "No Appointment Found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
//                        allAppointmentdata.setVisibility(View.VISIBLE);
//                        appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllAppointmentBydateResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
//                    allAppointmentdata.setVisibility(View.VISIBLE);
//                    appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    public class AllDoctorsWeekViewAdapter extends RecyclerView.Adapter<AllDoctorsWeekViewHolder> {

        private Context mContext;
        private List<AppointmentBydateData> doctorAvailableData;

        public AllDoctorsWeekViewAdapter(Context mContext, List<AppointmentBydateData> doctorAvailableData) {
            this.mContext = mContext;
            this.doctorAvailableData = doctorAvailableData;
            sessionManager = new SessionManager(mContext);
        }

        @Override
        public AllDoctorsWeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.weekview_patient_list, parent, false);
            return new AllDoctorsWeekViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AllDoctorsWeekViewHolder holder, int position) {
            holder.patientName.setText(doctorAvailableData.get(position).getPatientFirstName() + " " + doctorAvailableData.get(position).getPatientLastName());

            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM YYYY");

            Date date = null;
            String str = null;

            try {
                date = inputFormat.parse(doctorAvailableData.get(position).getAppointmentDate());
                str = outputFormat.format(date);
                holder.date.setText(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.time.setText("Time: " + doctorAvailableData.get(position).getTimeFrom());
            setClickEventForAssignment(holder, position);
        }

        private void setClickEventForAssignment(AllDoctorsWeekViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return doctorAvailableData.size();
        }

        public void updateDoctorsDayViewList(List<AppointmentBydateData> doctor) {
            doctorAvailableData = doctor;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backMenu:
                onBackPressed();
                break;
        }
    }

    private void getSearchDataByName() {
        try {
            et_SearchingByName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    filter(editable.toString());
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void filter(String toString) {
        try {
            List<AppointmentBydateData> temp = new ArrayList();
            for (AppointmentBydateData patient : appointmentBydateData) {
                if (patient.getPatientFirstName().contains(toString) || patient.getPatientLastName().contains(toString)) {
                    temp.add(patient);
                }
            }
            allDoctorsWeekViewAdapter.updateDoctorsDayViewList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }
}
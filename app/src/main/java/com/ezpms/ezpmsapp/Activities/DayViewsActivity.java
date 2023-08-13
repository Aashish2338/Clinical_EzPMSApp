package com.ezpms.ezpmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.AllDoctorsDayViewAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllAppointmentData;
import com.ezpms.ezpmsapp.Models.AppointmentCountData;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentCountResponse;
import com.ezpms.ezpmsapp.ResponseModels.DoctorAvailableResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.AllDoctorsWeekViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayViewsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, allAppointment, completed, remaining;
    private ImageView backMenu;
    private EditText Et_SearchingByName;
    private RecyclerView doctorsDaysViewsRc;
    private AllDoctorsDaysViewAdapter allDoctorsDayViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<DoctorAvailableData> doctorAvailableData = null;
    private CalendarView calendarView;
    private SessionManager sessionManager;
    private String todate, fromDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_views);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        Tv_Title.setText("Day Appointments");
        backMenu.setOnClickListener(this);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int selectedYear, int selectedMonth, int selectedDay) {
                String selectedDayString = "", selectedMonthString = "";
                int month = selectedMonth + 1;
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date = new Date(selectedYear - 1, selectedMonth, selectedDay);
//                String dayOfWeek = simpledateformat.format(date);

                String selectedDate = selectedYear + "-" + month + "-" + selectedDay;
                Log.d("selectedDate:", "" + selectedDate + "\n" + selectedYear + "\n" + month + "\n" + selectedDay);
                if (selectedDay < 10) {
                    selectedDayString = "0" + selectedDay;
                } else {
                    selectedDayString = "" + selectedDay;
                }
                if (month < 10) {
                    selectedMonthString = "0" + month;
                } else {
                    selectedMonthString = "" + month;
                }
                fromDate = selectedYear + "-" + selectedMonthString + "-" + selectedDayString;
                todate = selectedYear + "-" + selectedMonthString + "-" + selectedDayString;
                getAllAppointmentCountMethod(fromDate, todate);

            }
        });

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAvailableDoctorsList(sessionManager.getUserCliniId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        getSearchDataByName();
        setCurrentDate();
    }

    private void setCurrentDate() {
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            todate = df.format(c);
            fromDate = df.format(c);
            getAllAppointmentCountMethod(fromDate, todate);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getSearchDataByName() {
        try {
            Et_SearchingByName.addTextChangedListener(new TextWatcher() {
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
            List<DoctorAvailableData> temp = new ArrayList();
            for (DoctorAvailableData patient : doctorAvailableData) {
                if (patient.getDoctorName().toLowerCase().contains(toString)) {
                    temp.add(patient);
                }
            }
            allDoctorsDayViewAdapter.updateDoctorsDayViewList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAvailableDoctorsList(int userCliniId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<DoctorAvailableResponse> doctorAvailableResponseCall = apiClientNetwork.getGetDoctorList(userCliniId);
            doctorAvailableResponseCall.enqueue(new Callback<DoctorAvailableResponse>() {
                @Override
                public void onResponse(Call<DoctorAvailableResponse> call, Response<DoctorAvailableResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getDoctorAvailableData() != null) {
                                doctorAvailableData = response.body().getDoctorAvailableData();
                                if (doctorAvailableData.size() >= 0) {
                                    getRecyclerForDayViews(doctorAvailableData);
                                } else {
                                    Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DoctorAvailableResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForDayViews(List<DoctorAvailableData> doctorAvailableData) {
        try {
            allDoctorsDayViewAdapter = new AllDoctorsDaysViewAdapter(mContext, doctorAvailableData);
            linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            doctorsDaysViewsRc.setLayoutManager(linearLayoutManager);
            doctorsDaysViewsRc.setAdapter(allDoctorsDayViewAdapter);
            allDoctorsDayViewAdapter.notifyDataSetChanged();

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            allAppointment = (TextView) findViewById(R.id.allAppointment);
            completed = (TextView) findViewById(R.id.completed);
            remaining = (TextView) findViewById(R.id.remaining);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            doctorsDaysViewsRc = (RecyclerView) findViewById(R.id.doctorsDaysViewsRc);
            calendarView = (CalendarView) findViewById(R.id.calendarView);
            Et_SearchingByName = (EditText) findViewById(R.id.Et_SearchingByName);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllAppointmentCountMethod(String fromDate, String todate) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllAppointmentCountResponse> allAppointmentCountResponseCall = apiClientNetwork.getAllAppointmentCount(fromDate, todate);
            allAppointmentCountResponseCall.enqueue(new Callback<AllAppointmentCountResponse>() {
                @Override
                public void onResponse(Call<AllAppointmentCountResponse> call, Response<AllAppointmentCountResponse> response) {
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getAppointmentCountData() != null) {
                                setTextFieldsValues(response.body().getAppointmentCountData());
                            } else {
                                Toast.makeText(mContext, "Data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "No Appointment Available", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllAppointmentCountResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setTextFieldsValues(AppointmentCountData appointmentCountData) {
        try {
            allAppointment.setText(appointmentCountData.getAllAppintment().toString());
            completed.setText(appointmentCountData.getCompleted().toString());
            remaining.setText(appointmentCountData.getPending().toString());

        } catch (Exception exp) {
            exp.getStackTrace();
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

    public class AllDoctorsDaysViewAdapter extends RecyclerView.Adapter<AllDoctorsWeekViewHolder> {

        private Context mContext;
        private List<DoctorAvailableData> doctorAvailableData;

        public AllDoctorsDaysViewAdapter(Context mContext, List<DoctorAvailableData> doctorAvailableData) {
            this.mContext = mContext;
            this.doctorAvailableData = doctorAvailableData;
            sessionManager = new SessionManager(mContext);
        }

        @Override
        public AllDoctorsWeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.alldoctors_weekviews_layout, parent, false);
            return new AllDoctorsWeekViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AllDoctorsWeekViewHolder holder, int position) {
            holder.doctorName.setText(doctorAvailableData.get(position).getDoctorName());
            setClickEventForAssignment(holder, position);
        }

        private void setClickEventForAssignment(AllDoctorsWeekViewHolder holder, int position) {
            holder.viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WeekViewAppointmentActivity.class);
                    intent.putExtra("FromDate", /*fromDate*/fromDate);
                    intent.putExtra("Todate", /*todate*/todate);
                    intent.putExtra("Day", "Day Appointments");
                    intent.putExtra("UserID", doctorAvailableData.get(position).getUserID());
                    startActivity(intent);
                    System.out.println("From Date :- " + fromDate + ", To date :- " + todate + "User-ID :- " + doctorAvailableData.get(position).getUserID());
                }
            });

            holder.id_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WeekViewAppointmentActivity.class);
                    intent.putExtra("FromDate", /*fromDate*/fromDate);
                    intent.putExtra("Todate", /*todate*/todate);
                    intent.putExtra("Day", "Day Appointments");
                    intent.putExtra("UserID", doctorAvailableData.get(position).getUserID());
                    startActivity(intent);
                    System.out.println("From Date :- " + fromDate + ", To date :- " + todate + "User-ID :- " + doctorAvailableData.get(position).getUserID());
                }
            });
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
}
package com.ezpms.ezpmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.AppointmentAdapters;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AppointmentBydateData;
import com.ezpms.ezpmsapp.Models.AppointmentCountData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentBydateResponse;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentCountResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayViewAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView backMenu;
    private TextView Tv_Title, allAppointment, completed, remaining, allAppointmentdata;
    private RecyclerView appointmentDayWeekMonthViewsRc;
    private AppointmentAdapters appointmentAdapters;
    private List<AppointmentBydateData> appointmentBydateData = null;
    private LinearLayoutManager linearLayoutManager;
    private SessionManager sessionManager;
    private String fromDate, toDate, title;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view_aapointment);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUIId();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            fromDate = extra.getString("FromDate");
            toDate = extra.getString("Todate");
            title = extra.getString("Day");
//            userId = extra.getInt("UserID");
        }

        Tv_Title.setText(title);

        backMenu.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllAppointmentByDateMethod(fromDate, toDate, userId);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllAppointmentByDateMethod(String fromDate, String todate, int userId) {
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
                                if (appointmentBydateData.size() >= 0) {
                                    setDataInRecyclerListMethod(appointmentBydateData);
                                } else {
                                    allAppointmentdata.setVisibility(View.VISIBLE);
                                    appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "Appointment data not found !", Toast.LENGTH_SHORT).show();
                                }
                              /*  if (NetworkStatus.isNetworkAvailable(mContext)) {
                                    getAllAppointmentCountMethod(fromDate, todate);
                                } else {
                                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                }*/
                            } else {
                                allAppointmentdata.setVisibility(View.VISIBLE);
                                appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Appointment data not found !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            allAppointmentdata.setVisibility(View.VISIBLE);
                            appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        allAppointmentdata.setVisibility(View.VISIBLE);
                        appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllAppointmentBydateResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    allAppointmentdata.setVisibility(View.VISIBLE);
                    appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setDataInRecyclerListMethod(List<AppointmentBydateData> appointmentBydateData) {
        try {
            if (appointmentBydateData.size() == 0) {
                allAppointmentdata.setVisibility(View.VISIBLE);
                appointmentDayWeekMonthViewsRc.setVisibility(View.GONE);
            } else {
                allAppointmentdata.setVisibility(View.GONE);
                appointmentDayWeekMonthViewsRc.setVisibility(View.VISIBLE);
                appointmentAdapters = new AppointmentAdapters(mContext, appointmentBydateData);
                linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                appointmentDayWeekMonthViewsRc.setLayoutManager(linearLayoutManager);
                appointmentDayWeekMonthViewsRc.setAdapter(appointmentAdapters);
                appointmentAdapters.notifyDataSetChanged();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllAppointmentCountMethod(String fromDate, String todate) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllAppointmentCountResponse> allAppointmentCountResponseCall = apiClientNetwork.getAllAppointmentCount(fromDate, todate);
            allAppointmentCountResponseCall.enqueue(new Callback<AllAppointmentCountResponse>() {
                @Override
                public void onResponse(Call<AllAppointmentCountResponse> call, Response<AllAppointmentCountResponse> response) {
                    if (response.isSuccessful()) {
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
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllAppointmentCountResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getLayoutUIId() {
        try {
            backMenu = (ImageView) findViewById(R.id.backMenu);
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            allAppointment = (TextView) findViewById(R.id.allAppointment);
            completed = (TextView) findViewById(R.id.completed);
            remaining = (TextView) findViewById(R.id.remaining);
            allAppointmentdata = (TextView) findViewById(R.id.allAppointmentdata);
            appointmentDayWeekMonthViewsRc = (RecyclerView) findViewById(R.id.appointmentDayWeekMonthViewsRc);
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
}
package com.ezpms.ezpmsapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.AvailableDoctorAdapter;
import com.ezpms.ezpmsapp.Appointments.NewAppointmentActivity;
import com.ezpms.ezpmsapp.Appointments.RescheduleCancelActivity;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.PMSSetup.NoticePMSSetupActivity;
import com.ezpms.ezpmsapp.Patients.NewRegistrationActivity;
import com.ezpms.ezpmsapp.Patients.PatientListActivity;
import com.ezpms.ezpmsapp.Patients.PatientRegistrationActivity;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.DoctorAvailableResponse;
import com.ezpms.ezpmsapp.Treatments.NextVisitAppointmentActivity;
import com.ezpms.ezpmsapp.Treatments.TreatmentUpdateActivity;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Context mContext;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageView drawerMenu, syncMenuHead, noticeMenuHead;
    private NavigationView nav_view;
    private View header;
    private RecyclerView availbaleDoctorRc;
    private AvailableDoctorAdapter availableDoctorAdapter;
    private GridLayoutManager layoutManager;
    private LinearLayout dayViews, weekViews, monthView;
    private TextView TvcliniName, userName_tv, notAvailableData_tv;
    private SessionManager sessionManager;
    private List<DoctorAvailableData> doctorAvailableData = null;
    private CountDownTimer CountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();
        getDrawerNavLayout();

        header = nav_view.getHeaderView(0);
        userName_tv = (TextView) header.findViewById(R.id.userName_tv);

        TvcliniName.setText(sessionManager.getUserClinicName());
        userName_tv.setText(sessionManager.getUserFullName());

        nav_view.setNavigationItemSelectedListener(this);
        drawerMenu.setOnClickListener(this);
        syncMenuHead.setOnClickListener(this);
        noticeMenuHead.setOnClickListener(this);
        dayViews.setOnClickListener(this);
        weekViews.setOnClickListener(this);
        monthView.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAvailableDoctorsList(sessionManager.getUserCliniId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
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
                                    notAvailableData_tv.setVisibility(View.GONE);
                                    availbaleDoctorRc.setVisibility(View.VISIBLE);
                                    getRecyclerForAvailableDoctor(doctorAvailableData);
                                } else {
                                    notAvailableData_tv.setVisibility(View.VISIBLE);
                                    availbaleDoctorRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                notAvailableData_tv.setVisibility(View.VISIBLE);
                                availbaleDoctorRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            notAvailableData_tv.setVisibility(View.VISIBLE);
                            availbaleDoctorRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        notAvailableData_tv.setVisibility(View.VISIBLE);
                        availbaleDoctorRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DoctorAvailableResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    notAvailableData_tv.setVisibility(View.VISIBLE);
                    availbaleDoctorRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForAvailableDoctor(List<DoctorAvailableData> doctorAvailableData) {
        try {
            if (doctorAvailableData.size() == 0) {
                notAvailableData_tv.setVisibility(View.VISIBLE);
                availbaleDoctorRc.setVisibility(View.GONE);
            } else {
                notAvailableData_tv.setVisibility(View.GONE);
                availbaleDoctorRc.setVisibility(View.VISIBLE);
                availableDoctorAdapter = new AvailableDoctorAdapter(mContext, doctorAvailableData);
                layoutManager = new GridLayoutManager(mContext, 2);
                availbaleDoctorRc.setLayoutManager(layoutManager);
                availbaleDoctorRc.setAdapter(availableDoctorAdapter);
                availableDoctorAdapter.notifyDataSetChanged();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getDrawerNavLayout() {
        try {
            actionBarDrawerToggle = new ActionBarDrawerToggle((Activity) mContext, drawerLayout, R.string.drawer_open, R.string.drawer_close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerMenu = (ImageView) findViewById(R.id.drawerMenu);
            syncMenuHead = (ImageView) findViewById(R.id.syncMenuHead);
            noticeMenuHead = (ImageView) findViewById(R.id.noticeMenuHead);
            nav_view = (NavigationView) findViewById(R.id.nav_view);
            availbaleDoctorRc = (RecyclerView) findViewById(R.id.availbaleDoctorRc);
            dayViews = (LinearLayout) findViewById(R.id.dayViews);
            weekViews = (LinearLayout) findViewById(R.id.weekViews);
            monthView = (LinearLayout) findViewById(R.id.monthView);
            TvcliniName = (TextView) findViewById(R.id.TvcliniName);
            notAvailableData_tv = (TextView) findViewById(R.id.notAvailableData_tv);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newAppointment:
                startActivity(new Intent(mContext, NewAppointmentActivity.class));
                break;

            case R.id.rescheduleCancel:
                startActivity(new Intent(mContext, RescheduleCancelActivity.class));
                break;

            case R.id.treatmentUpdate:
                startActivity(new Intent(mContext, TreatmentUpdateActivity.class));
                break;

            case R.id.nextVisitAppointment:
                startActivity(new Intent(mContext, NextVisitAppointmentActivity.class));
                break;

            case R.id.newRegistration:
                startActivity(new Intent(mContext, NewRegistrationActivity.class));
//                startActivity(new Intent(mContext, PatientRegistrationActivity.class));
                break;

            case R.id.patientsList:
                startActivity(new Intent(mContext, PatientListActivity.class));
                break;

            case R.id.pMSSetup:
                startActivity(new Intent(mContext, PMSSetupActivity.class));
                break;

            case R.id.nav_logout:
                sessionManager.ClearPreferences();
                startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.drawerMenu:
                    if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    }
                    break;

                case R.id.syncMenuHead:
                    syncingData();
                    break;

                case R.id.noticeMenuHead:
                    startActivity(new Intent(mContext, NoticePMSSetupActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    break;

                case R.id.dayViews:
                    startActivity(new Intent(mContext, DayViewsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    break;

                case R.id.weekViews:
                    startActivity(new Intent(mContext, WeekViewsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    break;

                case R.id.monthView:
                    startActivity(new Intent(mContext, MonthViewsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    break;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void syncingData() {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("please wait, Data is syncing!");
            progressDialog.setCancelable(false);

            CountDownTimer = new CountDownTimer(5000, 1000) {
                public void onTick(long millisUntilFinished) {
                    int seconds = ((int) (millisUntilFinished / 1000)) % 60;
                    progressDialog.show();
                }

                public void onFinish() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    getAlertDialogForSuccess();
                }
            }.start();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAlertDialogForSuccess() {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.update_appoinrments_layout);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_Ok);
            TextView successfully = (TextView) dialog.findViewById(R.id.tv_TitleSuccessfully);
            successfully.setText("Syncing Successfully.");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        getAvailableDoctorsList(sessionManager.getUserCliniId());
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }
}
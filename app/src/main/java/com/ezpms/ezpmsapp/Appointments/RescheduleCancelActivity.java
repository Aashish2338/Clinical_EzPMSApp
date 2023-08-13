package com.ezpms.ezpmsapp.Appointments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.RescheduleCancelAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllAppNoticesData;
import com.ezpms.ezpmsapp.Models.AllAppointmentData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RescheduleCancelActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, textAppointmentsData;
    private ImageView backMenu;
    private EditText et_SearchingByNameNumber;
    private RecyclerView rescheduleCancelRc;
    private RescheduleCancelAdapter rescheduleCancelAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<AllAppointmentData> allAppointmentData = null;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_cancel);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        Tv_Title.setText("Reschedule/Cancel");
        backMenu.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllAppointmentData(sessionManager.getUserCliniId(), "0"); // sessionManager.getLoginUserId()
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        searchAllAppointmentData();
    }

    private void getAllAppointmentData(int userCliniId, String loginUserId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllAppointmentResponse> appointmentResponseCall = apiClientNetwork.getAllAppointment(userCliniId, loginUserId);
            appointmentResponseCall.enqueue(new Callback<AllAppointmentResponse>() {
                @Override
                public void onResponse(Call<AllAppointmentResponse> call, Response<AllAppointmentResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getAllAppointmentData() != null) {
                                allAppointmentData = response.body().getAllAppointmentData();
                                if (allAppointmentData.size() >= 0) {
                                    textAppointmentsData.setVisibility(View.GONE);
                                    rescheduleCancelRc.setVisibility(View.VISIBLE);
                                    getRecyclerForRescheduleCancelAppointment(allAppointmentData);
                                } else {
                                    textAppointmentsData.setVisibility(View.VISIBLE);
                                    rescheduleCancelRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "Not found appointment data!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                textAppointmentsData.setVisibility(View.VISIBLE);
                                rescheduleCancelRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Not found appointment data!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            textAppointmentsData.setVisibility(View.VISIBLE);
                            rescheduleCancelRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        textAppointmentsData.setVisibility(View.VISIBLE);
                        rescheduleCancelRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllAppointmentResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    textAppointmentsData.setVisibility(View.VISIBLE);
                    rescheduleCancelRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForRescheduleCancelAppointment(List<AllAppointmentData> allAppointmentData) {
        try {
            if (allAppointmentData.size() == 0) {
                textAppointmentsData.setVisibility(View.VISIBLE);
                rescheduleCancelRc.setVisibility(View.GONE);
            } else {
                textAppointmentsData.setVisibility(View.GONE);
                rescheduleCancelRc.setVisibility(View.VISIBLE);
                rescheduleCancelAdapter = new RescheduleCancelAdapter(mContext, allAppointmentData);
                linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                rescheduleCancelRc.setLayoutManager(linearLayoutManager);
                rescheduleCancelRc.setAdapter(rescheduleCancelAdapter);
                rescheduleCancelAdapter.notifyDataSetChanged();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void searchAllAppointmentData() {
        try {
            et_SearchingByNameNumber.addTextChangedListener(new TextWatcher() {
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
            List<AllAppointmentData> temp = new ArrayList();
            for (AllAppointmentData patient : allAppointmentData) {
                if (patient.getPatientFirstName().toLowerCase().contains(toString) || patient.getPatientLastName().toLowerCase().contains(toString)
                        || patient.getPatientId().toString().contains(toString)) {
                    temp.add(patient);
                }
            }
            rescheduleCancelAdapter.updateRescheduleCancelList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            textAppointmentsData = (TextView) findViewById(R.id.textAppointmentsData);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            et_SearchingByNameNumber = (EditText) findViewById(R.id.et_SearchingByNameNumber);
            rescheduleCancelRc = (RecyclerView) findViewById(R.id.rescheduleCancelRc);

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
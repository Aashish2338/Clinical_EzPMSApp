package com.ezpms.ezpmsapp.Treatments;

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

import com.ezpms.ezpmsapp.Adapters.TreatmentUpdateAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllTreatmentData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllTreatmentResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreatmentUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, textTreatmentsData;
    private ImageView backMenu;
    private EditText et_SearchingByNameNumberId;
    private RecyclerView treatmentUpdateRc;
    private TreatmentUpdateAdapter treatmentUpdateAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SessionManager sessionManager;
    private List<AllTreatmentData> allTreatmentData = null;
    String Login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_update);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        Tv_Title.setText("Treatment Update");

        backMenu.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            System.out.println("Clinic Id :- " + sessionManager.getUserCliniId());
            getAllTreatment(String.valueOf(sessionManager.getUserCliniId()));// sessionManager.getLoginUserId()
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        getSaerchTreatments();
    }

    private void getAllTreatment(String loginUserId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllTreatmentResponse> treatmentResponseCall = apiClientNetwork.getAllTreatment(loginUserId);
            treatmentResponseCall.enqueue(new Callback<AllTreatmentResponse>() {
                @Override
                public void onResponse(Call<AllTreatmentResponse> call, Response<AllTreatmentResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getAllTreatmentData() != null) {
                                allTreatmentData = response.body().getAllTreatmentData();
                                if (allTreatmentData.size() >= 0) {
                                    textTreatmentsData.setVisibility(View.GONE);
                                    treatmentUpdateRc.setVisibility(View.VISIBLE);
                                    getRecyclerForTreatmentUpdate(allTreatmentData);
                                } else {
                                    textTreatmentsData.setVisibility(View.VISIBLE);
                                    treatmentUpdateRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "Treatment data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                textTreatmentsData.setVisibility(View.VISIBLE);
                                treatmentUpdateRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Treatment data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            textTreatmentsData.setVisibility(View.VISIBLE);
                            treatmentUpdateRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        textTreatmentsData.setVisibility(View.VISIBLE);
                        treatmentUpdateRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllTreatmentResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    textTreatmentsData.setVisibility(View.VISIBLE);
                    treatmentUpdateRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForTreatmentUpdate(List<AllTreatmentData> allTreatmentData) {
        try {
            if (allTreatmentData.size() == 0) {
                textTreatmentsData.setVisibility(View.VISIBLE);
                treatmentUpdateRc.setVisibility(View.GONE);
            } else {
                textTreatmentsData.setVisibility(View.GONE);
                treatmentUpdateRc.setVisibility(View.VISIBLE);
                treatmentUpdateAdapter = new TreatmentUpdateAdapter(mContext, allTreatmentData);
                linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                treatmentUpdateRc.setLayoutManager(linearLayoutManager);
                treatmentUpdateRc.setAdapter(treatmentUpdateAdapter);
                treatmentUpdateAdapter.notifyDataSetChanged();
            }

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getSaerchTreatments() {
        try {
            et_SearchingByNameNumberId.addTextChangedListener(new TextWatcher() {
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
            List<AllTreatmentData> temp = new ArrayList();
            for (AllTreatmentData patient : allTreatmentData) {
                if (patient.getPatientFirstName().toLowerCase().contains(toString) || patient.getPatientLastName().toLowerCase().contains(toString)
                        || patient.getPatientId().toString().contains(toString)) {
                    temp.add(patient);
                }
            }
            treatmentUpdateAdapter.updateTreatmentUpdateList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            textTreatmentsData = (TextView) findViewById(R.id.textTreatmentsData);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            treatmentUpdateRc = (RecyclerView) findViewById(R.id.treatmentUpdateRc);
            et_SearchingByNameNumberId = (EditText) findViewById(R.id.et_SearchingByNameNumberId);

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
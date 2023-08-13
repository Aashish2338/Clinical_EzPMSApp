package com.ezpms.ezpmsapp.Patients;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.PatientListAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.PatientListData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.PatientListResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientListActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, textPatientsData;
    private ImageView backMenu;
    private Button addPatient_Btn;
    private EditText et_SearchingByNameNumberId;
    private RecyclerView patientListRc;
    private PatientListAdapter patientListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SessionManager sessionManager;
    private List<PatientListData> patientListData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        Tv_Title.setText("Patient List");
        backMenu.setOnClickListener(this);
        addPatient_Btn.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getPatientList(sessionManager.getUserCliniId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        getSearchingDataForDownload();
    }

    private void getPatientList(int userCliniId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<PatientListResponse> listResponseCall = apiClientNetwork.getAllPatient(userCliniId);
            listResponseCall.enqueue(new Callback<PatientListResponse>() {
                @Override
                public void onResponse(Call<PatientListResponse> call, Response<PatientListResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getPatientListData() != null) {
                                patientListData = response.body().getPatientListData();
                                if (patientListData.size() >= 0) {
                                    textPatientsData.setVisibility(View.GONE);
                                    patientListRc.setVisibility(View.VISIBLE);
                                    getRecyclerForPatientList(patientListData);
                                } else {
                                    textPatientsData.setVisibility(View.VISIBLE);
                                    patientListRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "Data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                textPatientsData.setVisibility(View.VISIBLE);
                                patientListRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            textPatientsData.setVisibility(View.VISIBLE);
                            patientListRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        textPatientsData.setVisibility(View.VISIBLE);
                        patientListRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, "Patient List not found!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PatientListResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    textPatientsData.setVisibility(View.VISIBLE);
                    patientListRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForPatientList(List<PatientListData> patientListData) {
        try {
            if (patientListData.size() == 0) {
                textPatientsData.setVisibility(View.VISIBLE);
                patientListRc.setVisibility(View.GONE);
            } else {
                textPatientsData.setVisibility(View.GONE);
                patientListRc.setVisibility(View.VISIBLE);
                patientListAdapter = new PatientListAdapter(mContext, patientListData);
                linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                patientListRc.setLayoutManager(linearLayoutManager);
                patientListRc.setAdapter(patientListAdapter);
                patientListAdapter.notifyDataSetChanged();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getSearchingDataForDownload() {
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
            List<PatientListData> temp = new ArrayList();
            for (PatientListData patient : patientListData) {
                if (patient.getFirstName().toLowerCase().contains(toString) || patient.getLastName().toLowerCase().contains(toString)
                        || patient.getPhoneNumber().toLowerCase().contains(toString) || patient.getPatientId().toString().contains(toString)) {
                    temp.add(patient);
                }
            }
            patientListAdapter.updatePatientList(temp);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            textPatientsData = (TextView) findViewById(R.id.textPatientsData);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            patientListRc = (RecyclerView) findViewById(R.id.patientListRc);
            et_SearchingByNameNumberId = (EditText) findViewById(R.id.et_SearchingByNameNumberId);
            addPatient_Btn = (Button) findViewById(R.id.addPatient_Btn);

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

            case R.id.addPatient_Btn:
                startActivity(new Intent(mContext, NewRegistrationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}
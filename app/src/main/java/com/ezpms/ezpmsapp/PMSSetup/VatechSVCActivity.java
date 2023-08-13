package com.ezpms.ezpmsapp.PMSSetup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.VatechSupportAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.SupportData;
import com.ezpms.ezpmsapp.Models.SupportReasonData;
import com.ezpms.ezpmsapp.Models.SupportTypeData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.ResponseModels.SupportReasonResponse;
import com.ezpms.ezpmsapp.ResponseModels.SupportResponse;
import com.ezpms.ezpmsapp.ResponseModels.SupportTypeResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VatechSVCActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, btn_Cancel, textSupportsData;
    private ImageView backMenu;
    private RecyclerView requestedListRc;
    private Spinner supportTypeSpinner;
    private EditText supportDetails_ET, et_SearchingByName;
    private Button btn_AddSupport;
    private VatechSupportAdapter vatechSupportAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SessionManager sessionManager;
    private List<SupportData> supportData = null;

    private int supportId = 0, supportsId;
    private List<SupportTypeData> supportTypeData = null;
    private List<SupportReasonData> supportReasonData = null;
    private String[] supportTypeNameArray;
    private String[] reasonTypeNameArray;
    private String supportTypeName = "", reasonTypeName = "";
    private String supportTypeId;
    private List<String> supportTypeIdList = new ArrayList<>();
//    private List<String> reasonTypeIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vatech_s_v_c);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        Tv_Title.setText("Vatech SVC");

        backMenu.setOnClickListener(this);
        btn_AddSupport.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllUserSupport(sessionManager.getClinicId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        getSearchSupportByReason();

    }

    private void getAllUserSupport(int clinicId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<SupportResponse> supportResponseCall = apiClientNetwork.getAllSUpport(clinicId);
            supportResponseCall.enqueue(new Callback<SupportResponse>() {
                @Override
                public void onResponse(Call<SupportResponse> call, Response<SupportResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getSupportData() != null) {
                                supportData = response.body().getSupportData();
                                if (supportData.size() >= 0) {
                                    textSupportsData.setVisibility(View.GONE);
                                    requestedListRc.setVisibility(View.VISIBLE);
                                    getRecyclerforVatechSupport(supportData);
                                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                                        getSupportTypeLestMethod(supportId);
                                    } else {
                                        textSupportsData.setVisibility(View.VISIBLE);
                                        requestedListRc.setVisibility(View.GONE);
                                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    textSupportsData.setVisibility(View.VISIBLE);
                                    requestedListRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "Support data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                textSupportsData.setVisibility(View.VISIBLE);
                                requestedListRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Support data not found!", Toast.LENGTH_SHORT).show();
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
                        textSupportsData.setVisibility(View.VISIBLE);
                        requestedListRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SupportResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    textSupportsData.setVisibility(View.VISIBLE);
                    requestedListRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerforVatechSupport(List<SupportData> supportData) {
        try {
            if (supportData.size() == 0) {
                textSupportsData.setVisibility(View.VISIBLE);
                requestedListRc.setVisibility(View.GONE);
            } else {
                textSupportsData.setVisibility(View.GONE);
                requestedListRc.setVisibility(View.VISIBLE);
                vatechSupportAdapter = new VatechSupportAdapter(mContext, supportData);
                linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                requestedListRc.setLayoutManager(linearLayoutManager);
                requestedListRc.setAdapter(vatechSupportAdapter);
                vatechSupportAdapter.notifyDataSetChanged();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getSearchSupportByReason() {
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
            List<SupportData> temp = new ArrayList();
            for (SupportData patient : supportData) {
                if (patient.getSupportTypeValue().toLowerCase().contains(toString) || patient.getStrReasonStatus().toLowerCase().contains(toString)) {
                    temp.add(patient);
                }
            }
            vatechSupportAdapter.updateSupportList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getSupportTypeLestMethod(int supportId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<SupportTypeResponse> supportTypeResponseCall = apiClientNetwork.getAllSupportType(supportId);
            supportTypeResponseCall.enqueue(new Callback<SupportTypeResponse>() {
                @Override
                public void onResponse(Call<SupportTypeResponse> call, Response<SupportTypeResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getSupportTypeData() != null) {
                                supportTypeData = response.body().getSupportTypeData();
                                if (supportTypeData.size() >= 0) {
                                    supportTypeNameArray = new String[response.body().getSupportTypeData().size()];
                                    for (int i = 0; i < response.body().getSupportTypeData().size(); i++) {
                                        supportTypeIdList.add(response.body().getSupportTypeData().get(i).getSupportId().toString());
                                        supportTypeNameArray[i] = response.body().getSupportTypeData().get(i).getSupportTypeValue();
                                    }
                                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, supportTypeNameArray);
                                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    supportTypeSpinner.setAdapter(countryAdapter);
                                    supportTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            supportTypeId = supportTypeIdList.get(i);
                                            supportTypeName = supportTypeNameArray[i];
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getAllUserSupportResion();
                                            } else {
                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Not found support type data!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found support type data!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SupportTypeResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllUserSupportResion() {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<SupportReasonResponse> supportReasonResponseCall = apiClientNetwork.getAllSupportResion();
            supportReasonResponseCall.enqueue(new Callback<SupportReasonResponse>() {
                @Override
                public void onResponse(Call<SupportReasonResponse> call, Response<SupportReasonResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getSupportReasonData() != null) {
                                supportReasonData = response.body().getSupportReasonData();
                                if (supportReasonData.size() >= 0) {
                                    reasonTypeNameArray = new String[response.body().getSupportReasonData().size()];
                                    for (int i = 0; i < response.body().getSupportReasonData().size(); i++) {
//                                        reasonTypeIdList.add(response.body().getSupportReasonData().get(i).getId().toString());
                                        reasonTypeNameArray[i] = response.body().getSupportReasonData().get(i).getName();
                                    }
                                    ArrayAdapter<String> reasonAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, reasonTypeNameArray);
                                    reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    reasonTypeSpinner.setAdapter(reasonAdapter);
//                                    reasonTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                            reasonTypeId = reasonTypeIdList.get(i);
//                                            reasonTypeName = reasonTypeNameArray[i];
//                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
//                                                getAllUserSupportResion();
//                                            } else {
//                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                        }
//                                    });
                                } else {
                                    Toast.makeText(mContext, "Reason data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Reason data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SupportReasonResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            textSupportsData = (TextView) findViewById(R.id.textSupportsData);
            btn_Cancel = (TextView) findViewById(R.id.btn_Cancel);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            requestedListRc = (RecyclerView) findViewById(R.id.requestedListRc);
            supportTypeSpinner = (Spinner) findViewById(R.id.supportTypeSpinner);
//            reasonTypeSpinner = (Spinner) findViewById(R.id.reasonTypeSpinner);

            supportDetails_ET = (EditText) findViewById(R.id.supportDetails_ET);
            et_SearchingByName = (EditText) findViewById(R.id.et_SearchingByName);

            btn_AddSupport = (Button) findViewById(R.id.btn_AddSupport);

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

            case R.id.btn_AddSupport:
                if (validSupportDetails()) {
//                    addUsersSupport(supportTypeId, sessionManager.getClinicId(), supportDetails_ET.getText().toString(), reasonTypeId);
                    addUsersSupport(supportTypeId, sessionManager.getClinicId(), supportDetails_ET.getText().toString());
                }
                break;

            case R.id.btn_Cancel:
                clearText();
                break;
        }
    }

    private void addUsersSupport(String supportTypeId, int clinicId, String supportDetail) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> clinicRegisterResponseCall = apiClientNetwork.addSupportDetail(Integer.valueOf(supportTypeId), clinicId, supportDetail,
                    "", "1");
            clinicRegisterResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            supportsId = response.body().getData();
                            Toast.makeText(mContext, "Added support successfully!", Toast.LENGTH_SHORT).show();
                            supportDetails_ET.getText().clear();
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
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClinicRegisterResponse> call, Throwable t) {
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

    private boolean validSupportDetails() {
        if (supportDetails_ET.getText().toString().isEmpty()) {
            supportDetails_ET.setError("Write something here!");
            supportDetails_ET.requestFocus();
            return false;
        }
        return true;
    }

    private void clearText() {
        try {
            supportDetails_ET.getText().clear();
            onBackPressed();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }
}
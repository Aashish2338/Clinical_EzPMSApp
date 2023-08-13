package com.ezpms.ezpmsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.Utilities.StoreDataRegistration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerfiyActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private EditText Et_OTP;
    private TextView resendOTP_TV, login_Btn, btn_Cancel;
    private Button btn_verifyOTP;
    private SessionManager sessionManager;
    private StoreDataRegistration storeDataRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verfiy);
        mContext = this;
        sessionManager = new SessionManager(mContext);
        storeDataRegistration = new StoreDataRegistration(mContext);

        getLayouUiId();

        resendOTP_TV.setOnClickListener(this);
        btn_verifyOTP.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        login_Btn.setOnClickListener(this);
    }

    private void getLayouUiId() {
        try {
            Et_OTP = (EditText) findViewById(R.id.Et_OTP);
            resendOTP_TV = (TextView) findViewById(R.id.resendOTP_TV);
            login_Btn = (TextView) findViewById(R.id.login_Btn);
            btn_Cancel = (TextView) findViewById(R.id.btn_Cancel);
            btn_verifyOTP = (Button) findViewById(R.id.btn_verifyOTP);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resendOTP_TV:
                resendOTPCodeForRegetOTP(storeDataRegistration.getRegUserClinicName(), storeDataRegistration.getRegUserAddressFirst(),
                        storeDataRegistration.getRegUserAddressSecond(), storeDataRegistration.getRegUserCountry(),
                        storeDataRegistration.getRegUserState(), storeDataRegistration.getRegUserCity(),
                        storeDataRegistration.getRegUserPinCode(), storeDataRegistration.getRegUserPackageName(),
                        storeDataRegistration.getRegUserClinicImageUpload(), storeDataRegistration.getRegUserTitle(),
                        storeDataRegistration.getRegUserFirstName(), storeDataRegistration.getRegUserLastName(),
                        storeDataRegistration.getRegUserMaleFemal(), storeDataRegistration.getRegUserEmailAddress(),
                        storeDataRegistration.getRegUserMobileNumber(), storeDataRegistration.getRegUserPassword(),
                        storeDataRegistration.getRegUserPatientRegi(), storeDataRegistration.getRegUserClinicAdmin());
                break;

            case R.id.btn_verifyOTP:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    if (otpVerify()) {
                        verifyingOTP(Et_OTP.getText().toString().trim());
                    }
                } else {
                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_Cancel:
                onBackPressed();
                break;

            case R.id.login_Btn:
                startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }

    private void resendOTPCodeForRegetOTP(String clinicName, String addressFirst, String addressSecond, String country, String state,
                                          String city, String pinCode, String packageName, String clinicImageUpload, String title,
                                          String firstName, String lastName, String maleFemale, String emailAddress, String mobileNumber,
                                          String Password, String patientRegi, String clinicAdmin) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.createAppClinicRegistration(title, firstName, lastName, maleFemale, clinicName,
                    addressFirst, addressSecond, city, state, country, pinCode, "", "", emailAddress, "", "",
                    mobileNumber, patientRegi, packageName, "", "", "", "", clinicImageUpload, Password,
                    "", clinicAdmin);

            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                getAlertDialogForSuccess();
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

    private void getAlertDialogForSuccess() {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.success_layout);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_verifyOTP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void verifyingOTP(String otpNumber) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.getAppVarificationCode(sessionManager.getRegisterCliniId(), otpNumber);
            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Verified Successfull!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
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

    private boolean otpVerify() {
        try {
            if (Et_OTP.getText().toString().trim().isEmpty()) {
                Et_OTP.setError("Enter OTP Number");
                Et_OTP.requestFocus();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }
}
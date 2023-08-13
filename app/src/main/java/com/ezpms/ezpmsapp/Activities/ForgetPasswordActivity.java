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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView btn_Cancel;
    private EditText et_USerEmailIdOrMobile;
    private Button btn_ResetPassword;
    private int userId = 0;
    private String editMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mContext = this;

        getLayoutUiID();

        btn_ResetPassword.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);

    }

    private void getLayoutUiID() {
        try {
            et_USerEmailIdOrMobile = (EditText) findViewById(R.id.et_USerEmailIdOrMobile);
            btn_ResetPassword = (Button) findViewById(R.id.btn_ResetPassword);
            btn_Cancel = (TextView) findViewById(R.id.btn_Cancel);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ResetPassword:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    if (validateEmail()) {
                        forgetPassword(et_USerEmailIdOrMobile.getText().toString().trim(), editMobileNumber);
                    }
                } else {
                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_Cancel:
                break;

        }
    }

    private void forgetPassword(String mobile, String emailId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> call = apiClientNetwork.sendOtp(userId, mobile, emailId);
            call.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            getSuccessfullMsg(String.valueOf(response.body().getData()), mobile, emailId);
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

    private void getSuccessfullMsg(String data, String mobile, String email) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.success_layout);

            TextView Tvlogin_TitleOld = (TextView) dialog.findViewById(R.id.Tvlogin_TitleOld);
            Tvlogin_TitleOld.setText("An OTP has been sent to your registered email address.");

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_verifyOTP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ChangePasswordActivity.class);
                    intent.putExtra("OTP", data);
                    intent.putExtra("Email", email);
                    intent.putExtra("Mobile", mobile);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private boolean validateEmail() {
        try {
            if (et_USerEmailIdOrMobile.getText().toString().isEmpty()) {
                et_USerEmailIdOrMobile.setError("Enter Email Address/Mobile Number");
                et_USerEmailIdOrMobile.requestFocus();
                return false;
            } else if (et_USerEmailIdOrMobile.getText().toString().matches("[0-9]+")) {
                editMobileNumber = et_USerEmailIdOrMobile.getText().toString();
                return true;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }
}
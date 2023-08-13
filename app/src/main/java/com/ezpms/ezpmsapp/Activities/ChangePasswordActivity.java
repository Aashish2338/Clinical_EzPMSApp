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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView btn_Cancel;
    private EditText otpNumber_Et, newPassowrd_Et, confirmPassword_Et;
    private Button btn_ChangePassword;
    private String otp, email, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mContext = this;

        getLayoutUIId();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            otp = extra.getString("OTP");
            email = extra.getString("Email");
            mobile = extra.getString("Mobile");
        } else {
            email = "";
            mobile = "";
        }

        btn_ChangePassword.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);

    }

    private void getLayoutUIId() {
        try {
            otpNumber_Et = (EditText) findViewById(R.id.otpNumber_Et);
            newPassowrd_Et = (EditText) findViewById(R.id.newPassowrd_Et);
            confirmPassword_Et = (EditText) findViewById(R.id.confirmPassword_Et);

            btn_ChangePassword = (Button) findViewById(R.id.btn_ChangePassword);
            btn_Cancel = (TextView) findViewById(R.id.btn_Cancel);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ChangePassword:
                if (validateOTP()) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        changePassword(otpNumber_Et.getText().toString().trim(), newPassowrd_Et.getText().toString().trim(),
                                confirmPassword_Et.getText().toString());
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btn_Cancel:
                break;
        }
    }

    private void changePassword(String otpNumber, String newPassowrd, String confirmPassword) {
       try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> call = apiClientNetwork.getForgotPassword(confirmPassword, 0, email, mobile, 0);
            call.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            getSuccessfullMsg();
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

    private void getSuccessfullMsg() {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.success_layout);

            TextView Tvlogin_TitleOld = (TextView) dialog.findViewById(R.id.Tvlogin_TitleOld);
            Tvlogin_TitleOld.setText("Forget password successfully!");

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_verifyOTP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private boolean validateOTP() {
        try {
            if (otpNumber_Et.getText().toString().isEmpty()) {
                otpNumber_Et.setError("Enter OTP");
                otpNumber_Et.requestFocus();
                return false;
            } else if (newPassowrd_Et.getText().toString().isEmpty()) {
                newPassowrd_Et.setError("Enter new password");
                newPassowrd_Et.requestFocus();
                return false;
            } else if (confirmPassword_Et.getText().toString().isEmpty()) {
                confirmPassword_Et.setError("Enter confirm password");
                confirmPassword_Et.requestFocus();
                return false;
            } else if (!otp.equalsIgnoreCase(otpNumber_Et.getText().toString().trim())) {
                otpNumber_Et.setError("Enter Occur OTP");
                otpNumber_Et.requestFocus();
                return false;
            } else if (!newPassowrd_Et.getText().toString().trim().equalsIgnoreCase(confirmPassword_Et.getText().toString().trim())) {
                Toast.makeText(mContext, "Please enter same password in confirm password like entered new password!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }
}
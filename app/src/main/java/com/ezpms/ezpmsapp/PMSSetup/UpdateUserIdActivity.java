package com.ezpms.ezpmsapp.PMSSetup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Activities.LoginActivity;
import com.ezpms.ezpmsapp.Appointments.UpdateAppointmentsActivity;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserIdActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, Ll_LoginId, Ll_OtherLogin, cancelUser_Btn, btn_Cancel;
    private LinearLayout Ll_OTPLayout;
    private EditText et_EmailIdMobileNumber, oldPassword_Et, newPassowrd_Et, confirmPassword_Et, et_OTPNumber;
    private Button sendOTP_Btn, updateUserId_Btn, btn_ChangePassword, verifyOTP_Btn;
    private ImageView backMenu;
    private SessionManager sessionManager;
    private CountDownTimer CountDownTimer;
    private String editMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_id);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayouUiId();

        Tv_Title.setText("Update Login Id");

        Ll_LoginId.setText("Your Current Login Id is : " + sessionManager.getUserEmail());
        Ll_OtherLogin.setText("Your Other Login Id is : " + sessionManager.getUserPhoneNumber());

        backMenu.setOnClickListener(this);
        sendOTP_Btn.setOnClickListener(this);
        updateUserId_Btn.setOnClickListener(this);
        cancelUser_Btn.setOnClickListener(this);
        btn_ChangePassword.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        verifyOTP_Btn.setOnClickListener(this);

    }

    private void getLayouUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            Ll_LoginId = (TextView) findViewById(R.id.Ll_LoginId);
            Ll_OtherLogin = (TextView) findViewById(R.id.Ll_OtherLogin);
            cancelUser_Btn = (TextView) findViewById(R.id.cancelUser_Btn);
            btn_Cancel = (TextView) findViewById(R.id.btn_Cancel);
            backMenu = (ImageView) findViewById(R.id.backMenu);

            et_EmailIdMobileNumber = (EditText) findViewById(R.id.et_EmailIdMobileNumber);
            oldPassword_Et = (EditText) findViewById(R.id.oldPassword_Et);
            newPassowrd_Et = (EditText) findViewById(R.id.newPassowrd_Et);
            confirmPassword_Et = (EditText) findViewById(R.id.confirmPassword_Et);
            et_OTPNumber = (EditText) findViewById(R.id.et_OTPNumber);

            sendOTP_Btn = (Button) findViewById(R.id.sendOTP_Btn);
            updateUserId_Btn = (Button) findViewById(R.id.updateUserId_Btn);
            btn_ChangePassword = (Button) findViewById(R.id.btn_ChangePassword);
            verifyOTP_Btn = (Button) findViewById(R.id.verifyOTP_Btn);

            Ll_OTPLayout = (LinearLayout) findViewById(R.id.Ll_OTPLayout);

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

            case R.id.sendOTP_Btn:
                if (validateEmailMobile()) {
                    sendOTPNumber(et_EmailIdMobileNumber.getText().toString().trim(), editMobileNumber);
                }
                break;

            case R.id.verifyOTP_Btn:
                if (validateOTP()) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        verifyOTPNumber(et_OTPNumber.getText().toString().trim());
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.updateUserId_Btn:
                if (validateEmailMobile()) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        updateUserId(et_EmailIdMobileNumber.getText().toString().trim());
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.cancelUser_Btn:
                et_EmailIdMobileNumber.getText().clear();
                break;

            case R.id.btn_ChangePassword:
                if (validatePassword()) {
                    changePassword(oldPassword_Et.getText().toString().trim(), newPassowrd_Et.getText().toString().trim(),
                            confirmPassword_Et.getText().toString());
                }
                break;

            case R.id.btn_Cancel:
                oldPassword_Et.getText().clear();
                newPassowrd_Et.getText().clear();
                confirmPassword_Et.getText().clear();
                finish();
                break;
        }
    }

    private void verifyOTPNumber(String otpNumber) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
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
                    if (otpNumber.equalsIgnoreCase(sessionManager.getOTP())) {
                        Ll_OTPLayout.setVisibility(View.GONE);
                        getAlertDialogForSuccess();
                    } else {
                        Ll_OTPLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, "Please try again!", Toast.LENGTH_SHORT).show();
                    }
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
            dialog.setContentView(R.layout.treatment_updates_layouts);

            TextView tv_TitleSuccessfully = (TextView) dialog.findViewById(R.id.tv_TitleSuccessfully);
            tv_TitleSuccessfully.setText("OTP verify successfully");

            Button btn_Yes = (Button) dialog.findViewById(R.id.btn_Yes);
            TextView btn_No = (TextView) dialog.findViewById(R.id.btn_No);
            btn_No.setVisibility(View.GONE);

            btn_Yes.setOnClickListener(new View.OnClickListener() {
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

    private boolean validateOTP() {
        try {
            if (et_OTPNumber.getText().toString().isEmpty()) {
                et_OTPNumber.setError("Please enter OTP Number");
                et_OTPNumber.requestFocus();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }

    private void updateUserId(String loginId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.updateUserIDDetail(sessionManager.getLoginUserId(),/* sessionManager.getUserEmail()*/loginId,
                    sessionManager.getUserPhoneNumber(), sessionManager.getUserFullName(), sessionManager.getLoginUserId());
            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "User Id Updated Successfully!", Toast.LENGTH_SHORT).show();
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

    private void changePassword(String oldPassword, String newPassowrd, String confirmPassword) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> clinicRegisterResponseCall = apiClientNetwork.changePasswordDetail(sessionManager.getLoginUserId(),
                    sessionManager.getUserEmail(), sessionManager.getUserPhoneNumber(), oldPassword, newPassowrd, "");
            clinicRegisterResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Password Updated successfully!", Toast.LENGTH_SHORT).show();
                            sessionManager.ClearPreferences();
                            startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
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

    private boolean validatePassword() {
        try {
            if (oldPassword_Et.getText().toString().isEmpty()) {
                oldPassword_Et.setError("Enter old password");
                oldPassword_Et.requestFocus();
                return false;
            } else if (newPassowrd_Et.getText().toString().isEmpty()) {
                newPassowrd_Et.setError("Enter new password");
                newPassowrd_Et.requestFocus();
                return false;
            } else if (confirmPassword_Et.getText().toString().isEmpty()) {
                confirmPassword_Et.setError("Enter confirm password");
                confirmPassword_Et.requestFocus();
                return false;
            } else if (!newPassowrd_Et.getText().toString().equalsIgnoreCase(confirmPassword_Et.getText().toString())) {
                Toast.makeText(mContext, "Please enter same password in confirm password like entered new password!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return false;
    }

    private void sendOTPNumber(String mailId, String mobileNumber) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> clinicRegisterResponseCall = apiClientNetwork.sendOtp(Integer.valueOf(sessionManager.getLoginUserId()),
                    mobileNumber, mailId);
            clinicRegisterResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            sessionManager.setOTP(String.valueOf(response.body().getData()));
                            getSuccessDialog();
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

    private void getSuccessDialog() {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.treatment_updates_layouts);

            TextView tv_TitleSuccessfully = (TextView) dialog.findViewById(R.id.tv_TitleSuccessfully);
            tv_TitleSuccessfully.setText("An OTP has been sent to your provided Mobile Number.");

            Button btn_Yes = (Button) dialog.findViewById(R.id.btn_Yes);
            TextView btn_No = (TextView) dialog.findViewById(R.id.btn_No);
            btn_No.setVisibility(View.GONE);

            btn_Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ll_OTPLayout.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private boolean validateEmailMobile() {
        try {
            if (et_EmailIdMobileNumber.getText().toString().isEmpty()) {
                et_EmailIdMobileNumber.setError("Enter Email Address/Mobile Number");
                et_EmailIdMobileNumber.requestFocus();
                return false;
            } else if (et_EmailIdMobileNumber.getText().toString().matches("[0-9]+")) {
                editMobileNumber = et_EmailIdMobileNumber.getText().toString();
                return true;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }
}
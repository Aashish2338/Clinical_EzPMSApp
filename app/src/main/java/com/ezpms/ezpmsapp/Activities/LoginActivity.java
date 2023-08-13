package com.ezpms.ezpmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.LoginData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.LoginResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.CommonUtils;
import com.ezpms.ezpmsapp.Utilities.ConfigurationData;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btn_login;
    private TextView TvToRegister, TvForgotPassword;
    private EditText Et_LoginId, Et_Password;
    private CheckBox Chk_RememberMe;
    private ImageView show_pass_btn;
    private String rememberMe;
    private boolean isPasswordVisible;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        if (CommonUtils.getPreferenceBoolean(mContext, ConfigurationData.RememberMe)) {
            Chk_RememberMe.setChecked(true);
            Et_LoginId.setText(CommonUtils.getPreferenceString(mContext, ConfigurationData.RememberMail));
            Et_Password.setText(CommonUtils.getPreferenceString(mContext, ConfigurationData.RememberPassword));
        }

        btn_login.setOnClickListener(this);
        TvToRegister.setOnClickListener(this);
        TvForgotPassword.setOnClickListener(this);
        show_pass_btn.setOnClickListener(this);
    }

    private void getLayoutUiId() {
        try {
            btn_login = (Button) findViewById(R.id.btn_login);
            TvToRegister = (TextView) findViewById(R.id.TvToRegister);
            TvForgotPassword = (TextView) findViewById(R.id.TvForgotPassword);
            Et_LoginId = (EditText) findViewById(R.id.Et_LoginId);
            Et_Password = (EditText) findViewById(R.id.Et_Password);
            Chk_RememberMe = (CheckBox) findViewById(R.id.Chk_RememberMe);
            show_pass_btn = (ImageView) findViewById(R.id.show_pass_btn);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    if (validateData()) {
                        CommonUtils.savePreferenceBoolean(mContext, ConfigurationData.IsLogin, true);
                        setRememberMe();
                        getLoginData(Et_LoginId.getText().toString().trim(), Et_Password.getText().toString().trim());
                    }
                } else {
                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.TvToRegister:
                startActivity(new Intent(mContext, ClinitRegistrationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;

            case R.id.TvForgotPassword:
                startActivity(new Intent(mContext, ForgetPasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;

            case R.id.show_pass_btn:
                if (isPasswordVisible) {
                    String pass = Et_Password.getText().toString();
                    Et_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Et_Password.setInputType(InputType.TYPE_CLASS_TEXT);
                    Et_Password.setText(pass);
                    Et_Password.setSelection(pass.length());
//                    show_pass_btn.setImageResource(R.drawable.eye);
                } else {
                    String pass = Et_Password.getText().toString();
                    Et_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Et_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Et_Password.setText(pass);
                    Et_Password.setSelection(pass.length());
//                    show_pass_btn.setImageResource(R.drawable.eye_black_hide);
                }
                isPasswordVisible = !isPasswordVisible;
                break;
        }
    }

    private void setRememberMe() {
        try {
            Chk_RememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        rememberMe = "1";
                        Chk_RememberMe.setChecked(true);
                        CommonUtils.savePreferenceBoolean(mContext, ConfigurationData.RememberMe, true);
                        CommonUtils.savePreferenceString(mContext, ConfigurationData.RememberMail, Et_LoginId.getText().toString().trim());
                        CommonUtils.savePreferenceString(mContext, ConfigurationData.RememberPassword, Et_Password.getText().toString().trim());
                    } else {
                        rememberMe = "0";
                        Chk_RememberMe.setChecked(false);
                        CommonUtils.savePreferenceBoolean(mContext, ConfigurationData.RememberMe, false);
                        CommonUtils.savePreferenceString(mContext, ConfigurationData.RememberMail, "");
                        CommonUtils.savePreferenceString(mContext, ConfigurationData.RememberPassword, "");
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLoginData(String emailId, String password) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<LoginResponse> stringCall = apiClientNetwork.getUsersAccLogin(emailId, password);
            stringCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getLoginData() != null) {
                                Toast.makeText(mContext, "Login successfull!", Toast.LENGTH_SHORT).show();
                                setDataInSession(response.body().getLoginData());
                                if (sessionManager.getLogInDirection().equals("start")) {
                                    sessionManager.setLogInDirection("inside");
                                    startActivity(new Intent(mContext, HomeActivity.class));
                                    finish();
                                } else {
                                    sessionManager.setLogInDirection("inside");
                                    finish();
                                }
                            } else {
                                Toast.makeText(mContext, "Login data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Login fail, please try again!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Please correct userId and password!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
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

    private void setDataInSession(LoginData loginData) {
        try {
            sessionManager.setLoginUserId(loginData.getUserId().toString());
            sessionManager.setUserFirstName(loginData.getFirstName());
            sessionManager.setUserLastName(loginData.getLastName());
            sessionManager.setUserFullName(loginData.getFirstName() + " " + loginData.getLastName());
            sessionManager.setUserEmail(loginData.getEmail());
            sessionManager.setUserPassword(loginData.getEncryptedPassword());
            sessionManager.setUserPhoneNumber(loginData.getPhoneNumber());
            sessionManager.setUserClinicId(loginData.getClinicId());
            sessionManager.setUserClinicName(loginData.getClinicName());
            if (loginData.getClinicAddress1() != null) {
                sessionManager.setUserClinicAddressFi(loginData.getClinicAddress1());
            } else {
                sessionManager.setUserClinicAddressFi(" ");
            }
            if (loginData.getClinicAddress2() != null) {
                sessionManager.setUserClinicAddressSe(loginData.getClinicAddress2());
            } else {
                sessionManager.setUserClinicAddressSe(" ");
            }
            sessionManager.setUserImage(loginData.getAvatarimage());
            if (loginData.getClAdminFirstName() != null) {
                sessionManager.setAdminFirstName(loginData.getClAdminFirstName().toString());
            } else {
                sessionManager.setAdminFirstName(" ");
            }
            if (loginData.getClAdminLastName() != null) {
                sessionManager.setAdminLastName(loginData.getClAdminLastName().toString());
            } else {
                sessionManager.setAdminLastName(" ");
            }
            if (loginData.getClAdminLastName() != null) {
                sessionManager.setAdminLastName(loginData.getClAdminLastName().toString());
            } else {
                sessionManager.setAdminLastName(" ");
            }
            if (loginData.getClAdminGender() != null) {
                sessionManager.setAdminGender(loginData.getClAdminGender().toString());
            } else {
                sessionManager.setAdminGender(" ");
            }
            if (loginData.getAddress() != null) {
                sessionManager.setAddress(loginData.getAddress().toString());
            } else {
                sessionManager.setAddress(" ");
            }
            sessionManager.setPostalCode(loginData.getPostalcode());
            sessionManager.setIsDoctorClinicAdmin(loginData.getDoctorClinicAdmin());
            sessionManager.setUserGender(loginData.getGender());
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private boolean validateData() {
        try {
            if (Et_LoginId.getText().toString().isEmpty()) {
                Et_LoginId.setError("Enter Email Id");
                Et_LoginId.requestFocus();
                return false;
            } else if (Et_Password.getText().toString().isEmpty()) {
                Et_Password.setError("Enter Password");
                Et_Password.requestFocus();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }
}
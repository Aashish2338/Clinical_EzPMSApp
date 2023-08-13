package com.ezpms.ezpmsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllTitleData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllTitleResponse;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, cancel_Btn;
    private ImageView backMenu;
    private Spinner titleSpinner;
    private EditText userType_Et, firstName_Et, lastName_Et, Et_CountryCode, usersId_Et, Et_Password;
    private CheckBox maleCheck_Bt, femaleCheck_Bt;
    private Switch activeDeactive;
    private Button update_Btn;
    private SessionManager sessionManager;
    private List<AllTitleData> allTitleData = null;
    private String maleFemale, titleId, titleName = "", emailId, gender;
    private int isActive, roleId, userId;
    private String[] titleNameArray;
    private List<String> titleIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayouUiId();
        getMailFemaleActiveCheckBox();

        Tv_Title.setText("User Update");
        backMenu.setOnClickListener(this);
        update_Btn.setOnClickListener(this);
        cancel_Btn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userType_Et.setText("Doctor");
            userType_Et.setClickable(false);
            userType_Et.setEnabled(false);
            firstName_Et.setText(extras.getString("FirstName"));
            lastName_Et.setText(extras.getString("LastName"));
            usersId_Et.setText(extras.getString("PhoneNumber"));
            usersId_Et.setClickable(false);
            usersId_Et.setEnabled(false);
            Et_CountryCode.setText("+91");
            Et_CountryCode.setEnabled(false);
            Et_CountryCode.setClickable(false);
            Et_Password.setText(extras.getString("Password"));
            Et_Password.setClickable(false);
            Et_Password.setEnabled(false);
            emailId = extras.getString("Email");
            gender = extras.getString("Gender");
            isActive = extras.getInt("IsActive");
            roleId = extras.getInt("RoleId");
            userId = extras.getInt("UserId");
        }

        checkMaleActiveData();

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllUserTitle();
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkMaleActiveData() {
        try {
            if (isActive == 0) {
                activeDeactive.setChecked(false);
                activeDeactive.setClickable(false);
                activeDeactive.setEnabled(false);
                activeDeactive.setText("Off");
            } else {
                activeDeactive.setChecked(true);
                activeDeactive.setClickable(false);
                activeDeactive.setEnabled(false);
                activeDeactive.setText("On");
            }

            if (gender.equalsIgnoreCase("Male")) {
                maleFemale = "Male";
                maleCheck_Bt.setEnabled(true);
                maleCheck_Bt.setChecked(true);
                femaleCheck_Bt.setChecked(false);
            } else {
                maleFemale = "Female";
                femaleCheck_Bt.setEnabled(true);
                femaleCheck_Bt.setChecked(true);
                maleCheck_Bt.setChecked(false);
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllUserTitle() {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllTitleResponse> titleResponseCall = apiClientNetwork.getAllTitle();
            titleResponseCall.enqueue(new Callback<AllTitleResponse>() {
                @Override
                public void onResponse(Call<AllTitleResponse> call, Response<AllTitleResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getAllTitleData() != null) {
                                allTitleData = response.body().getAllTitleData();
                                if (allTitleData.size() >= 0) {
                                    titleNameArray = new String[response.body().getAllTitleData().size()];
                                    for (int i = 0; i < response.body().getAllTitleData().size(); i++) {
                                        titleIdList.add(response.body().getAllTitleData().get(i).getTitleID().toString());
                                        titleNameArray[i] = response.body().getAllTitleData().get(i).getTitleName();
                                    }
                                    ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, titleNameArray);
                                    titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    titleSpinner.setAdapter(titleAdapter);
                                    titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            titleId = titleIdList.get(i);
                                            titleName = titleNameArray[i];
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Title data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Title data not found!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<AllTitleResponse> call, Throwable t) {
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

    private void getLayouUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            cancel_Btn = (TextView) findViewById(R.id.cancel_Btn);
            backMenu = (ImageView) findViewById(R.id.backMenu);

            titleSpinner = (Spinner) findViewById(R.id.titleSpinner);

            firstName_Et = (EditText) findViewById(R.id.firstName_Et);
            lastName_Et = (EditText) findViewById(R.id.lastName_Et);
            Et_CountryCode = (EditText) findViewById(R.id.Et_CountryCode);
            usersId_Et = (EditText) findViewById(R.id.usersId_Et);
            Et_Password = (EditText) findViewById(R.id.Et_Password);
            userType_Et = (EditText) findViewById(R.id.userType_Et);

            maleCheck_Bt = (CheckBox) findViewById(R.id.maleCheck_Bt);
            femaleCheck_Bt = (CheckBox) findViewById(R.id.femaleCheck_Bt);

            activeDeactive = (Switch) findViewById(R.id.activeDeactive);
            update_Btn = (Button) findViewById(R.id.update_Btn);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getMailFemaleActiveCheckBox() {
        try {
            maleCheck_Bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        maleFemale = "Male";
                        femaleCheck_Bt.setChecked(false);
                        maleCheck_Bt.setChecked(true);
                    }
                }
            });

            femaleCheck_Bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        maleFemale = "Female";
                        maleCheck_Bt.setChecked(false);
                        femaleCheck_Bt.setChecked(true);
                    }
                }
            });

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

            case R.id.update_Btn:
                if (validateUser()) {
                    updateUserProfile(titleId, firstName_Et.getText().toString().trim(), lastName_Et.getText().toString().trim(),
                            maleFemale, Et_CountryCode.getText().toString().trim(), usersId_Et.getText().toString().trim(), Et_Password.getText().toString());
                }
                break;

            case R.id.cancel_Btn:
                onBackPressed();
                break;
        }
    }

    private boolean validateUser() {
        try {
            if (firstName_Et.getText().toString().isEmpty()) {
                firstName_Et.setError("Enter first name!");
                firstName_Et.requestFocus();
                return false;
            } else if (lastName_Et.getText().toString().isEmpty()) {
                lastName_Et.setError("Enter last name!");
                lastName_Et.requestFocus();
                return false;
            } else if (Et_CountryCode.getText().toString().isEmpty()) {
                Et_CountryCode.setError("Enter country code!");
                Et_CountryCode.requestFocus();
                return false;
            } else if (usersId_Et.getText().toString().isEmpty()) {
                usersId_Et.setError("Enter user Id!");
                usersId_Et.requestFocus();
                return false;
            } else if (Et_Password.getText().toString().isEmpty()) {
                Et_Password.setError("Enter password!");
                Et_Password.requestFocus();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }

    private void updateUserProfile(String titleId, String firstName, String lastName, String maleFemale, String countryCode,
                                   String usersId, String password) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String phoneNumber = countryCode + "-" + usersId;

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> clinicRegisterResponseCall = apiClientNetwork.updateUserProfile(userId, firstName, lastName, maleFemale, roleId,
                    titleId, emailId, phoneNumber, "", "", "", "", "", "", "");
            clinicRegisterResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "User updated successfully!", Toast.LENGTH_SHORT).show();
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
}
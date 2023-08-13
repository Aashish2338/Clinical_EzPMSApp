package com.ezpms.ezpmsapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllTitleData;
import com.ezpms.ezpmsapp.Models.CityData;
import com.ezpms.ezpmsapp.Models.CountryData;
import com.ezpms.ezpmsapp.Models.PackageData;
import com.ezpms.ezpmsapp.Models.StateData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllTitleResponse;
import com.ezpms.ezpmsapp.ResponseModels.CityResponse;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.ResponseModels.CountryResponse;
import com.ezpms.ezpmsapp.ResponseModels.PackagesResponse;
import com.ezpms.ezpmsapp.ResponseModels.StateResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.Utilities.StoreDataRegistration;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinitRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView clinicName_TvM, addressFirst_TvM, addressSecond_TvM, packageName_TvM, title_TvM;
    private TextView firstName_TvM, lastName_TvM, emailAddress_TvM, patientId_TvM;
    private int countryID = 0, statesId = 0, citiesId = 0, packageId = 0;
    private List<CountryData> countryData = null;
    private List<StateData> stateData = null;
    private List<CityData> cityData = null;
    private List<PackageData> packageData = null;
    private List<AllTitleData> allTitleData = null;
    private Spinner countrySpinner, stateSpinner, citySpinner, titleSpinner, packageSpinner;
    private String[] countryNameArray;
    private String[] stateNameArray;
    private String[] cityNameArray;
    private String[] packageNameArray;
    private String[] allTitleNameArray;
    private String countryName = "", stateName = "", cityName = "", packageName = "", allTitleName = "";
    private String countryId, stateid, cityId, packagesId, titleId, allTitleId;
    private String maleFemale, clinicAdmin, clinicImageUpload;
    private List<String> CountryIdList = new ArrayList<>();
    private List<String> StateIdList = new ArrayList<>();
    private List<String> CityIdlist = new ArrayList<>();
    private List<String> PackageIdlist = new ArrayList<>();
    private List<String> allTitleIdlist = new ArrayList<>();

    private EditText clinicName_Et, addressFirst_Et, addressSecond_Et, pinCode_Et;
    private EditText firstName_Et, lastName_Et, emailAddress_Et, mobileNumber_Et, Et_Password, patientRegi_Et;
    private TextView TvAlreadyAccout;
    private LinearLayout uploadImage_Btn;
    private Button btn_CreateAccount;
    private ImageView clinicImageUpload_Im;
    private CheckBox maleCheck_Bt, femaleCheck_Bt, Chk_ClinicAdmin;

    private static final int MY_CAMERA_PERMISSION_CODE = 10;
    private SessionManager sessionManager;
    private StoreDataRegistration storeDataRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinit_registration);
        mContext = this;
        sessionManager = new SessionManager(mContext);
        storeDataRegistration = new StoreDataRegistration(mContext);

        getLayoutUiId();
        getTextLayoutUiId();
        setTextMandatoryLayout();
        getMailFemaleAmdinCheckBox();

        patientRegi_Et.setFocusable(false);
        patientRegi_Et.setEnabled(false);
        patientRegi_Et.setClickable(false);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllCountryList(countryID);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        String styledText = "Already a member? <font color='blue'>Log in</font>";
        TvAlreadyAccout.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);

        btn_CreateAccount.setOnClickListener(this);
        uploadImage_Btn.setOnClickListener(this);
        TvAlreadyAccout.setOnClickListener(this);
        Chk_ClinicAdmin.setOnClickListener(this);
    }

    private void getTextLayoutUiId() {
        try {
            title_TvM = (TextView) findViewById(R.id.title_TvM);
            patientId_TvM = (TextView) findViewById(R.id.patientId_TvM);
            firstName_TvM = (TextView) findViewById(R.id.firstName_TvM);
            lastName_TvM = (TextView) findViewById(R.id.lastName_TvM);
            addressFirst_TvM = (TextView) findViewById(R.id.addressFirst_TvM);
            addressSecond_TvM = (TextView) findViewById(R.id.addressSecond_TvM);
            clinicName_TvM = (TextView) findViewById(R.id.clinicName_TvM);
            packageName_TvM = (TextView) findViewById(R.id.packageName_TvM);
            emailAddress_TvM = (TextView) findViewById(R.id.emailAddress_TvM);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setTextMandatoryLayout() {
        try {
            String clinicName_Tv = "Clinic Name<font color='red'>*</font>";
            clinicName_TvM.setText(Html.fromHtml(clinicName_Tv), TextView.BufferType.SPANNABLE);

            String addressFirst_Tv = "Address 1<font color='red'>*</font>";
            addressFirst_TvM.setText(Html.fromHtml(addressFirst_Tv), TextView.BufferType.SPANNABLE);

            String addressSecond_Tv = "Address 2<font color='red'>*</font>";
            addressSecond_TvM.setText(Html.fromHtml(addressSecond_Tv), TextView.BufferType.SPANNABLE);

            String packageName_Tv = "Package Name<font color='red'>*</font>";
            packageName_TvM.setText(Html.fromHtml(packageName_Tv), TextView.BufferType.SPANNABLE);

            String title_Tv = "Title<font color='red'>*</font>";
            title_TvM.setText(Html.fromHtml(title_Tv), TextView.BufferType.SPANNABLE);

            String firstName_Tv = "First Name<font color='red'>*</font>";
            firstName_TvM.setText(Html.fromHtml(firstName_Tv), TextView.BufferType.SPANNABLE);

            String lastName_Tv = "Last Name<font color='red'>*</font>";
            lastName_TvM.setText(Html.fromHtml(lastName_Tv), TextView.BufferType.SPANNABLE);

            String emailAddress_Tv = "Email Address<font color='red'>*</font>";
            emailAddress_TvM.setText(Html.fromHtml(emailAddress_Tv), TextView.BufferType.SPANNABLE);

            String patientId_Tv = "Patient Reg. Initial ID<font color='red'>*</font>";
            patientId_TvM.setText(Html.fromHtml(patientId_Tv), TextView.BufferType.SPANNABLE);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getMailFemaleAmdinCheckBox() {
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

            Chk_ClinicAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        clinicAdmin = "1";
//                        Toast.makeText(mContext, clinicAdmin, Toast.LENGTH_SHORT).show();
                        Chk_ClinicAdmin.setChecked(true);
                    } else {
                        clinicAdmin = "0";
//                        Toast.makeText(mContext, clinicAdmin, Toast.LENGTH_SHORT).show();
                        Chk_ClinicAdmin.setChecked(false);
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
            stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
            citySpinner = (Spinner) findViewById(R.id.citySpinner);
            titleSpinner = (Spinner) findViewById(R.id.titleSpinner);
            packageSpinner = (Spinner) findViewById(R.id.packageSpinner);

            clinicName_Et = (EditText) findViewById(R.id.clinicName_Et);
            addressFirst_Et = (EditText) findViewById(R.id.addressFirst_Et);
            addressSecond_Et = (EditText) findViewById(R.id.addressSecond_Et);
            pinCode_Et = (EditText) findViewById(R.id.pinCode_Et);
            firstName_Et = (EditText) findViewById(R.id.firstName_Et);
            lastName_Et = (EditText) findViewById(R.id.lastName_Et);
            emailAddress_Et = (EditText) findViewById(R.id.emailAddress_Et);
            mobileNumber_Et = (EditText) findViewById(R.id.mobileNumber_Et);
            Et_Password = (EditText) findViewById(R.id.Et_Password);
            patientRegi_Et = (EditText) findViewById(R.id.patientRegi_Et);

            clinicImageUpload_Im = (ImageView) findViewById(R.id.clinicImageUpload_Im);
            uploadImage_Btn = (LinearLayout) findViewById(R.id.uploadImage_Btn);
            TvAlreadyAccout = (TextView) findViewById(R.id.TvAlreadyAccout);

            btn_CreateAccount = (Button) findViewById(R.id.btn_CreateAccount);

            maleCheck_Bt = (CheckBox) findViewById(R.id.maleCheck_Bt);
            femaleCheck_Bt = (CheckBox) findViewById(R.id.femaleCheck_Bt);
            Chk_ClinicAdmin = (CheckBox) findViewById(R.id.Chk_ClinicAdmin);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllCountryList(int countryID) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiInterface = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<CountryResponse> CountryResponseCall = apiInterface.getCountryList(countryID);
            CountryResponseCall.enqueue(new Callback<CountryResponse>() {
                @Override
                public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getData() != null) {
                                countryData = response.body().getData();
                                if (countryData.size() >= 0) {
                                    countryNameArray = new String[response.body().getData().size()];
                                    for (int i = 0; i < response.body().getData().size(); i++) {
                                        CountryIdList.add(response.body().getData().get(i).getCountryId().toString());
                                        countryNameArray[i] = response.body().getData().get(i).getCountryName();
                                    }
                                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, countryNameArray);
                                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrySpinner.setAdapter(countryAdapter);
                                    countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            countryId = CountryIdList.get(i);
                                            countryName = countryNameArray[i];
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getStateListMethod(countryId);
                                            } else {
                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Not found any country!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found any country!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CountryResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getStateListMethod(String countryId) {
        try {
            ApiClientNetwork apiInterface = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<StateResponse> StateResponseCall = apiInterface.getAllStateName(Integer.valueOf(countryId), statesId);
            StateResponseCall.enqueue(new Callback<StateResponse>() {
                @Override
                public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getStateData() != null) {
                                stateData = response.body().getStateData();
                                if (stateData.size() >= 0) {
                                    stateNameArray = new String[response.body().getStateData().size()];
                                    for (int i = 0; i < response.body().getStateData().size(); i++) {
                                        StateIdList.add(response.body().getStateData().get(i).getStateId().toString());
                                        stateNameArray[i] = response.body().getStateData().get(i).getStateName();
                                    }
                                    ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, stateNameArray);
                                    stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    stateSpinner.setAdapter(stateAdapter);
                                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            stateid = StateIdList.get(i);
                                            stateName = stateNameArray[i];
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getCityListMethod(stateid);
                                            } else {
                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Not found any state!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found any state!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StateResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getCityListMethod(String stateid) {
        try {
            ApiClientNetwork apiInterface = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<CityResponse> CityResponseCall = apiInterface.getAllCityName(Integer.valueOf(stateid), citiesId);
            CityResponseCall.enqueue(new Callback<CityResponse>() {
                @Override
                public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getCityData() != null) {
                                cityData = response.body().getCityData();
                                if (cityData.size() >= 0) {
                                    cityNameArray = new String[response.body().getCityData().size()];
                                    for (int i = 0; i < response.body().getCityData().size(); i++) {
                                        CityIdlist.add(response.body().getCityData().get(i).getCityId().toString());
                                        cityNameArray[i] = response.body().getCityData().get(i).getCityName();
                                        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, cityNameArray);
                                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        citySpinner.setAdapter(cityAdapter);
                                        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                cityId = CityIdlist.get(i);
                                                cityName = cityNameArray[i];
                                                if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                    getPackageNameMethod(packageId);
                                                } else {
                                                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(mContext, "Not found any city!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found any city!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getPackageNameMethod(int packageId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<PackagesResponse> packagesResponseCall = apiClientNetwork.getAllPackages(packageId);
            packagesResponseCall.enqueue(new Callback<PackagesResponse>() {
                @Override
                public void onResponse(Call<PackagesResponse> call, Response<PackagesResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getPackageData() != null) {
                                packageData = response.body().getPackageData();
                                if (packageData.size() >= 0) {
                                    packageNameArray = new String[response.body().getPackageData().size()];
                                    for (int i = 0; i < response.body().getPackageData().size(); i++) {
                                        PackageIdlist.add(response.body().getPackageData().get(i).getPackageId().toString());
                                        packageNameArray[i] = response.body().getPackageData().get(i).getPackageName();
                                        ArrayAdapter<String> packageAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, packageNameArray);
                                        packageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        packageSpinner.setEnabled(false);
                                        packageSpinner.setClickable(false);
                                        packageSpinner.setAdapter(packageAdapter);
                                        packageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                packagesId = CityIdlist.get(i);
                                                packageName = cityNameArray[i];
                                                if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                    getAllTitleListMethod();
                                                } else {
                                                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(mContext, "Not found any package!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found any package!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PackagesResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllTitleListMethod() {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllTitleResponse> titleResponseCall = apiClientNetwork.getAllTitle();
            titleResponseCall.enqueue(new Callback<AllTitleResponse>() {
                @Override
                public void onResponse(Call<AllTitleResponse> call, Response<AllTitleResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getAllTitleData() != null) {
                                allTitleData = response.body().getAllTitleData();
                                if (allTitleData.size() >= 0) {
                                    allTitleNameArray = new String[response.body().getAllTitleData().size()];
                                    for (int i = 0; i < response.body().getAllTitleData().size(); i++) {
                                        allTitleIdlist.add(response.body().getAllTitleData().get(i).getTitleID().toString());
                                        allTitleNameArray[i] = response.body().getAllTitleData().get(i).getTitleName();
                                        ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, allTitleNameArray);
                                        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        titleSpinner.setAdapter(titleAdapter);
                                        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                allTitleId = allTitleIdlist.get(i);
                                                allTitleName = allTitleNameArray[i];
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(mContext, "Title data not found !", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Title data not found !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllTitleResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_CreateAccount:
                if (validateRegisterData()) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        createClinicRegister(clinicName_Et.getText().toString().trim(), addressFirst_Et.getText().toString().trim(),
                                addressSecond_Et.getText().toString().trim(), countryId, stateid, cityId, pinCode_Et.getText().toString().trim(),
                                packagesId, clinicImageUpload, allTitleId, firstName_Et.getText().toString().trim(), lastName_Et.getText().toString().trim(), maleFemale,
                                emailAddress_Et.getText().toString().trim(), mobileNumber_Et.getText().toString().trim(), Et_Password.getText().toString().trim(),
                                patientRegi_Et.getText().toString().trim(), clinicAdmin);
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.TvAlreadyAccout:
                startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;

            case R.id.uploadImage_Btn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        someActivityResultLauncher.launch(cameraIntent);
                    }
                }
                break;
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap image = (Bitmap) data.getExtras().get("data");
                        clinicImageUpload_Im.setImageBitmap(image);
                        clinicImageUpload = getEncodedString(image);
                    }
                }
            });

    private void createClinicRegister(String clinicName, String addressFirst, String addressSecond, String country, String state,
                                      String city, String pinCode, String packageName, String clinicImageUpload, String title,
                                      String firstName, String lastName, String maleFemale, String emailAddress, String mobileNumber,
                                      String Password, String patientRegi, String clinicAdmin) {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        System.out.println("clinicName :- " + clinicName);
//        System.out.println("addressFirst :- " + addressFirst);
//        System.out.println("addressSecond :- " + addressSecond);
//        System.out.println("country :- " + country);
//        System.out.println("state :- " + state);
//        System.out.println("city :- " + city);
//        System.out.println("pinCode :- " + pinCode);
//        System.out.println("packageName :- " + packageName);
//        System.out.println("clinicImageUpload :- " + clinicImageUpload);
//
//        System.out.println("title :- " + title);
//        System.out.println("firstName :- " + firstName);
//        System.out.println("lastName :- " + lastName);
//        System.out.println("maleFemale :- " + maleFemale);
//        System.out.println("emailAddress :- " + emailAddress);
//        System.out.println("mobileNumber :- " + mobileNumber);
//        System.out.println("Password :- " + Password);
//        System.out.println("patientRegi :- " + patientRegi);
//        System.out.println("clinicAdmin :- " + clinicAdmin);

        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.createAppClinicRegistration(title, firstName, lastName, maleFemale,
                    clinicName, addressFirst, addressSecond, city, state, country, pinCode, "", "", emailAddress,
                    "", "", mobileNumber, patientRegi, packageName, "", "", "",
                    "", clinicImageUpload, Password, "", clinicAdmin);

            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                storeDataRegistration.setRegUserTitle(title);
                                storeDataRegistration.setRegUserFirstName(firstName);
                                storeDataRegistration.setRegUserLastName(lastName);
                                storeDataRegistration.setRegUserMaleFemal(maleFemale);
                                storeDataRegistration.setRegUserClinicName(clinicName);
                                storeDataRegistration.setRegUserAddressFirst(addressFirst);
                                storeDataRegistration.setRegUserAddressSecond(addressSecond);
                                storeDataRegistration.setRegUserCity(city);
                                storeDataRegistration.setRegUserState(state);
                                storeDataRegistration.setRegUserCountry(country);
                                storeDataRegistration.setRegUserPinCode(pinCode);
                                storeDataRegistration.setRegUserEmailAddress(emailAddress);
                                storeDataRegistration.setRegUserMobileNumber(mobileNumber);
                                storeDataRegistration.setRegUserPatientRegi(patientRegi);
                                storeDataRegistration.setRegUserPackageName(packageName);
                                storeDataRegistration.setRegUserClinicImageUpload(clinicImageUpload);
                                storeDataRegistration.setRegUserPassword(Password);
                                storeDataRegistration.setRegUserClinicAdmin(clinicAdmin);
                                getAlertDialogForSuccess(response.body().getData());
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

    private void getAlertDialogForSuccess(Integer data) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.success_layout);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_verifyOTP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.setRegisterClinicId(data);
                    startActivity(new Intent(mContext, OTPVerfiyActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private boolean validateRegisterData() {
        try {
            if (clinicName_Et.getText().toString().isEmpty()) {
                clinicName_Et.setError("Enter Clinic Name");
                clinicName_Et.requestFocus();
                return false;
            } else if (addressFirst_Et.getText().toString().isEmpty()) {
                addressFirst_Et.setError("Enter Address first");
                addressFirst_Et.requestFocus();
                return false;
            } else if (addressSecond_Et.getText().toString().isEmpty()) {
                addressSecond_Et.setError("Enter Address second");
                addressSecond_Et.requestFocus();
                return false;
            } else if (pinCode_Et.getText().toString().isEmpty()) {
                pinCode_Et.setError("Enter Pin Code");
                pinCode_Et.requestFocus();
                return true;
            } else if (firstName_Et.getText().toString().isEmpty()) {
                firstName_Et.setError("Enter first name");
                firstName_Et.requestFocus();
                return false;
            } else if (lastName_Et.getText().toString().isEmpty()) {
                lastName_Et.setError("Enter last name");
                lastName_Et.requestFocus();
                return false;
            } else if (emailAddress_Et.getText().toString().isEmpty()) {
                emailAddress_Et.setError("Enter email address");
                emailAddress_Et.requestFocus();
                return false;
            } else if (mobileNumber_Et.getText().toString().isEmpty()) {
                mobileNumber_Et.setError("Enter mobile number");
                mobileNumber_Et.requestFocus();
                return false;
            } else if (Et_Password.getText().toString().isEmpty()) {
                Et_Password.setError("Enter password");
                Et_Password.requestFocus();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(cameraIntent);
            } else {
                Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getEncodedString(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);
    }
}
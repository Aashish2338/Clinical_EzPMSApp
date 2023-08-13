package com.ezpms.ezpmsapp.Appointments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllTitleData;
import com.ezpms.ezpmsapp.Models.BloodGroupData;
import com.ezpms.ezpmsapp.Models.CityData;
import com.ezpms.ezpmsapp.Models.CountryData;
import com.ezpms.ezpmsapp.Models.MedicalHistoryData;
import com.ezpms.ezpmsapp.Models.StateData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllTitleResponse;
import com.ezpms.ezpmsapp.ResponseModels.BloodGroupResponse;
import com.ezpms.ezpmsapp.ResponseModels.CityResponse;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.ResponseModels.CountryResponse;
import com.ezpms.ezpmsapp.ResponseModels.MedicalHistoryResponse;
import com.ezpms.ezpmsapp.ResponseModels.StateResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, title_TvM, patientId_TvM, firstName_TvM, lastName_TvM, dateOfBirth_TvM, age_TvM;
    private TextView addressFirst_TvM, addressSecond_TvM, bloodGroup_TvM, medicalHistory_TvM;
    private ImageView backMenu, calander_Im;
    private Spinner titleSpinner, bloodGroupSpinner, medicalHistorySpinner, countrySpinner, stateSpinner, citySpinner;
    private EditText patientregiID_Et, firstName_Et, lastName_Et, dateOfBirth_Et, age_Et, counrtyCodeNo_Et, mobileNo_Et;
    private EditText Et_EmailId, otherMedicalHistory_Et, addressFirst_Et, addressSecond_Et, postalCode_Et;
    private Button btn_Register;
    private CheckBox maleCheck_Bt, femaleCheck_Bt;
    private int countryID = 0, statesId = 0, citiesId = 0, bloodGroupId = 0, medicalHistoryId = 0;
    private List<CountryData> countryData = null;
    private List<StateData> stateData = null;
    private List<CityData> cityData = null;
    private List<BloodGroupData> bloodGroupData = null;
    private List<MedicalHistoryData> medicalHistoryData = null;
    private List<AllTitleData> allTitleData = null;
    private String[] countryNameArray;
    private String[] stateNameArray;
    private String[] cityNameArray;
    private String[] bloodGroupNameArray;
    private String[] medicalHistoryNameArray;
    private String[] allTitleNameArray;
    private String countryName = "", stateName = "", cityName = "", bloodName = "", medicalHistoryName = "", allTitleName = "";
    private String countriesId, stateId, cityId, bloodId, titleId, medicalId, maleFemale, allTitleId;
    private List<String> CountryIdList = new ArrayList<>();
    private List<String> StateIdList = new ArrayList<>();
    private List<String> CityIdlist = new ArrayList<>();
    private List<String> BloodGroupIdlist = new ArrayList<>();
    private List<String> MedicalHistoryIdlist = new ArrayList<>();
    private List<String> allTitleIdlist = new ArrayList<>();
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_status);
        mContext = this;

        getLayoutUiId();
        getMailFemaleAmdinCheckBox();
        getTextLayoutUiId();
        setTextMandatoryLayout();

        Tv_Title.setText("Patient Registration");
        counrtyCodeNo_Et.setText("+91");
        patientregiID_Et.setEnabled(false);
        patientregiID_Et.setClickable(false);
        patientregiID_Et.setFocusable(false);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllCountryList();
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        backMenu.setOnClickListener(this);
        btn_Register.setOnClickListener(this);
        calander_Im.setOnClickListener(this);
    }

    private void getTextLayoutUiId() {
        try {
            title_TvM = (TextView) findViewById(R.id.title_TvM);
            patientId_TvM = (TextView) findViewById(R.id.patientId_TvM);
            firstName_TvM = (TextView) findViewById(R.id.firstName_TvM);
            lastName_TvM = (TextView) findViewById(R.id.lastName_TvM);
            dateOfBirth_TvM = (TextView) findViewById(R.id.dateOfBirth_TvM);
            age_TvM = (TextView) findViewById(R.id.age_TvM);
            bloodGroup_TvM = (TextView) findViewById(R.id.bloodGroup_TvM);
            medicalHistory_TvM = (TextView) findViewById(R.id.medicalHistory_TvM);
            addressFirst_TvM = (TextView) findViewById(R.id.addressFirst_TvM);
            addressSecond_TvM = (TextView) findViewById(R.id.addressSecond_TvM);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setTextMandatoryLayout() {
        try {
            String title_Tv = "Title<font color='red'>*</font>";
            title_TvM.setText(Html.fromHtml(title_Tv), TextView.BufferType.SPANNABLE);

            String patientId_Tv = "Patient ID<font color='red'>*</font>";
            patientId_TvM.setText(Html.fromHtml(patientId_Tv), TextView.BufferType.SPANNABLE);

            String firstName_Tv = "First Name<font color='red'>*</font>";
            firstName_TvM.setText(Html.fromHtml(firstName_Tv), TextView.BufferType.SPANNABLE);

            String lastName_Tv = "Last Name<font color='red'>*</font>";
            lastName_TvM.setText(Html.fromHtml(lastName_Tv), TextView.BufferType.SPANNABLE);

            String dateOfBirth_Tv = "Date Of Birth<font color='red'>*</font>";
            dateOfBirth_TvM.setText(Html.fromHtml(dateOfBirth_Tv), TextView.BufferType.SPANNABLE);

            String age_Tv = "Age<font color='red'>*</font>";
            age_TvM.setText(Html.fromHtml(age_Tv), TextView.BufferType.SPANNABLE);

            String bloodGroup_Tv = "Blood Group<font color='red'>*</font>";
            bloodGroup_TvM.setText(Html.fromHtml(bloodGroup_Tv), TextView.BufferType.SPANNABLE);

            String medicalHistory_Tv = "Medical History<font color='red'>*</font>";
            medicalHistory_TvM.setText(Html.fromHtml(medicalHistory_Tv), TextView.BufferType.SPANNABLE);

            String addressFirst_Tv = "Address 1<font color='red'>*</font>";
            addressFirst_TvM.setText(Html.fromHtml(addressFirst_Tv), TextView.BufferType.SPANNABLE);

            String addressSecond_Tv = "Address 2<font color='red'>*</font>";
            addressSecond_TvM.setText(Html.fromHtml(addressSecond_Tv), TextView.BufferType.SPANNABLE);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllCountryList() {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("EzPMS");
            progressDialog.setMessage("Please wait data loading !");
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
                                            countriesId = CountryIdList.get(i);
                                            countryName = countryNameArray[i];
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getStateListMethod(countriesId);
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
                                            stateId = StateIdList.get(i);
                                            stateName = stateNameArray[i];
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getCityListMethod(stateId);
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
                                                    getBloodGroupMethod(bloodGroupId);
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

    private void getBloodGroupMethod(int bloodGroupId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<BloodGroupResponse> groupResponseCall = apiClientNetwork.getAllBloodGroup(bloodGroupId);
            groupResponseCall.enqueue(new Callback<BloodGroupResponse>() {
                @Override
                public void onResponse(Call<BloodGroupResponse> call, Response<BloodGroupResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getBloodGroupData() != null) {
                                bloodGroupData = response.body().getBloodGroupData();
                                if (bloodGroupData.size() >= 0) {
                                    bloodGroupNameArray = new String[response.body().getBloodGroupData().size()];
                                    for (int i = 0; i < response.body().getBloodGroupData().size(); i++) {
                                        BloodGroupIdlist.add(response.body().getBloodGroupData().get(i).getBloodGroupId().toString());
                                        bloodGroupNameArray[i] = response.body().getBloodGroupData().get(i).getBloodGroupName();
                                        ArrayAdapter<String> bloodGroupAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, bloodGroupNameArray);
                                        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        bloodGroupSpinner.setAdapter(bloodGroupAdapter);
                                        bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                bloodId = BloodGroupIdlist.get(i);
                                                bloodName = bloodGroupNameArray[i];
                                                if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                    getMediicalHistoryMethod(medicalHistoryId);
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
                                    Toast.makeText(mContext, "Not found any blood group!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found any blood group!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BloodGroupResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getMediicalHistoryMethod(int medicalHistoryId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<MedicalHistoryResponse> medicalHistoryResponseCall = apiClientNetwork.getAllMedicalHistory(medicalHistoryId);
            medicalHistoryResponseCall.enqueue(new Callback<MedicalHistoryResponse>() {
                @Override
                public void onResponse(Call<MedicalHistoryResponse> call, Response<MedicalHistoryResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getMedicalHistoryData() != null) {
                                medicalHistoryData = response.body().getMedicalHistoryData();
                                if (medicalHistoryData.size() >= 0) {
                                    medicalHistoryNameArray = new String[response.body().getMedicalHistoryData().size()];
                                    for (int i = 0; i < response.body().getMedicalHistoryData().size(); i++) {
                                        MedicalHistoryIdlist.add(response.body().getMedicalHistoryData().get(i).getMedicalHistoryId().toString());
                                        medicalHistoryNameArray[i] = response.body().getMedicalHistoryData().get(i).getMedicalHistoryName();
                                        ArrayAdapter<String> medicalHistoryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, medicalHistoryNameArray);
                                        medicalHistoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        medicalHistorySpinner.setAdapter(medicalHistoryAdapter);
                                        medicalHistorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                medicalId = MedicalHistoryIdlist.get(i);
                                                medicalHistoryName = medicalHistoryNameArray[i];
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
                                    Toast.makeText(mContext, "Not found any medical history!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found any medical history!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MedicalHistoryResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                        femaleCheck_Bt.setChecked(true);
                        maleCheck_Bt.setChecked(false);
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            titleSpinner = (Spinner) findViewById(R.id.titleSpinner);
            bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
            medicalHistorySpinner = (Spinner) findViewById(R.id.medicalHistorySpinner);
            countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
            stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
            citySpinner = (Spinner) findViewById(R.id.citySpinner);

            patientregiID_Et = (EditText) findViewById(R.id.patientregiID_Et);
            firstName_Et = (EditText) findViewById(R.id.firstName_Et);
            lastName_Et = (EditText) findViewById(R.id.lastName_Et);
            dateOfBirth_Et = (EditText) findViewById(R.id.dateOfBirth_Et);
            age_Et = (EditText) findViewById(R.id.age_Et);
            mobileNo_Et = (EditText) findViewById(R.id.mobileNo_Et);
            Et_EmailId = (EditText) findViewById(R.id.Et_EmailId);
            otherMedicalHistory_Et = (EditText) findViewById(R.id.otherMedicalHistory_Et);
            addressFirst_Et = (EditText) findViewById(R.id.addressFirst_Et);
            addressSecond_Et = (EditText) findViewById(R.id.addressSecond_Et);
            postalCode_Et = (EditText) findViewById(R.id.postalCode_Et);
            counrtyCodeNo_Et = (EditText) findViewById(R.id.counrtyCodeNo_Et);

            btn_Register = (Button) findViewById(R.id.btn_Register);
            maleCheck_Bt = (CheckBox) findViewById(R.id.maleCheck_Bt);
            femaleCheck_Bt = (CheckBox) findViewById(R.id.femaleCheck_Bt);

            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            calander_Im = (ImageView) findViewById(R.id.calander_Im);

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

            case R.id.btn_Register:
                if (validateInput()) {
                    registrationNewPatients(allTitleId, patientregiID_Et.getText().toString().trim(), firstName_Et.getText().toString().trim(),
                            lastName_Et.getText().toString().trim(), dateOfBirth_Et.getText().toString().trim(),
                            age_Et.getText().toString().trim(), bloodId, maleFemale, counrtyCodeNo_Et.getText().toString().trim(), mobileNo_Et.getText().toString().trim(),
                            Et_EmailId.getText().toString().trim(), medicalId, otherMedicalHistory_Et.getText().toString().trim(),
                            addressFirst_Et.getText().toString().trim(), addressSecond_Et.getText().toString().trim(),
                            countriesId, stateId, cityId, postalCode_Et.getText().toString().trim());
                }
                break;

            case R.id.calander_Im:
                myCalendar = Calendar.getInstance();
                date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDate();
                    }
                };

                new DatePickerDialog(mContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    private void updateDate() {
        try {
            String myFormat = "dd-MMM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dateOfBirth_Et.setText(sdf.format(myCalendar.getTime()));
            dateOfBirth_Et.setClickable(false);

            Date birthdates = sdf.parse(sdf.format(myCalendar.getTime()));
            age_Et.setText(String.valueOf(calculateAgeResult(birthdates)));

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    public static int calculateAgeResult(Date birthdates) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdates);
        Calendar today = Calendar.getInstance();
        int yearDifferences = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifferences--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
                yearDifferences--;
            }
        }
        return yearDifferences;
    }

    private void registrationNewPatients(String titleId, String patientregiID, String firstName, String lastName,
                                         String dateOfBirth, String age, String bloodId, String maleFemale, String counrtyCode, String mobileNo,
                                         String emailId, String medicalId, String otherMedicalHistory, String addressFirst,
                                         String addressSecond, String countriesId, String stateId, String cityId, String postalCode) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.createPatientRegistration(patientregiID, titleId, firstName, lastName,
                    maleFemale, "", age, emailId, mobileNo, addressFirst, addressSecond, cityId,
                    stateId, countriesId, postalCode, counrtyCode, "", dateOfBirth, bloodId, medicalId, otherMedicalHistory,
                    "", "");
            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Say Hi!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Say Bye!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Try again !", Toast.LENGTH_SHORT).show();
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

    private boolean validateInput() {
        try {
            if (firstName_Et.getText().toString().trim().isEmpty()) {
                firstName_Et.setError("Enter first name");
                firstName_Et.requestFocus();
                return false;
            } else if (lastName_Et.getText().toString().trim().isEmpty()) {
                lastName_Et.setError("Enter patient Id");
                lastName_Et.requestFocus();
                return false;
            } else if (dateOfBirth_Et.getText().toString().trim().isEmpty()) {
                dateOfBirth_Et.setError("Enter date of birth");
                dateOfBirth_Et.requestFocus();
                return false;
            } else if (age_Et.getText().toString().trim().isEmpty()) {
                age_Et.setError("Enter age");
                age_Et.requestFocus();
                return false;
            } else if (mobileNo_Et.getText().toString().trim().isEmpty()) {
                mobileNo_Et.setError("Enter mobile number");
                mobileNo_Et.requestFocus();
                return false;
            } else if (Et_EmailId.getText().toString().trim().isEmpty()) {
                Et_EmailId.setError("Enter email address");
                Et_EmailId.requestFocus();
                return false;
            } else if (otherMedicalHistory_Et.getText().toString().trim().isEmpty()) {
                otherMedicalHistory_Et.setError("Enter other medical history");
                otherMedicalHistory_Et.requestFocus();
                return false;
            } else if (addressFirst_Et.getText().toString().trim().isEmpty()) {
                addressFirst_Et.setError("Enter address first");
                addressFirst_Et.requestFocus();
                return false;
            } else if (addressSecond_Et.getText().toString().trim().isEmpty()) {
                addressSecond_Et.setError("Enter address second");
                addressSecond_Et.requestFocus();
                return false;
            } else if (postalCode_Et.getText().toString().trim().isEmpty()) {
                postalCode_Et.setError("Enter  postal code");
                postalCode_Et.requestFocus();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return false;
    }
}
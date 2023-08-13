package com.ezpms.ezpmsapp.Patients;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
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

import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Appointments.AppointmentsRegistrationActivity;
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
import com.ezpms.ezpmsapp.Treatments.TreatmentsHistoryActivity;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePatientDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView backMenu, calanderDOB_Im;
    private TextView Tv_Title, title_TvM, patientId_TvM, firstName_TvM, lastName_TvM, dateOfBirth_TvM, age_TvM, bloodGroup_TvM;
    private TextView medicalHistory_TvM, addressFirst_TvM, addressSecond_TvM, cancel_Btn;
    private Spinner titleSpinner, bloodGroupSpinner, medicalHistorySpinner, countrySpinner, stateSpinner, citySpinner;
    private EditText patientRegId_ET, firstName_Et, lastName_Et, dateOfBirth_Pt, age_Et, counrtyCodeNo_Et, mobileNumber_Et;
    private EditText Et_EmailId, otherMedicalHistory_Et, addressFirst_Et, addressSecond_Et, postalCode_Et;
    private CheckBox maleCheck_Bt, femaleCheck_Bt;
    private Button update_Btn, edit_Btn, history_Btn, appointments_Btn;

    private int countryID = 0, statesId = 0, citiesId = 0, bloodGroupId = 0, medicalHistoryId = 0, patientId;
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
    private String maleFemale, countryId, stateId, cityId, titleId, bloodId, medicalId, allTitleId, firstName, lastName, mobileNumber, gender, dob;
    private String email, bloodGroup, medicalHistory, otherMedicalHistory, addressFirst, addressSecond, postalCode, cityIds, stateIds, countryIds;
    private List<String> CountryIdList = new ArrayList<>();
    private List<String> StateIdList = new ArrayList<>();
    private List<String> CityIdlist = new ArrayList<>();
    private List<String> BloodGroupIdlist = new ArrayList<>();
    private List<String> MedicalHistoryIdlist = new ArrayList<>();
    private List<String> allTitleIdlist = new ArrayList<>();
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private SessionManager sessionManager;
    int appAppointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient_details);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayouUiId();
        getTextLayoutUiId();
        setTextMandatoryLayout();
        setForMaleFemalePatient();

        patientRegId_ET.setClickable(false);
        patientRegId_ET.setEnabled(false);
        patientRegId_ET.setFocusable(false);

        Tv_Title.setText("Update Patient detail");
        backMenu.setOnClickListener(this);
        calanderDOB_Im.setOnClickListener(this);
        edit_Btn.setOnClickListener(this);
        history_Btn.setOnClickListener(this);
        appointments_Btn.setOnClickListener(this);
        cancel_Btn.setOnClickListener(this);
        update_Btn.setOnClickListener(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            titleId = String.valueOf(extra.getInt("Title"));
            firstName = extra.getString("FirstName");
            lastName = extra.getString("LastName");
            patientId = extra.getInt("PatientId");
            mobileNumber = extra.getString("PhoneNumber");
            email = extra.getString("Email");
            gender = extra.getString("Gender");
            dob = extra.getString("DOB");
            bloodGroup = String.valueOf(extra.getInt("BloodGroup"));
            medicalHistory = String.valueOf(extra.getInt("MedicalHistory"));
            otherMedicalHistory = extra.getString("OtherMedicalHistory");
            addressFirst = extra.getString("AddressFirst");
            addressSecond = extra.getString("AddressSecond");
            postalCode = extra.getString("PostalCode");
            cityIds = String.valueOf(extra.getInt("CityId"));
            stateIds = String.valueOf(extra.getInt("StateId"));
            countryIds = String.valueOf(extra.getInt("CountryId"));
            appAppointmentId = extra.getInt("AppAppointmentId");

        } else {
            patientId = sessionManager.getPatientId();
            gender = sessionManager.getUserGender();
            mobileNumber = sessionManager.getUserPhoneNumber();
        }

        setAllTextFields();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllCountryList(countryID);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAllTextFields() {
        try {
            firstName_Et.setText(firstName);
            lastName_Et.setText(lastName);
            mobileNumber_Et.setText(mobileNumber);
            Et_EmailId.setText(email);
            otherMedicalHistory_Et.setText(otherMedicalHistory);
            addressFirst_Et.setText(addressFirst);
            addressSecond_Et.setText(addressSecond);
            postalCode_Et.setText(postalCode);
            int lenght = String.valueOf(patientId).length();
            if (lenght == 6) {
                patientRegId_ET.setText("0" + String.valueOf(patientId));
            } else if (lenght == 5) {
                patientRegId_ET.setText("00" + String.valueOf(patientId));
            } else if (lenght == 4) {
                patientRegId_ET.setText("000" + String.valueOf(patientId));
            } else if (lenght == 3) {
                patientRegId_ET.setText("0000" + String.valueOf(patientId));
            } else if (lenght == 2) {
                patientRegId_ET.setText("00000" + String.valueOf(patientId));
            } else {
                patientRegId_ET.setText("000000" + String.valueOf(patientId));
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            String date = formatter.format(Date.parse(dob));
            dateOfBirth_Pt.setText(date);

            Date birthdates = formatter.parse(date);
            age_Et.setText(String.valueOf(calculateAge(birthdates)));

            if (gender.equalsIgnoreCase("Male")) {
                maleFemale = "Male";
                maleCheck_Bt.setChecked(true);
                femaleCheck_Bt.setClickable(false);
            } else {
                maleFemale = "Female";
                femaleCheck_Bt.setChecked(true);
                maleCheck_Bt.setChecked(false);
            }

            setAllfieldsDesable();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setAllfieldsDesable() {
        try {
            update_Btn.setVisibility(View.GONE);

            firstName_Et.setEnabled(false);
            firstName_Et.setClickable(false);
            firstName_Et.setFocusable(false);

            lastName_Et.setEnabled(false);
            lastName_Et.setClickable(false);
            lastName_Et.setFocusable(false);

            mobileNumber_Et.setEnabled(false);
            mobileNumber_Et.setClickable(false);
            mobileNumber_Et.setFocusable(false);

            counrtyCodeNo_Et.setEnabled(false);
            counrtyCodeNo_Et.setClickable(false);
            counrtyCodeNo_Et.setFocusable(false);

            dateOfBirth_Pt.setEnabled(false);
            dateOfBirth_Pt.setClickable(false);
            dateOfBirth_Pt.setFocusable(false);

            age_Et.setEnabled(false);
            age_Et.setClickable(false);
            age_Et.setFocusable(false);

            Et_EmailId.setEnabled(false);
            Et_EmailId.setClickable(false);
            Et_EmailId.setFocusable(false);

            otherMedicalHistory_Et.setEnabled(false);
            otherMedicalHistory_Et.setClickable(false);
            otherMedicalHistory_Et.setFocusable(false);

            addressFirst_Et.setEnabled(false);
            addressFirst_Et.setClickable(false);
            addressFirst_Et.setFocusable(false);

            addressSecond_Et.setEnabled(false);
            addressSecond_Et.setClickable(false);
            addressSecond_Et.setFocusable(false);

            postalCode_Et.setEnabled(false);
            postalCode_Et.setClickable(false);
            postalCode_Et.setFocusable(false);

            maleCheck_Bt.setClickable(false);
            femaleCheck_Bt.setClickable(false);

            calanderDOB_Im.setClickable(false);

            ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, countryNameArray);
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countrySpinner.setEnabled(false);
            countrySpinner.setClickable(false);
            countrySpinner.setAdapter(countryAdapter);

            ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, stateNameArray);
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setEnabled(false);
            stateSpinner.setClickable(false);
            stateSpinner.setAdapter(stateAdapter);

            ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, cityNameArray);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setEnabled(false);
            citySpinner.setClickable(false);
            citySpinner.setAdapter(cityAdapter);

            ArrayAdapter<String> bloodGroupAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, bloodGroupNameArray);
            bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bloodGroupSpinner.setEnabled(false);
            bloodGroupSpinner.setClickable(false);
            bloodGroupSpinner.setAdapter(bloodGroupAdapter);

            ArrayAdapter<String> medicalHistoryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, medicalHistoryNameArray);
            medicalHistoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            medicalHistorySpinner.setEnabled(false);
            medicalHistorySpinner.setClickable(false);
            medicalHistorySpinner.setAdapter(medicalHistoryAdapter);

            ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, allTitleNameArray);
            titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            titleSpinner.setEnabled(false);
            titleSpinner.setClickable(false);
            titleSpinner.setAdapter(titleAdapter);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getTextLayoutUiId() {
        try {
            title_TvM = (TextView) findViewById(R.id.title_TvM);
            patientId_TvM = (TextView) findViewById(R.id.patientId_TvM);
            firstName_TvM = (TextView) findViewById(R.id.firstName_TvM);
            lastName_TvM = (TextView) findViewById(R.id.lastName_TvM);
            dateOfBirth_TvM = (TextView) findViewById(R.id.dateOfBirth_TvM);
            addressFirst_TvM = (TextView) findViewById(R.id.addressFirst_TvM);
            addressSecond_TvM = (TextView) findViewById(R.id.addressSecond_TvM);
            age_TvM = (TextView) findViewById(R.id.age_TvM);
            bloodGroup_TvM = (TextView) findViewById(R.id.bloodGroup_TvM);
            medicalHistory_TvM = (TextView) findViewById(R.id.medicalHistory_TvM);
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

    private void getLayouUiId() {
        try {
            backMenu = (ImageView) findViewById(R.id.backMenu);
            calanderDOB_Im = (ImageView) findViewById(R.id.calanderDOB_Im);
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            cancel_Btn = (TextView) findViewById(R.id.cancel_Btn);

            titleSpinner = (Spinner) findViewById(R.id.titleSpinner);
            bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
            medicalHistorySpinner = (Spinner) findViewById(R.id.medicalHistorySpinner);
            countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
            stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
            citySpinner = (Spinner) findViewById(R.id.citySpinner);

            patientRegId_ET = (EditText) findViewById(R.id.patientRegId_ET);
            firstName_Et = (EditText) findViewById(R.id.firstName_Et);
            lastName_Et = (EditText) findViewById(R.id.lastName_Et);
            dateOfBirth_Pt = (EditText) findViewById(R.id.dateOfBirth_Pt);
            age_Et = (EditText) findViewById(R.id.age_Et);
            counrtyCodeNo_Et = (EditText) findViewById(R.id.counrtyCodeNo_Et);
            mobileNumber_Et = (EditText) findViewById(R.id.mobileNumber_Et);
            Et_EmailId = (EditText) findViewById(R.id.Et_EmailId);
            otherMedicalHistory_Et = (EditText) findViewById(R.id.otherMedicalHistory_Et);
            addressFirst_Et = (EditText) findViewById(R.id.addressFirst_Et);
            addressSecond_Et = (EditText) findViewById(R.id.addressSecond_Et);
            postalCode_Et = (EditText) findViewById(R.id.postalCode_Et);

            maleCheck_Bt = (CheckBox) findViewById(R.id.maleCheck_Bt);
            femaleCheck_Bt = (CheckBox) findViewById(R.id.femaleCheck_Bt);

            edit_Btn = (Button) findViewById(R.id.edit_Btn);
            history_Btn = (Button) findViewById(R.id.history_Btn);
            appointments_Btn = (Button) findViewById(R.id.appointments_Btn);
            update_Btn = (Button) findViewById(R.id.update_Btn);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setForMaleFemalePatient() {
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
                                if (countryData != null) {
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
//                                            countrySpinner.setSelection(CountryIdList.indexOf(countryIds));
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
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
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
                                if (stateData != null) {
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
//                                            stateSpinner.setSelection(StateIdList.indexOf(stateIds));
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
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                                if (cityData != null) {
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
//                                                citySpinner.setSelection(CityIdlist.indexOf(cityIds));
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
//                                                bloodGroupSpinner.setSelection(BloodGroupIdlist.indexOf(bloodGroup));
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
//                                                medicalHistorySpinner.setSelection(MedicalHistoryIdlist.indexOf(medicalHistory));
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
//                                                titleSpinner.setSelection(allTitleIdlist.indexOf(titleId));
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
                        Toast.makeText(mContext, "Title data not found !", Toast.LENGTH_SHORT).show();
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
        try {
            switch (view.getId()) {
                case R.id.backMenu:
                    onBackPressed();
                    break;

                case R.id.calanderDOB_Im:
                    myCalendar = Calendar.getInstance();
                    date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateDateDOB();
                        }
                    };
                    new DatePickerDialog(mContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    break;

                case R.id.edit_Btn:
                    setEnableAllFields();
                    break;

                case R.id.history_Btn:
                    Intent intent1 = new Intent(mContext, TreatmentsHistoryActivity.class);
                    intent1.putExtra("PatientId", patientId);
                    intent1.putExtra("AppAppointmentId", appAppointmentId);
//                    intent1.putExtra("ClinicId", sessionManager.getUserCliniId());
                    startActivity(intent1);
                    break;

                case R.id.update_Btn:
                    if (validateRegisterPatient()) {
                        email = Et_EmailId.getText().toString();
                        if (email.isEmpty()) {
                            email = "";
                        }
                        otherMedicalHistory = otherMedicalHistory_Et.getText().toString();
                        if (otherMedicalHistory.isEmpty()) {
                            otherMedicalHistory = "";
                        }

                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            updatePatientAppointment(patientId, allTitleId, patientRegId_ET.getText().toString(), firstName_Et.getText().toString(),
                                    lastName_Et.getText().toString(), dateOfBirth_Pt.getText().toString(), age_Et.getText().toString(),
                                    bloodId, maleFemale, counrtyCodeNo_Et.getText().toString(), mobileNumber_Et.getText().toString(),
                                    email, medicalId, otherMedicalHistory,
                                    addressFirst_Et.getText().toString(), addressSecond_Et.getText().toString(), countryId, stateId,
                                    cityId, postalCode_Et.getText().toString(), sessionManager.getUserCliniId());
                        } else {
                            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case R.id.appointments_Btn:
                    Intent intent = new Intent(mContext, AppointmentsRegistrationActivity.class);
                    intent.putExtra("PatientId", patientId);
                    intent.putExtra("AppAppointmentId", appAppointmentId);
                    startActivity(intent);
                    break;

                case R.id.cancel_Btn:
                    onBackPressed();
                    break;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void updatePatientAppointment(int patientId, String titleId, String patientIdReg, String firstName, String lastName, String dateOfBirth,
                                          String age, String bloodId, String maleFemaleCheck, String counrtyCodeNo, String mobileNumber,
                                          String emailAddress, String medicalId, String otherMedicalHistory, String addressFirst,
                                          String addressSecond, String countryId, String stateId, String cityId, String pinCode,
                                          int clinicId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.updatePatientDetails(patientId, patientIdReg, titleId, firstName,
                    lastName, maleFemaleCheck, "", age, emailAddress, mobileNumber, addressFirst,
                    addressSecond, cityId, stateId, countryId, pinCode, counrtyCodeNo, sessionManager.getLoginUserId(), dateOfBirth, bloodId,
                    medicalId, otherMedicalHistory, "", "", "", "",
                    "", "", "", String.valueOf(clinicId));
            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            int patientData = response.body().getData();
                            getAlertDialogForPatientAppointmentSuccess(patientData);
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

    private void getAlertDialogForPatientAppointmentSuccess(int patientData) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.success_layout);

            TextView Tvlogin_TitleOld = (TextView) dialog.findViewById(R.id.Tvlogin_TitleOld);
            Tvlogin_TitleOld.setText("Appointment Updated successfully");
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

    private boolean validateRegisterPatient() {
      /*  if (patientRegId_ET.getText().toString().isEmpty()) {
            patientRegId_ET.setError("Enter patient Id");
            patientRegId_ET.requestFocus();
            return false;
        } else */
        if (firstName_Et.getText().toString().isEmpty()) {
            firstName_Et.setError("Enter first name");
            firstName_Et.requestFocus();
            return false;
        } else if (lastName_Et.getText().toString().isEmpty()) {
            lastName_Et.setError("Enter last name");
            lastName_Et.requestFocus();
            return false;
        } else if (dateOfBirth_Pt.getText().toString().isEmpty()) {
            dateOfBirth_Pt.setError("Enter date of birth");
            dateOfBirth_Pt.requestFocus();
            return false;
        } else if (age_Et.getText().toString().isEmpty()) {
            age_Et.setError("Enter age");
            age_Et.requestFocus();
            return false;
        } else if (mobileNumber_Et.getText().toString().isEmpty()) {
            mobileNumber_Et.setError("Enter mobile number");
            mobileNumber_Et.requestFocus();
            return false;
        }
//        else if (Et_EmailId.getText().toString().isEmpty()) {
//            Et_EmailId.setError("Enter email address");
//            Et_EmailId.requestFocus();
//            return false;
//        }
//        else if (otherMedicalHistory_Et.getText().toString().isEmpty()) {
//            otherMedicalHistory_Et.setError("Enter other medical history");
//            otherMedicalHistory_Et.requestFocus();
//            return false;
//        }
        else if (addressFirst_Et.getText().toString().isEmpty()) {
            addressFirst_Et.setError("Enter address first");
            addressFirst_Et.requestFocus();
            return false;
        } else if (addressSecond_Et.getText().toString().isEmpty()) {
            addressSecond_Et.setError("Enter address second");
            addressSecond_Et.requestFocus();
            return false;
        } else if (postalCode_Et.getText().toString().isEmpty()) {
            postalCode_Et.setError("Enter pin code");
            postalCode_Et.requestFocus();
            return false;
        }
        return true;
    }

    private void setEnableAllFields() {
        try {
            update_Btn.setVisibility(View.VISIBLE);

            firstName_Et.setEnabled(true);
            firstName_Et.setClickable(true);
            firstName_Et.setFocusable(true);
            firstName_Et.setFocusableInTouchMode(true);

            lastName_Et.setEnabled(true);
            lastName_Et.setClickable(true);
            lastName_Et.setFocusable(true);
            lastName_Et.setFocusableInTouchMode(true);

            mobileNumber_Et.setEnabled(true);
            mobileNumber_Et.setClickable(true);
            mobileNumber_Et.setFocusable(true);
            mobileNumber_Et.setFocusableInTouchMode(true);

            counrtyCodeNo_Et.setEnabled(true);
            counrtyCodeNo_Et.setClickable(true);
            counrtyCodeNo_Et.setFocusable(true);
            counrtyCodeNo_Et.setFocusableInTouchMode(true);

            dateOfBirth_Pt.setEnabled(true);
            dateOfBirth_Pt.setClickable(true);
            dateOfBirth_Pt.setFocusable(true);
            dateOfBirth_Pt.setFocusableInTouchMode(true);

            age_Et.setEnabled(true);
            age_Et.setClickable(true);
            age_Et.setFocusable(true);
            age_Et.setFocusableInTouchMode(true);

            Et_EmailId.setEnabled(true);
            Et_EmailId.setClickable(true);
            Et_EmailId.setFocusable(true);
            Et_EmailId.setFocusableInTouchMode(true);

            otherMedicalHistory_Et.setEnabled(true);
            otherMedicalHistory_Et.setClickable(true);
            otherMedicalHistory_Et.setFocusable(true);
            otherMedicalHistory_Et.setFocusableInTouchMode(true);

            addressFirst_Et.setEnabled(true);
            addressFirst_Et.setClickable(true);
            addressFirst_Et.setFocusable(true);
            addressFirst_Et.setFocusableInTouchMode(true);

            addressSecond_Et.setEnabled(true);
            addressSecond_Et.setClickable(true);
            addressSecond_Et.setFocusable(true);
            addressSecond_Et.setFocusableInTouchMode(true);

            postalCode_Et.setEnabled(true);
            postalCode_Et.setClickable(true);
            postalCode_Et.setFocusable(true);
            postalCode_Et.setFocusableInTouchMode(true);

            maleCheck_Bt.setClickable(true);
            femaleCheck_Bt.setClickable(true);

            calanderDOB_Im.setClickable(true);

            ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, countryNameArray);
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countrySpinner.setEnabled(true);
            countrySpinner.setClickable(true);
            countrySpinner.setAdapter(countryAdapter);

            ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, stateNameArray);
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setEnabled(true);
            stateSpinner.setClickable(true);
            stateSpinner.setAdapter(stateAdapter);

            ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, cityNameArray);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setEnabled(true);
            citySpinner.setClickable(true);
            citySpinner.setAdapter(cityAdapter);

            ArrayAdapter<String> bloodGroupAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, bloodGroupNameArray);
            bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bloodGroupSpinner.setEnabled(true);
            bloodGroupSpinner.setClickable(true);
            bloodGroupSpinner.setAdapter(bloodGroupAdapter);

            ArrayAdapter<String> medicalHistoryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, medicalHistoryNameArray);
            medicalHistoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            medicalHistorySpinner.setEnabled(true);
            medicalHistorySpinner.setClickable(true);
            medicalHistorySpinner.setAdapter(medicalHistoryAdapter);

            ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, allTitleNameArray);
            titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            titleSpinner.setEnabled(true);
            titleSpinner.setClickable(true);
            titleSpinner.setAdapter(titleAdapter);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void updateDateDOB() {
        try {
            String myFormatDOB = "dd-MMM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormatDOB, Locale.US);
            dateOfBirth_Pt.setText(sdf.format(myCalendar.getTime()));
            dateOfBirth_Pt.setClickable(false);

            Date birthdate = sdf.parse(sdf.format(myCalendar.getTime()));
            age_Et.setText(String.valueOf(calculateAge(birthdate)));
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    public static int calculateAge(Date birthdate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();
        int yearDifference = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }
        }
        return yearDifference;
    }
}
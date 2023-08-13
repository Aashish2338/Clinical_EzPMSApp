package com.ezpms.ezpmsapp.Appointments;

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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Adapters.AutoCompleteAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllTitleData;
import com.ezpms.ezpmsapp.Models.AppointmentStatusData;
import com.ezpms.ezpmsapp.Models.BloodGroupData;
import com.ezpms.ezpmsapp.Models.CityData;
import com.ezpms.ezpmsapp.Models.ConsultationTypesData;
import com.ezpms.ezpmsapp.Models.CountryData;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.Models.MedicalHistoryData;
import com.ezpms.ezpmsapp.Models.PatientDetailByID;
import com.ezpms.ezpmsapp.Models.PatientListData;
import com.ezpms.ezpmsapp.Models.StateData;
import com.ezpms.ezpmsapp.Models.TimeSloteData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllTitleResponse;
import com.ezpms.ezpmsapp.ResponseModels.AppointmentStatusResponse;
import com.ezpms.ezpmsapp.ResponseModels.BloodGroupResponse;
import com.ezpms.ezpmsapp.ResponseModels.CityResponse;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.ResponseModels.ConsultationTypesResponse;
import com.ezpms.ezpmsapp.ResponseModels.CountryResponse;
import com.ezpms.ezpmsapp.ResponseModels.DoctorAvailableResponse;
import com.ezpms.ezpmsapp.ResponseModels.MedicalHistoryResponse;
import com.ezpms.ezpmsapp.ResponseModels.PatientDetailByIDResponse;
import com.ezpms.ezpmsapp.ResponseModels.PatientListResponse;
import com.ezpms.ezpmsapp.ResponseModels.StateResponse;
import com.ezpms.ezpmsapp.ResponseModels.TimeSlotsResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, changeableTextNewExisting, title_TvM, patientId_TvM, firstName_TvM, lastName_TvM, dateOfBirth_TvM, addressFirst_TvM;
    private TextView addressSecond_TvM, selectPatient_TvM, doctorName_TvM, consultationType_TvM, appointmentStatus_TvM, appointmentDate_TvM, timeSlote_TvM;
    private ImageView backMenu, calander_Im, calanderDOB_Im;
    private TextView cancel_Btn;
    private CheckBox checkNewPatients, checkExistingPatient, maleCheck_Bt, femaleCheck_Bt;
    private Spinner titleSpinner, bloodGroupSpinner, medicalHistorySpinner, countrySpinner, stateSpinner, citySpinner;
    private Spinner doctorNameSpinner, consultationTypeSpinner, appointmentStatusSpinner, timeSloteSpinner;
    private EditText patientId_Et, firstName_Et, lastName_Et, dateOfBirth_Pt, age_Et, mobileNumber_Et, emailAddress_Et;
    private EditText EtOtherMedicalHistory, addressFirst_Et, addressSecond_Et, pinCode_Et, appointmentDate_Et;
    private EditText notes_Et, counrtyCodeNo_Et;
    private AutoCompleteTextView patientName_Et;
    private TextView patientId_Tv, patientName_Tv, TvGender_Title, age_Tv, mobileNumber_Tv, dateOfBirth_Tv, bloodGroup_Tv;
    private TextView medicalHistory_Tv, otherMedicalHistory_Tv;
    private Button btn_CreateAccount, addAppointments_Btn;
    private RelativeLayout addNewPatientLayout, addExistingPatientLayout;
    private int countryID = 0, statesId = 0, citiesId = 0, consultationType = 0, timeSlotId = 0, patientId;
    private int bloodGroupId = 0, medicalHistoryId = 0;
    private List<CountryData> countryData = null;
    private List<StateData> stateData = null;
    private List<CityData> cityData = null;
    private List<PatientListData> patientListData = null;
    private List<DoctorAvailableData> doctorAvailableData = null;
    private List<ConsultationTypesData> consultationTypesData = null;
    private List<TimeSloteData> timeSloteData = null;
    private List<BloodGroupData> bloodGroupData = null;
    private List<MedicalHistoryData> medicalHistoryData = null;
    private List<AllTitleData> allTitleData = null;
    private List<AppointmentStatusData> appointmentStatusData = null;
    private String[] countryNameArray;
    private String[] stateNameArray;
    private String[] cityNameArray;
    private String[] doctorAvailableNameArray;
    private String[] consultationTypesNameArray;
    private String[] timeSloteNameArray;
    private String[] bloodGroupNameArray;
    private String[] medicalHistoryNameArray;
    private String[] allTitleNameArray;
    private String[] appointmentStatusNameArray;
    private String countryName = "", stateName = "", cityName = "", doctorName = "", timeSloteName = "", consultationTypesName = "";
    private String appointmentStatusTypesName = "", bloodName = "", medicalHistoryName = "", allTitleName = "";
    private String maleFemale, clinicAdmin, countryId, stateId, cityId, doctorAvailId, consultationTypesId, allTitleId;
    private String titleId, timeSloteId, statusId, bloodId, medicalId, appointmentStatusTypesId, str_emailId,
            str_otherMedicalHistory, str_pinCode, str_address1, str_address2;
    private List<String> CountryIdList = new ArrayList<>();
    private List<String> StateIdList = new ArrayList<>();
    private List<String> CityIdlist = new ArrayList<>();
    private List<String> doctorAvailIdlist = new ArrayList<>();
    private List<String> consultationTypesIdlist = new ArrayList<>();
    private List<String> timeSloteIdlist = new ArrayList<>();
    private List<String> BloodGroupIdlist = new ArrayList<>();
    private List<String> MedicalHistoryIdlist = new ArrayList<>();
    private List<String> allTitleIdlist = new ArrayList<>();
    private List<String> appointmentStatusIdlist = new ArrayList<>();
    private SessionManager sessionManager;
    private AutoCompleteAdapter adapter;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    String str_Notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();
        getTextLayoutUiId();
        setTextMandatoryLayout();
        Tv_Title.setText("Appointment Registration");
        counrtyCodeNo_Et.setText("+91");

        patientId_Et.setEnabled(false);
        patientId_Et.setClickable(false);
        patientId_Et.setFocusable(false);

        checkExistingPatient.setChecked(true);
        if (checkExistingPatient.isChecked() == true) {
            changeableTextNewExisting.setText("Enter Patient Appointment Details To Book Appointment");
            checkNewPatients.setChecked(false);
            addExistingPatientLayout.setVisibility(View.VISIBLE);
            addNewPatientLayout.setVisibility(View.GONE);
            if (NetworkStatus.isNetworkAvailable(mContext)) {
                getPatientSearchList(sessionManager.getUserCliniId());
                getAllDoctorsName(sessionManager.getUserCliniId());
            } else {
                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
            }
        }

        backMenu.setOnClickListener(this);
        btn_CreateAccount.setOnClickListener(this);
        addAppointments_Btn.setOnClickListener(this);
        cancel_Btn.setOnClickListener(this);
        calander_Im.setOnClickListener(this);
        calanderDOB_Im.setOnClickListener(this);

        setLayoutForNewAndExistingPatient();

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
            selectPatient_TvM = (TextView) findViewById(R.id.selectPatient_TvM);
            doctorName_TvM = (TextView) findViewById(R.id.doctorName_TvM);
            consultationType_TvM = (TextView) findViewById(R.id.consultationType_TvM);
            appointmentStatus_TvM = (TextView) findViewById(R.id.appointmentStatus_TvM);
            appointmentDate_TvM = (TextView) findViewById(R.id.appointmentDate_TvM);
            timeSlote_TvM = (TextView) findViewById(R.id.timeSlote_TvM);
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

            String addressFirst_Tv = "Address 1<font color='red'>*</font>";
            addressFirst_TvM.setText(Html.fromHtml(addressFirst_Tv), TextView.BufferType.SPANNABLE);

//            String addressSecond_Tv = "Address 2<font color='red'>*</font>";
//            addressSecond_TvM.setText(Html.fromHtml(addressSecond_Tv), TextView.BufferType.SPANNABLE);

            String selectPatient_Tv = "Select Patient<font color='red'>*</font>";
            selectPatient_TvM.setText(Html.fromHtml(selectPatient_Tv), TextView.BufferType.SPANNABLE);

            String doctorName_Tv = "Doctor Name<font color='red'>*</font>";
            doctorName_TvM.setText(Html.fromHtml(doctorName_Tv), TextView.BufferType.SPANNABLE);

            String consultationType_Tv = "Consultation Type<font color='red'>*</font>";
            consultationType_TvM.setText(Html.fromHtml(consultationType_Tv), TextView.BufferType.SPANNABLE);

            String appointmentStatus_Tv = "Appointment Status<font color='red'>*</font>";
            appointmentStatus_TvM.setText(Html.fromHtml(appointmentStatus_Tv), TextView.BufferType.SPANNABLE);

            String appointmentDate_Tv = "Appointment Date<font color='red'>*</font>";
            appointmentDate_TvM.setText(Html.fromHtml(appointmentDate_Tv), TextView.BufferType.SPANNABLE);

            String timeSlote_Tv = "Time Slote<font color='red'>*</font>";
            timeSlote_TvM.setText(Html.fromHtml(timeSlote_Tv), TextView.BufferType.SPANNABLE);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setLayoutForNewAndExistingPatient() {
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

            checkNewPatients.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        changeableTextNewExisting.setText("Enter Patient Details To Add Patient");
                        addNewPatientLayout.setVisibility(View.VISIBLE);
                        addExistingPatientLayout.setVisibility(View.GONE);
                        checkExistingPatient.setChecked(false);
                        checkNewPatients.setChecked(true);
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            getAllCountryList();
                        } else {
                            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            checkExistingPatient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        changeableTextNewExisting.setText("Enter Patient Appointment Details To Book Appointment");
                        addNewPatientLayout.setVisibility(View.GONE);
                        addExistingPatientLayout.setVisibility(View.VISIBLE);
                        checkNewPatients.setChecked(false);
                        checkExistingPatient.setChecked(true);
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getPatientSearchList(int userCliniId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<PatientListResponse> patientListResponseCall = apiClientNetwork.getAllPatient(userCliniId);
            patientListResponseCall.enqueue(new Callback<PatientListResponse>() {
                @Override
                public void onResponse(Call<PatientListResponse> call, Response<PatientListResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getPatientListData() != null) {
                                patientListData = response.body().getPatientListData();
                                adapter = new AutoCompleteAdapter(mContext, patientListData);
                                patientName_Et.setThreshold(1);
                                patientName_Et.setAdapter(adapter);
                                patientName_Et.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        patientId = response.body().getPatientListData().get(i).getPatientId();
                                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                                            getPatientDetailByID(patientId);
                                        } else {
                                            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(mContext, "Not found patients!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<PatientListResponse> call, Throwable t) {
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

    private void getPatientDetailByID(int patientId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<PatientDetailByIDResponse> patientDetailByIDResponseCall = apiClientNetwork.getPatientDetailByID(patientId);
            patientDetailByIDResponseCall.enqueue(new Callback<PatientDetailByIDResponse>() {
                @Override
                public void onResponse(Call<PatientDetailByIDResponse> call, Response<PatientDetailByIDResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getPatientDetailByID() != null) {
                                setPatientDetailsInTextView(response.body().getPatientDetailByID());
                            } else {
                                Toast.makeText(mContext, "Not found patient details!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PatientDetailByIDResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setPatientDetailsInTextView(PatientDetailByID patientDetailByID) {
        try {
            patientName_Et.setText(patientDetailByID.getPatientName() + "/" + patientDetailByID.getPhoneNumber());

            int lenght = String.valueOf(patientDetailByID.getPatientId()).length();
            if (lenght == 6) {
                patientId_Tv.setText("0" + String.valueOf(patientDetailByID.getPatientId()));
            } else if (lenght == 5) {
                patientId_Tv.setText("00" + String.valueOf(patientDetailByID.getPatientId()));
            } else if (lenght == 4) {
                patientId_Tv.setText("000" + String.valueOf(patientDetailByID.getPatientId()));
            } else if (lenght == 3) {
                patientId_Tv.setText("0000" + String.valueOf(patientDetailByID.getPatientId()));
            } else if (lenght == 2) {
                patientId_Tv.setText("00000" + String.valueOf(patientDetailByID.getPatientId()));
            } else {
                patientId_Tv.setText("000000" + String.valueOf(patientDetailByID.getPatientId()));
            }

            patientName_Tv.setText(patientDetailByID.getPatientName());
            TvGender_Title.setText(patientDetailByID.getGender());
            mobileNumber_Tv.setText(patientDetailByID.getPhoneNumber());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            String date = formatter.format(Date.parse(patientDetailByID.getDob()));
            dateOfBirth_Tv.setText(date);

//            bloodGroup_Tv.setText(patientDetailByID.getBloodGroup().toString());
            if (patientDetailByID.getBloodGroup() == 1) {
                bloodGroup_Tv.setText("A-");
            } else if (patientDetailByID.getBloodGroup() == 11) {
                bloodGroup_Tv.setText("A+");
            } else if (patientDetailByID.getBloodGroup() == 8) {
                bloodGroup_Tv.setText("AB-");
            } else if (patientDetailByID.getBloodGroup() == 5) {
                bloodGroup_Tv.setText("AB+");
            } else if (patientDetailByID.getBloodGroup() == 3) {
                bloodGroup_Tv.setText("B-");
            } else if (patientDetailByID.getBloodGroup() == 4) {
                bloodGroup_Tv.setText("B+");
            } else if (patientDetailByID.getBloodGroup() == 7) {
                bloodGroup_Tv.setText("O-");
            } else if (patientDetailByID.getBloodGroup() == 13) {
                bloodGroup_Tv.setText("O+");
            } else {
                bloodGroup_Tv.setText(" ");
            }
//            medicalHistory_Tv.setText(patientDetailByID.getMedicalHistory().toString());
            if (patientDetailByID.getMedicalHistory() == 6) {
                medicalHistory_Tv.setText("Asthama");
            } else if (patientDetailByID.getMedicalHistory() == 3) {
                medicalHistory_Tv.setText("Asthma");
            } else if (patientDetailByID.getMedicalHistory() == 12) {
                medicalHistory_Tv.setText("Blood Pressure Issue");
            } else if (patientDetailByID.getMedicalHistory() == 9) {
                medicalHistory_Tv.setText("Breast Feeding");
            } else if (patientDetailByID.getMedicalHistory() == 2) {
                medicalHistory_Tv.setText("Diabetes");
            } else if (patientDetailByID.getMedicalHistory() == 4) {
                medicalHistory_Tv.setText("Epilepsy");
            } else if (patientDetailByID.getMedicalHistory() == 7) {
                medicalHistory_Tv.setText("Gasteric Ulcers");
            } else if (patientDetailByID.getMedicalHistory() == 5) {
                medicalHistory_Tv.setText("Heart Disease");
            } else if (patientDetailByID.getMedicalHistory() == 13) {
                medicalHistory_Tv.setText("Hypertension");
            } else if (patientDetailByID.getMedicalHistory() == 1) {
                medicalHistory_Tv.setText("No Health Issues");
            } else if (patientDetailByID.getMedicalHistory() == 11) {
                medicalHistory_Tv.setText("No Treatment History");
            } else if (patientDetailByID.getMedicalHistory() == 8) {
                medicalHistory_Tv.setText("Pregnant");
            } else if (patientDetailByID.getMedicalHistory() == 10) {
                medicalHistory_Tv.setText("Undergoing Any Treatment");
            } else {
                medicalHistory_Tv.setText(" ");
            }
            otherMedicalHistory_Tv.setText(patientDetailByID.getOtherMedicalHistory());

            Date birthdates = formatter.parse(date);
            age_Tv.setText(String.valueOf(calculateAgeResult(birthdates)));

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

    private void getAllDoctorsName(int userCliniId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<DoctorAvailableResponse> availableResponseCall = apiClientNetwork.getGetDoctorList(userCliniId);
            availableResponseCall.enqueue(new Callback<DoctorAvailableResponse>() {
                @Override
                public void onResponse(Call<DoctorAvailableResponse> call, Response<DoctorAvailableResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getDoctorAvailableData() != null) {
                                doctorAvailableData = response.body().getDoctorAvailableData();
                                if (doctorAvailableData.size() >= 0) {
                                    doctorAvailableNameArray = new String[response.body().getDoctorAvailableData().size()];
                                    for (int i = 0; i < response.body().getDoctorAvailableData().size(); i++) {
                                        doctorAvailIdlist.add(response.body().getDoctorAvailableData().get(i).getDoctorId().toString());
                                        doctorAvailableNameArray[i] = response.body().getDoctorAvailableData().get(i).getDoctorName();
                                    }
                                    ArrayAdapter<String> doctorNameAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, doctorAvailableNameArray);
                                    doctorNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    doctorNameSpinner.setAdapter(doctorNameAdapter);
                                    doctorNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            doctorAvailId = doctorAvailIdlist.get(i);
                                            doctorName = doctorAvailableNameArray[i];
//                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
//                                                getAllConsultationTypesMethod(consultationType);
//                                            } else {
//                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
//                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
                                }
                                if (NetworkStatus.isNetworkAvailable(mContext)) {
                                    getAllConsultationTypesMethod(consultationType);
                                } else {
                                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DoctorAvailableResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllConsultationTypesMethod(int consultationType) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ConsultationTypesResponse> consultationTypesResponseCall = apiClientNetwork.getAllConsultationTypes(consultationType);
            consultationTypesResponseCall.enqueue(new Callback<ConsultationTypesResponse>() {
                @Override
                public void onResponse(Call<ConsultationTypesResponse> call, Response<ConsultationTypesResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getConsultationTypesData() != null) {
                                consultationTypesData = response.body().getConsultationTypesData();
                                if (consultationTypesData.size() >= 0) {
                                    consultationTypesNameArray = new String[response.body().getConsultationTypesData().size()];
                                    for (int i = 0; i < response.body().getConsultationTypesData().size(); i++) {
                                        consultationTypesIdlist.add(response.body().getConsultationTypesData().get(i).getConsultationTypeId().toString());
                                        consultationTypesNameArray[i] = response.body().getConsultationTypesData().get(i).getConsultationType();
                                    }
                                    ArrayAdapter<String> consultationAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, consultationTypesNameArray);
                                    consultationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    consultationTypeSpinner.setAdapter(consultationAdapter);
                                    consultationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            consultationTypesId = consultationTypesIdlist.get(i);
                                            consultationTypesName = consultationTypesNameArray[i];
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getAllTimeSloteMethod(timeSlotId);
                                            } else {
                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Not fount consultation type name!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not fount consultation type name!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ConsultationTypesResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllTimeSloteMethod(int timeSlotId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<TimeSlotsResponse> slotsResponseCall = apiClientNetwork.getAllTimeSlots(timeSlotId);
            slotsResponseCall.enqueue(new Callback<TimeSlotsResponse>() {
                @Override
                public void onResponse(Call<TimeSlotsResponse> call, Response<TimeSlotsResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getTimeSloteData() != null) {
                                timeSloteData = response.body().getTimeSloteData();
                                if (timeSloteData.size() >= 0) {
                                    timeSloteNameArray = new String[response.body().getTimeSloteData().size()];
                                    for (int i = 0; i < response.body().getTimeSloteData().size(); i++) {
                                        timeSloteIdlist.add(response.body().getTimeSloteData().get(i).getAppTimeSlotsId().toString());
                                        timeSloteNameArray[i] = response.body().getTimeSloteData().get(i).getTimeFrom()
                                                + " - " + response.body().getTimeSloteData().get(i).getTimeTo();
                                    }
                                    ArrayAdapter<String> consultationAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, timeSloteNameArray);
                                    consultationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    timeSloteSpinner.setAdapter(consultationAdapter);
                                    timeSloteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            timeSloteId = timeSloteIdlist.get(i);
                                            timeSloteName = timeSloteNameArray[i];
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getAppointmentStatusListMethod();
                                            } else {
                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Not found time slotes!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found time slotes!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TimeSlotsResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAppointmentStatusListMethod() {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AppointmentStatusResponse> appointmentStatusResponseCall = apiClientNetwork.getAllAppointmentStatus();
            appointmentStatusResponseCall.enqueue(new Callback<AppointmentStatusResponse>() {
                @Override
                public void onResponse(Call<AppointmentStatusResponse> call, Response<AppointmentStatusResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getAppointmentStatusData() != null) {
                                appointmentStatusData = response.body().getAppointmentStatusData();
                                if (appointmentStatusData.size() >= 0) {
                                    appointmentStatusNameArray = new String[response.body().getAppointmentStatusData().size()];
                                    for (int i = 0; i < response.body().getAppointmentStatusData().size(); i++) {
                                        appointmentStatusIdlist.add(response.body().getAppointmentStatusData().get(i).getStatusID().toString());
                                        appointmentStatusNameArray[i] = response.body().getAppointmentStatusData().get(i).getStatusName();
                                    }
                                    ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, appointmentStatusNameArray);
                                    statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    appointmentStatusSpinner.setAdapter(statusAdapter);
                                    appointmentStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            appointmentStatusTypesId = appointmentStatusIdlist.get(i);
                                            appointmentStatusTypesName = appointmentStatusNameArray[i];
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Appointment status data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Appointment status data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppointmentStatusResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllCountryList() {
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
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            changeableTextNewExisting = (TextView) findViewById(R.id.changeableTextNewExisting);
            patientId_Tv = (TextView) findViewById(R.id.patientId_Tv);
            patientName_Tv = (TextView) findViewById(R.id.patientName_Tv);
            TvGender_Title = (TextView) findViewById(R.id.TvGender_Title);
            age_Tv = (TextView) findViewById(R.id.age_Tv);
            mobileNumber_Tv = (TextView) findViewById(R.id.mobileNumber_Tv);
            dateOfBirth_Tv = (TextView) findViewById(R.id.dateOfBirth_Tv);
            bloodGroup_Tv = (TextView) findViewById(R.id.bloodGroup_Tv);
            medicalHistory_Tv = (TextView) findViewById(R.id.medicalHistory_Tv);
            otherMedicalHistory_Tv = (TextView) findViewById(R.id.otherMedicalHistory_Tv);

            backMenu = (ImageView) findViewById(R.id.backMenu);
            calander_Im = (ImageView) findViewById(R.id.calander_Im);
            calanderDOB_Im = (ImageView) findViewById(R.id.calanderDOB_Im);
            checkNewPatients = (CheckBox) findViewById(R.id.checkNewPatients);
            checkExistingPatient = (CheckBox) findViewById(R.id.checkExistingPatient);
            maleCheck_Bt = (CheckBox) findViewById(R.id.maleCheck_Bt);
            femaleCheck_Bt = (CheckBox) findViewById(R.id.femaleCheck_Bt);

            titleSpinner = (Spinner) findViewById(R.id.titleSpinner);
            bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
            medicalHistorySpinner = (Spinner) findViewById(R.id.medicalHistorySpinner);
            countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
            stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
            citySpinner = (Spinner) findViewById(R.id.citySpinner);
            doctorNameSpinner = (Spinner) findViewById(R.id.doctorNameSpinner);
            consultationTypeSpinner = (Spinner) findViewById(R.id.consultationTypeSpinner);
            appointmentStatusSpinner = (Spinner) findViewById(R.id.appointmentStatusSpinner);
            timeSloteSpinner = (Spinner) findViewById(R.id.timeSloteSpinner);

            patientId_Et = (EditText) findViewById(R.id.patientId_Et);
            firstName_Et = (EditText) findViewById(R.id.firstName_Et);
            lastName_Et = (EditText) findViewById(R.id.lastName_Et);
            dateOfBirth_Pt = (EditText) findViewById(R.id.dateOfBirth_Pt);
            age_Et = (EditText) findViewById(R.id.age_Et);
            mobileNumber_Et = (EditText) findViewById(R.id.mobileNumber_Et);
            counrtyCodeNo_Et = (EditText) findViewById(R.id.counrtyCodeNo_Et);
            emailAddress_Et = (EditText) findViewById(R.id.emailAddress_Et);
            EtOtherMedicalHistory = (EditText) findViewById(R.id.EtOtherMedicalHistory);
            addressFirst_Et = (EditText) findViewById(R.id.addressFirst_Et);
            addressSecond_Et = (EditText) findViewById(R.id.addressSecond_Et);
            pinCode_Et = (EditText) findViewById(R.id.pinCode_Et);
            patientName_Et = (AutoCompleteTextView) findViewById(R.id.patientName_Et);
            appointmentDate_Et = (EditText) findViewById(R.id.appointmentDate_Et);
            notes_Et = (EditText) findViewById(R.id.notes_Et);

            btn_CreateAccount = (Button) findViewById(R.id.btn_CreateAccount);
            addAppointments_Btn = (Button) findViewById(R.id.addAppointments_Btn);
            cancel_Btn = (TextView) findViewById(R.id.cancel_Btn);

            addNewPatientLayout = (RelativeLayout) findViewById(R.id.addNewPatientLayout);
            addExistingPatientLayout = (RelativeLayout) findViewById(R.id.addExistingPatientLayout);

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

            case R.id.btn_CreateAccount:
                if (validateRegisterPatient()) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        str_otherMedicalHistory = EtOtherMedicalHistory.getText().toString();
                        if (str_otherMedicalHistory.isEmpty()) {
                            str_otherMedicalHistory = "";
                        }
                        str_emailId = emailAddress_Et.getText().toString();
                        if (str_emailId.isEmpty()) {
                            str_emailId = "";
                        }
                        str_pinCode = pinCode_Et.getText().toString();
                        if (str_pinCode.isEmpty()) {
                            str_pinCode = "";
                        }
                        str_address1 = addressFirst_Et.getText().toString();
                        if (str_address1.isEmpty()) {
                            str_address1 = "";
                        }
                        str_address2 = addressSecond_Et.getText().toString();
                        if (str_address2.isEmpty()) {
                            str_address2 = "";
                        }
                        registerPatientAppointment(allTitleId, patientId_Et.getText().toString(), firstName_Et.getText().toString(),
                                lastName_Et.getText().toString(), dateOfBirth_Pt.getText().toString(), age_Et.getText().toString(),
                                bloodId, maleFemale, counrtyCodeNo_Et.getText().toString(), mobileNumber_Et.getText().toString(),
                                str_emailId, medicalId, str_otherMedicalHistory, str_address1, str_address2, countryId, stateId,
                                cityId, str_pinCode, sessionManager.getUserCliniId());
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.addAppointments_Btn:
                if (validateAddAppointment()) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        str_Notes = notes_Et.getText().toString();
                        if (str_Notes.isEmpty()) {
                            str_Notes = "";
                        }
                        addAppointment(patientId, Integer.valueOf(sessionManager.getUserCliniId()), sessionManager.getLoginUserId(),
                                appointmentDate_Et.getText().toString(), consultationTypesId, appointmentStatusTypesId, timeSloteId,
                                str_Notes);
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.cancel_Btn:
                onBackPressed();
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

    private void updateDate() {
        try {
            String myFormat = "dd-MMM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            appointmentDate_Et.setText(sdf.format(myCalendar.getTime()));
            appointmentDate_Et.setClickable(false);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void addAppointment(int patientId, int userCliniId, String loginUserId, String appointmentDates,
                                String consultationTypesId, String statusId, String appointmentTimeSlote, String notes) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> clinicRegisterResponseCall = apiClientNetwork.getManageAppAppointments(patientId, userCliniId,
                    doctorAvailId, appointmentDates, consultationTypesId, statusId, Integer.valueOf(appointmentTimeSlote), notes, loginUserId);
            clinicRegisterResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                int successData = response.body().getData();
                                getAlertDialogForSuccess(patientId);
                            }
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

    private void getAlertDialogForSuccess(int successData) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.success_layout);

            TextView Tvlogin_TitleOld = (TextView) dialog.findViewById(R.id.Tvlogin_TitleOld);
            Tvlogin_TitleOld.setText("Appointment created successfully");
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_verifyOTP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AppointmentDetailsActivity.class);
                    intent.putExtra("PatientId", successData);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void registerPatientAppointment(String titleId, String patientId, String firstName, String lastName, String dateOfBirth,
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
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.createPatientRegistration(patientId, titleId,
                    firstName, lastName, maleFemaleCheck, "", age, emailAddress, mobileNumber, addressFirst,
                    addressSecond, cityId, stateId, countryId, pinCode, counrtyCodeNo, sessionManager.getLoginUserId(),
                    dateOfBirth, bloodId, medicalId, otherMedicalHistory, "", String.valueOf(clinicId));

            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                int patientData = response.body().getData();
                                getAlertDialogForPatientAppointmentSuccess(patientData);
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

    private void getAlertDialogForPatientAppointmentSuccess(int patientData) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.success_layout);

            TextView Tvlogin_TitleOld = (TextView) dialog.findViewById(R.id.Tvlogin_TitleOld);
            Tvlogin_TitleOld.setText("Your appointment added successfully");
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_verifyOTP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkExistingPatient.setChecked(true);
                    if (checkExistingPatient.isChecked() == true) {
                        changeableTextNewExisting.setText("Enter Patient Appointment Details To Book Appountment");
                        checkNewPatients.setChecked(false);
                        checkExistingPatient.setChecked(true);
                        addExistingPatientLayout.setVisibility(View.VISIBLE);
                        addNewPatientLayout.setVisibility(View.GONE);
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            getPatientSearchList(sessionManager.getUserCliniId());
                            getAllDoctorsName(sessionManager.getUserCliniId());
                            getPatientDetailByID(patientData);
                        } else {
                            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private boolean validateAddAppointment() {
        if (appointmentDate_Et.getText().toString().isEmpty()) {
            appointmentDate_Et.setError("Enter appointment date");
            appointmentDate_Et.requestFocus();
            return false;
        }
//        else if (notes_Et.getText().toString().isEmpty()) {
//            notes_Et.setError("Something type here");
//            notes_Et.requestFocus();
//            return true;
//        }
        appointmentDate_Et.getText().toString();
        return true;
    }

    private boolean validateRegisterPatient() {
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
        return true;
    }
}
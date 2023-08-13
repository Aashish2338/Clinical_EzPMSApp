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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AppointmentStatusData;
import com.ezpms.ezpmsapp.Models.ConsultationTypesData;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.Models.PatientDetailByID;
import com.ezpms.ezpmsapp.Models.TimeSloteData;
import com.ezpms.ezpmsapp.Patients.PatientListActivity;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AppointmentStatusResponse;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.ResponseModels.ConsultationTypesResponse;
import com.ezpms.ezpmsapp.ResponseModels.DoctorAvailableResponse;
import com.ezpms.ezpmsapp.ResponseModels.PatientDetailByIDResponse;
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

public class AppointmentsRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, selectPatient_TvM, doctorName_TvM, consultationType_TvM, appointmentStatus_TvM, appointmentDate_TvM, timeSlote_TvM, cancel_Btn;
    private ImageView backMenu, calander_Im;
    private Spinner doctorNameSpinner, consultationTypeSpinner, appointmentStatusSpinner, timeSloteSpinner;
    private EditText appointmentDate_Et, notes_Et;
    private AutoCompleteTextView patientName_Et;
    private TextView patientId_Tv, patientName_Tv, TvGender_Title, age_Tv, mobileNumber_Tv, dateOfBirth_Tv, bloodGroup_Tv;
    private TextView medicalHistory_Tv, otherMedicalHistory_Tv;
    private Button addAppointments_Btn;
    private int consultationType = 0, timeSlotId = 0, patientId;
    private List<DoctorAvailableData> doctorAvailableData = null;
    private List<ConsultationTypesData> consultationTypesData = null;
    private List<TimeSloteData> timeSloteData = null;
    private List<AppointmentStatusData> appointmentStatusData = null;
    private String[] doctorAvailableNameArray;
    private String[] consultationTypesNameArray;
    private String[] timeSloteNameArray;
    private String[] appointmentStatusNameArray;
    private String doctorName = "", timeSloteName = "", consultationTypesName = "", appointmentStatusTypesName = "";
    private String doctorAvailId, consultationTypesId, timeSloteId, statusId, appointmentStatusTypesId;
    private List<String> doctorAvailIdlist = new ArrayList<>();
    private List<String> consultationTypesIdlist = new ArrayList<>();
    private List<String> timeSloteIdlist = new ArrayList<>();
    private List<String> appointmentStatusIdlist = new ArrayList<>();
    private SessionManager sessionManager;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_registration);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();
        getTextLayoutUiId();
        setCurrentDate();
        setTextMandatoryLayout();
        Tv_Title.setText("Appointment Registration");

        patientName_Et.setEnabled(false);
        patientName_Et.setClickable(false);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            patientId = extra.getInt("PatientId");
        }

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getPatientDetailByID(patientId);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        backMenu.setOnClickListener(this);
        addAppointments_Btn.setOnClickListener(this);
        cancel_Btn.setOnClickListener(this);
        calander_Im.setOnClickListener(this);
    }

    private void setCurrentDate() {
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            appointmentDate_Et.setText(formattedDate);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getTextLayoutUiId() {
        try {
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

    private void getPatientDetailByID(int patientId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("PLease wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<PatientDetailByIDResponse> patientDetailByIDResponseCall = apiClientNetwork.getPatientDetailByID(patientId);
            patientDetailByIDResponseCall.enqueue(new Callback<PatientDetailByIDResponse>() {
                @Override
                public void onResponse(Call<PatientDetailByIDResponse> call, Response<PatientDetailByIDResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getPatientDetailByID() != null) {
                                setPatientDetailsInTextView(response.body().getPatientDetailByID());
                                if (NetworkStatus.isNetworkAvailable(mContext)) {
                                    getAllDoctorsName(sessionManager.getUserCliniId());
                                } else {
                                    Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(mContext, "Not found patient details!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<PatientDetailByIDResponse> call, Throwable t) {
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

    private void setPatientDetailsInTextView(PatientDetailByID patientDetailByID) {
        try {
            patientName_Et.setText(patientDetailByID.getPatientName() + "/" + patientDetailByID.getPhoneNumber());

            int lenght = String.valueOf(patientDetailByID.getPatientId()).length();
            patientId = patientDetailByID.getPatientId();
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
                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getAllConsultationTypesMethod(consultationType);
                                            } else {
                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
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

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
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

            doctorNameSpinner = (Spinner) findViewById(R.id.doctorNameSpinner);
            consultationTypeSpinner = (Spinner) findViewById(R.id.consultationTypeSpinner);
            appointmentStatusSpinner = (Spinner) findViewById(R.id.appointmentStatusSpinner);
            timeSloteSpinner = (Spinner) findViewById(R.id.timeSloteSpinner);

            patientName_Et = (AutoCompleteTextView) findViewById(R.id.patientName_Et);
            appointmentDate_Et = (EditText) findViewById(R.id.appointmentDate_Et);
            notes_Et = (EditText) findViewById(R.id.notes_Et);

            addAppointments_Btn = (Button) findViewById(R.id.addAppointments_Btn);
            cancel_Btn = (TextView) findViewById(R.id.cancel_Btn);

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

            case R.id.addAppointments_Btn:
                if (validateAddAppointment()) {
                    addAppointment(patientId, Integer.valueOf(sessionManager.getUserCliniId()), sessionManager.getLoginUserId(),
                            appointmentDate_Et.getText().toString(), consultationTypesId, appointmentStatusTypesId, timeSloteId,
                            notes_Et.getText().toString());
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
//                    Intent intent = new Intent(mContext, AppointmentDetailsActivity.class);
                    Intent intent = new Intent(mContext, PatientListActivity.class);
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

    private boolean validateAddAppointment() {
        if (appointmentDate_Et.getText().toString().isEmpty()) {
            appointmentDate_Et.setError("Enter appointment date");
            appointmentDate_Et.requestFocus();
            return false;
        } else if (notes_Et.getText().toString().isEmpty()) {
            notes_Et.setError("Something type here");
            notes_Et.requestFocus();
            return true;
        }
        return true;
    }
}
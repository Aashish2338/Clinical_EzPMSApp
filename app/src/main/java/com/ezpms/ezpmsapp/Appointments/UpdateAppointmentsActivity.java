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

public class UpdateAppointmentsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, patientName_Et, patientId_Tv, patientName_Tv, TvGender_Title, age_Tv, cancel_Btn;
    private TextView mobileNumber_Tv, dateOfBirth_Tv, bloodGroup_Tv, medicalHistory_Tv, otherMedicalHistory_Tv;
    private TextView patientNameMobile_TvM, doctorName_TvM, consultationType_TvM, appointmentStatus_TvM, appointmentDate_TvM, timeSlote_TvM;
    private ImageView backMenu, calander_Im;
    private Spinner doctorNameSpinner, consultationTypeSpinner, appointmentStatusSpinner, timeSloteSpinner;
    private EditText appointmentDate_Et, notes_Et;
    private Button updateAppointments_Btn;
    private SessionManager sessionManager;
    private int consultationType = 0, timeSlotId = 0, patientId, cliniId, appointmentId, appointmentStatusId, appAppointmentId;
    private List<DoctorAvailableData> doctorAvailableData = null;
    private List<ConsultationTypesData> consultationTypesData = null;
    private List<TimeSloteData> timeSloteData = null;
    private List<AppointmentStatusData> appointmentStatusData = null;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private String dateOfBirth, timeSloteId, doctorAvailId, consultationTypesId, appointmentStatusTypesId, dateOfAppointment, keyNotes, str_appointmentDate = "";
    private String doctorName = "", timeSloteName = "", consultationTypesName = "", appointmentStatusTypesName = "", str_notes;
    private String[] doctorAvailableNameArray;
    private String[] consultationTypesNameArray;
    private String[] timeSloteNameArray;
    private String[] appointmentStatusNameArray;
    private List<String> doctorAvailIdlist = new ArrayList<>();
    private List<String> consultationTypesIdlist = new ArrayList<>();
    private List<String> timeSloteIdlist = new ArrayList<>();
    private List<String> appointmentStatusIdlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointments);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();
        getTextLayoutUiId();
        setTextMandatoryLayout();
        Tv_Title.setText("Update Appointment");

        backMenu.setOnClickListener(this);
        calander_Im.setOnClickListener(this);
        updateAppointments_Btn.setOnClickListener(this);
        cancel_Btn.setOnClickListener(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            patientId = extra.getInt("PatientId");
            appAppointmentId = extra.getInt("AppAppointmentId");
            cliniId = extra.getInt("ClinicId");
            appointmentId = extra.getInt("AppointmentId");
            appointmentStatusId = extra.getInt("AppointmentStatus");
            dateOfAppointment = extra.getString("Date");
            keyNotes = extra.getString("KeyNotes");
        } else {
            cliniId = sessionManager.getUserCliniId();
            dateOfAppointment = "";
            keyNotes = "";
            patientId = sessionManager.getPatientId();
            appointmentId = sessionManager.getAppointmentId();
        }

        appointmentDate_Et.setText(dateOfAppointment);
        notes_Et.setText(keyNotes);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getPatientDetailByID(patientId);
            getAllDoctorsName(sessionManager.getUserCliniId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void getTextLayoutUiId() {
        try {
            patientNameMobile_TvM = (TextView) findViewById(R.id.patientNameMobile_TvM);
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
            String patientNameMobile_Tv = "Patient Name/Mob No.<font color='red'>*</font>";
            patientNameMobile_TvM.setText(Html.fromHtml(patientNameMobile_Tv), TextView.BufferType.SPANNABLE);

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
            progressDialog.setMessage("Please wait...");
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

            sessionManager.setPatientId(patientDetailByID.getPatientId());
            patientName_Tv.setText(patientDetailByID.getPatientName());
            TvGender_Title.setText(patientDetailByID.getGender());
            mobileNumber_Tv.setText(patientDetailByID.getPhoneNumber());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            String date = formatter.format(Date.parse(patientDetailByID.getDob()));
            dateOfBirth_Tv.setText(date);

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
                                    Toast.makeText(mContext, "Not found consultation type name!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found consultation type name!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
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
//                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
//                                                getAppointmentStatusListMethod();
//                                            } else {
//                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
//                                            }
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

                                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                                getAllTimeSloteMethod(timeSlotId);
                                            } else {
                                                Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                                            }

                                            if (appointmentStatusTypesId.equalsIgnoreCase("1") &&
                                                    appointmentStatusTypesName.equalsIgnoreCase("Scheduled")) {
                                                appointmentDate_Et.setText(dateOfAppointment);
                                            } else {
                                                Date c = Calendar.getInstance().getTime();
                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                                String formattedDate = df.format(c);
                                                appointmentDate_Et.setText(formattedDate);
                                            }
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
            cancel_Btn = (TextView) findViewById(R.id.cancel_Btn);
            patientName_Et = (TextView) findViewById(R.id.patientName_Et);
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

            appointmentDate_Et = (EditText) findViewById(R.id.appointmentDate_Et);
            notes_Et = (EditText) findViewById(R.id.notes_Et);

            updateAppointments_Btn = (Button) findViewById(R.id.updateAppointments_Btn);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            str_notes = notes_Et.getText().toString();
            if (str_notes.isEmpty()) {
                str_notes = "";
            }
            switch (view.getId()) {
                case R.id.backMenu:
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

                case R.id.updateAppointments_Btn:
                    if (validate()) {
                        getUpdateAppointments(doctorAvailId, consultationTypesId, appointmentStatusTypesId, str_appointmentDate,
                                timeSloteId, str_notes);
                    }
                    break;

                case R.id.cancel_Btn:
                    if (validateCancel()) {
                        getCancelAppointments(doctorAvailId, consultationTypesId, appointmentStatusTypesId, str_appointmentDate,
                                timeSloteId, str_notes);
                    }
                    break;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void updateDate() {
        try {
            String myFormat = "dd-MMM-yyyy";
            String myFormat1 = "yyyy-mm-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
            str_appointmentDate = sdf1.format(myCalendar.getTime());
            appointmentDate_Et.setText(sdf.format(myCalendar.getTime()));
            dateOfBirth = sdf.format(myCalendar.getTime());
            appointmentDate_Et.setClickable(false);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getUpdateAppointments(String doctorAvailId, String consultationTypesId, String statusId,
                                       String appointmentDate, String timeSlote, String notes) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.getRescheduledAppointments(appointmentId, patientId, sessionManager.getUserCliniId(),
                    doctorAvailId, appointmentDate, consultationTypesId, statusId, Integer.valueOf(timeSlote), notes, sessionManager.getLoginUserId());
            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
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
            dialog.setContentView(R.layout.update_appoinrments_layout);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_Ok);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.setRegisterClinicId(data);
                    Intent intent = new Intent(mContext, AppointmentDetailsActivity.class);
                    intent.putExtra("PatientId", patientId);
                    intent.putExtra("AppAppointmentId", appAppointmentId);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getCancelAppointments(String doctorAvailId, String consultationTypesId, String statusId,
                                       String appointmentDate, String timeSloteId, String timeSlote) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> responseCall = apiClientNetwork.getCancelAppAppointments(sessionManager.getAppointmentId(),
                    sessionManager.getPatientId(), sessionManager.getClinicId(), doctorAvailId, statusId, Integer.valueOf(timeSlote),
                    "", sessionManager.getLoginUserId());
            responseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Appointment Cancel Successfull!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
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

    private boolean validate() {
        try {
            if (appointmentDate_Et.getText().toString().trim().isEmpty()) {
                appointmentDate_Et.setError("Enter Appointment Date");
                appointmentDate_Et.requestFocus();
                return false;
            } else if (str_appointmentDate.equals("")) {
                Toast.makeText(mContext, "Please select appointment Date!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return true;
    }

    private boolean validateCancel() {
        try {
            if (appointmentDate_Et.getText().toString().trim().isEmpty()) {
                appointmentDate_Et.setError("Enter Appointment Date");
                appointmentDate_Et.requestFocus();
                return false;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
        return false;
    }
}
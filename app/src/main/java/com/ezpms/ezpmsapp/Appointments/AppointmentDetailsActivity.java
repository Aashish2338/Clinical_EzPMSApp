package com.ezpms.ezpmsapp.Appointments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.TreatmentDetailPatientData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.TreatmentDetailPatientResponse;
import com.ezpms.ezpmsapp.Treatments.NewTreatmentsActivity;
import com.ezpms.ezpmsapp.Treatments.TreatmentsHistoryActivity;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ezpms.ezpmsapp.Utilities.ConfigurationData.Base_Url;

public class AppointmentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, PatientName, PatientMobileNo_Tv, doctorName_TvM, status_Tvm, appointmentDate_TvM;
    private TextView appointmentTime_TvM, patientType_TvM, txt_Symptoms, txt_treatment, txt_medPrescribed;
    private ImageView backMenu;
    private LinearLayout appointments, treatments, newTreatment, updateAppointments;
    private SessionManager sessionManager;
    private SimpleDateFormat formatter;
    private String date;
    private int patientId;
    private RequestQueue requestQueue;
    private int AppointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        mContext = this;
        sessionManager = new SessionManager(mContext);
        requestQueue = Volley.newRequestQueue(this);
        getLayoutUiId();
        Tv_Title.setText("Appointment Details");
        appointments.setBackgroundResource(R.color.oldCanBtn);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            patientId = extra.getInt("PatientId");
            AppointmentId = extra.getInt("AppAppointmentId");
        } else {
            patientId = sessionManager.getPatientId();
            AppointmentId = sessionManager.getAppointmentId();
        }

        backMenu.setOnClickListener(this);
        appointments.setOnClickListener(this);
        treatments.setOnClickListener(this);
        newTreatment.setOnClickListener(this);
        updateAppointments.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getPatientDetailsById(patientId);
            getAppointmentAPI(patientId);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void getPatientDetailsById(int patientId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<TreatmentDetailPatientResponse> patientResponseCall = apiClientNetwork.getTreatmentDetailByPatientID(patientId);
            patientResponseCall.enqueue(new Callback<TreatmentDetailPatientResponse>() {
                @Override
                public void onResponse(Call<TreatmentDetailPatientResponse> call, Response<TreatmentDetailPatientResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getTreatmentDetailPatientData() != null) {
//                                setAllTextFields(response.body().getTreatmentDetailPatientData());
                                setAllTextField(response.body().getTreatmentDetailPatientData());
                            } else {
                                Toast.makeText(mContext, "Patient Detail Not Found!", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TreatmentDetailPatientResponse> call, Throwable t) {
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

    private void setAllTextField(TreatmentDetailPatientData treatmentDetailPatientData) {
        txt_Symptoms.setText("" + treatmentDetailPatientData.getDiseaseSymptoms());
        txt_treatment.setText("" + treatmentDetailPatientData.getDiseaseTreatment());
        txt_medPrescribed.setText("" + treatmentDetailPatientData.getDiseaseMedicinePrescribed());

    }

    private void setAllTextFields(TreatmentDetailPatientData treatmentDetailPatientData) {
        try {
            if (treatmentDetailPatientData.getPatientFirstName() != null && treatmentDetailPatientData.getPatientLastName() != null) {
                PatientName.setText(treatmentDetailPatientData.getPatientFirstName() + " " + treatmentDetailPatientData.getPatientLastName());
            } else {
                PatientName.setText(" ");
            }

            if (treatmentDetailPatientData.getPhoneNumber() != null) {
                PatientMobileNo_Tv.setText(treatmentDetailPatientData.getPhoneNumber());
            } else {
                PatientMobileNo_Tv.setText(" ");
            }

            if (treatmentDetailPatientData.getDoctorName() != null) {
                doctorName_TvM.setText(treatmentDetailPatientData.getDoctorName());
            } else {
                doctorName_TvM.setText(" ");
            }

            if (treatmentDetailPatientData.getAppointmentDate() != null) {
                formatter = new SimpleDateFormat("dd-MMM-yyyy");
                date = formatter.format(Date.parse(treatmentDetailPatientData.getAppointmentDate()));
                appointmentDate_TvM.setText(date);
            } else {
                appointmentDate_TvM.setText(" ");
            }

            if (treatmentDetailPatientData.getTimeFrom() != null && treatmentDetailPatientData.getTimeTo() != null) {
                appointmentTime_TvM.setText(treatmentDetailPatientData.getTimeFrom() + " - " + treatmentDetailPatientData.getTimeTo());
            } else {
                appointmentTime_TvM.setText("");
            }

            if (treatmentDetailPatientData.getConsultationTypeName() != null) {
                patientType_TvM.setText(treatmentDetailPatientData.getConsultationTypeName());
            } else {
                patientType_TvM.setText("");
            }

            if (treatmentDetailPatientData.getAppointmentStatus() == 1) {
                status_Tvm.setText("Scheduled");
            } else if (treatmentDetailPatientData.getAppointmentStatus() == 2) {
                status_Tvm.setText("Completed");
            } else if (treatmentDetailPatientData.getAppointmentStatus() == 3) {
                status_Tvm.setText("Cancelled");
            } else if (treatmentDetailPatientData.getAppointmentStatus() == 4) {
                status_Tvm.setText("Re-Scheduled");
            } else if (treatmentDetailPatientData.getAppointmentStatus() == 5) {
                status_Tvm.setText("Pending");
            } else if (treatmentDetailPatientData.getAppointmentStatus() == 6) {
                status_Tvm.setText("With Doctor");
            } else if (treatmentDetailPatientData.getAppointmentStatus() == 7) {
                status_Tvm.setText("Complete Today & Schedule Next Visit");
            } else {
                status_Tvm.setText(" ");
            }

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            PatientName = (TextView) findViewById(R.id.PatientName);
            PatientMobileNo_Tv = (TextView) findViewById(R.id.PatientMobileNo_Tv);
            doctorName_TvM = (TextView) findViewById(R.id.doctorName_TvM);
            status_Tvm = (TextView) findViewById(R.id.status_Tvm);
            appointmentDate_TvM = (TextView) findViewById(R.id.appointmentDate_TvM);
            appointmentTime_TvM = (TextView) findViewById(R.id.appointmentTime_TvM);
            patientType_TvM = (TextView) findViewById(R.id.patientType_TvM);

            txt_Symptoms = (TextView) findViewById(R.id.txt_Symptoms);
            txt_medPrescribed = (TextView) findViewById(R.id.txt_medPrescribed);
            txt_treatment = (TextView) findViewById(R.id.txt_treatment);

            backMenu = (ImageView) findViewById(R.id.backMenu);

            appointments = (LinearLayout) findViewById(R.id.appointments);
            treatments = (LinearLayout) findViewById(R.id.treatments);
            newTreatment = (LinearLayout) findViewById(R.id.newTreatment);
            updateAppointments = (LinearLayout) findViewById(R.id.updateAppointments);
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

            case R.id.appointments:
                appointments.setBackgroundResource(R.color.oldCanBtn);
                Tv_Title.setText("Appointment Details");
                break;

            case R.id.treatments:
                Intent intent = new Intent(mContext, TreatmentsHistoryActivity.class);
                intent.putExtra("AppAppointmentId", AppointmentId);
                intent.putExtra("PatientId", patientId);
                startActivity(intent);
                break;

            case R.id.newTreatment:
                Intent i = new Intent(mContext, NewTreatmentsActivity.class);
                i.putExtra("AppAppointmentId", AppointmentId);
                i.putExtra("PatientId", patientId);
                startActivity(i);
                break;

            case R.id.updateAppointments:
                Intent intent1 = new Intent(mContext, UpdateAppointmentsActivity.class);
                intent1.putExtra("AppAppointmentId", AppointmentId);
                intent1.putExtra("PatientId", patientId);
                startActivity(intent1);
                break;
        }
    }

    private void getAppointmentAPI(int patientID) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url + "GetAppointmentByPatientId?PatientID=" + patientID, null, new com.android.volley.Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Orderdeatil:", "" + response);

                try {
                    String RespCode = response.getString("RespCode");
                    String RespMsg = response.getString("RespMsg");
                    if (RespMsg.equals("SUCCESS")) {

                        JSONArray jsonArray = response.getJSONArray("DATA");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            AppointmentId = jObj.getInt("AppointmentId");
                            String PatientFirstName = jObj.getString("PatientFirstName");
                            String PatientLastName = jObj.getString("PatientLastName");
                            String AppointmentDate = jObj.getString("AppointmentDate");
                            String DoctorName = jObj.getString("DoctorName");
                            String KeyNotes = jObj.getString("KeyNotes");
                            String PhoneNumber = jObj.getString("PhoneNumber");
                            String TimeFrom = jObj.getString("TimeFrom");
                            String TimeTo = jObj.getString("TimeTo");
                            String ConsultationTypeName = jObj.getString("ConsultationTypeName");
                            int AppointmentStatus = jObj.getInt("AppointmentStatus");

                            if (AppointmentStatus == 1) {
                                status_Tvm.setText("Scheduled");
                            } else if (AppointmentStatus == 2) {
                                status_Tvm.setText("Consultation Fully Completed");
                            } else if (AppointmentStatus == 3) {
                                status_Tvm.setText("Completed & Schedule Next Visit");
                            }

                            PatientName.setText(PatientFirstName + " " + PatientLastName);
                            PatientMobileNo_Tv.setText(PhoneNumber);
                            if (DoctorName != "null") {
                                doctorName_TvM.setText(DoctorName);
                            } else {
                                doctorName_TvM.setText("Not available");
                            }

                            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            SimpleDateFormat outputFormat = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                outputFormat = new SimpleDateFormat("dd MMM YYYY");
                            }

                            Date date = null;
                            String str = null;
                            try {
                                date = inputFormat.parse(AppointmentDate);
                                str = outputFormat.format(date);
                                appointmentDate_TvM.setText(str);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (TimeFrom != "null" && TimeTo != "null") {
                                appointmentTime_TvM.setText(TimeFrom + " - " + TimeTo);
                            } else {
                                appointmentTime_TvM.setText("Time not given");
                            }
                            patientType_TvM.setText(ConsultationTypeName);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), RespMsg.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("LoginDataE:", "" + e.toString());
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Orderdeatil:", "" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
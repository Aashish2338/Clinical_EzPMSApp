package com.ezpms.ezpmsapp.Treatments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ezpms.ezpmsapp.Appointments.AppointmentDetailsActivity;
import com.ezpms.ezpmsapp.Appointments.UpdateAppointmentsActivity;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.TreatmentDetailPatientData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.TreatmentDetailPatientResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ezpms.ezpmsapp.Utilities.ConfigurationData.Base_Url;

public class TreatmentsHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, textTreatmentDetails, appointmentsDate_Tv;
    private ImageView backMenu, calander_Im;
    private EditText selectDate_Et, symptomsName_Et, treatment_Et, medicinePrescribed_Et;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private int patientId, appAppointmentId, treatmentId, appointmentId;
    private String dateOfAppointment;
    private SessionManager sessionManager;
    private Button btn_AppointmentDetails, btn_NewTreatment, btn_UpdateAppointment;
    RequestQueue requestQueue;
    TextView txt_xRay, text_Prescription, txt_Others;
    ImageView img_xRay, img_Prescription, img_Others;
    List<String> xRayList = new ArrayList<>();
    List<String> prescriptionList = new ArrayList<>();
    List<String> OtherDocList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments_history);
        mContext = this;
        sessionManager = new SessionManager(mContext);
        requestQueue = Volley.newRequestQueue(this);
        getLayoutUiId();
        Tv_Title.setText("Treatments History");

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            patientId = extra.getInt("PatientId");
            appointmentId = extra.getInt("AppointmentId");
            treatmentId = extra.getInt("TreatmentId");
            appAppointmentId = extra.getInt("AppAppointmentId");
        } else {
            patientId = sessionManager.getPatientId();
            treatmentId = 0;
            appointmentId = 0;
            appAppointmentId = 0;
        }

        backMenu.setOnClickListener(this);
        calander_Im.setOnClickListener(this);
        btn_AppointmentDetails.setOnClickListener(this);
        btn_NewTreatment.setOnClickListener(this);
        btn_UpdateAppointment.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getTreatmentDetailsById(patientId);
            getPatientDocumentsAPI(patientId);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void getTreatmentDetailsById(int patientId) {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<TreatmentDetailPatientResponse> patientResponseCall = apiClientNetwork.getTreatmentDetailByPatientID(patientId);
            patientResponseCall.enqueue(new Callback<TreatmentDetailPatientResponse>() {
                @Override
                public void onResponse(Call<TreatmentDetailPatientResponse> call, Response<TreatmentDetailPatientResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getTreatmentDetailPatientData() != null) {
                                setAllTextFields(response.body().getTreatmentDetailPatientData());
                            } else {
                                Toast.makeText(mContext, "Patient Treatment Detail Data Not Found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(mContext, "Treatment History Not found!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TreatmentDetailPatientResponse> call, Throwable t) {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setAllTextFields(TreatmentDetailPatientData treatmentDetailPatientData) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            String date = formatter.format(Date.parse(treatmentDetailPatientData.getAppointmentDate()));
            textTreatmentDetails.setText("Treatment Details Scheduled Appointment Date : " + date);
            selectDate_Et.setText(date);
            selectDate_Et.setEnabled(false);
            selectDate_Et.setClickable(false);
            selectDate_Et.setFocusable(false);
            symptomsName_Et.setText(treatmentDetailPatientData.getDiseaseSymptoms());
            symptomsName_Et.setEnabled(false);
            symptomsName_Et.setClickable(false);
            symptomsName_Et.setFocusable(false);
            treatment_Et.setText(treatmentDetailPatientData.getDiseaseTreatment());
            treatment_Et.setEnabled(false);
            treatment_Et.setClickable(false);
            treatment_Et.setFocusable(false);
            medicinePrescribed_Et.setText(treatmentDetailPatientData.getDiseaseMedicinePrescribed());
            medicinePrescribed_Et.setEnabled(false);
            medicinePrescribed_Et.setClickable(false);
            medicinePrescribed_Et.setFocusable(false);
            appointmentsDate_Tv.setText(treatmentDetailPatientData.getConsultationTypeName() + ", \n Appointments Date : " + date);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            textTreatmentDetails = (TextView) findViewById(R.id.textTreatmentDetails);
            appointmentsDate_Tv = (TextView) findViewById(R.id.appointmentsDate_Tv);

            txt_Others = (TextView) findViewById(R.id.txt_Others);
            text_Prescription = (TextView) findViewById(R.id.text_Prescription);
            txt_xRay = (TextView) findViewById(R.id.txt_xRay);

            img_xRay = (ImageView) findViewById(R.id.img_xRay);
            img_Prescription = (ImageView) findViewById(R.id.img_Prescription);
            img_Others = (ImageView) findViewById(R.id.img_Others);

            backMenu = (ImageView) findViewById(R.id.backMenu);
            calander_Im = (ImageView) findViewById(R.id.calander_Im);

            selectDate_Et = (EditText) findViewById(R.id.selectDate_Et);
            symptomsName_Et = (EditText) findViewById(R.id.symptomsName_Et);
            treatment_Et = (EditText) findViewById(R.id.treatment_Et);
            medicinePrescribed_Et = (EditText) findViewById(R.id.medicinePrescribed_Et);

            btn_AppointmentDetails = (Button) findViewById(R.id.btn_AppointmentDetails);
            btn_NewTreatment = (Button) findViewById(R.id.btn_NewTreatment);
            btn_UpdateAppointment = (Button) findViewById(R.id.btn_UpdateAppointment);

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

                case R.id.btn_AppointmentDetails:
                    Intent intent1 = new Intent(mContext, AppointmentDetailsActivity.class);
                    intent1.putExtra("PatientId", patientId);
                    startActivity(intent1);
                    break;

                case R.id.btn_NewTreatment:
                    Intent intent2 = new Intent(mContext, NewTreatmentsActivity.class);
                    intent2.putExtra("PatientId", patientId);
                    intent2.putExtra("AppAppointmentId", appAppointmentId);
                    startActivity(intent2);
                    break;

                case R.id.btn_UpdateAppointment:
                    Intent intent3 = new Intent(mContext, UpdateAppointmentsActivity.class);
                    intent3.putExtra("PatientId", patientId);
                    startActivity(intent3);
                    break;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void updateDate() {
        try {
            String myFormat = "dd-MMM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            selectDate_Et.setText(sdf.format(myCalendar.getTime()));
            dateOfAppointment = sdf.format(myCalendar.getTime());
            selectDate_Et.setClickable(false);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getPatientDocumentsAPI(int patientID) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url + "GetAllDocumentBypatient?PatientID=" + patientID, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Orderdeatil:", "" + response);

                try {
                    String RespCode = response.getString("RespCode");
                    String RespMsg = response.getString("RespMsg");
                    if (RespMsg.equals("SUCCESS")) {
                        xRayList.clear();
                        prescriptionList.clear();
                        OtherDocList.clear();
                        JSONArray jsonArray = response.getJSONArray("DATA");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = jsonArray.getJSONObject(i);
                                int documentId = jObj.getInt("PatientDocumentUploadId");
                                String ImageName = jObj.getString("ImageName");
                                String ImageInByte = jObj.getString("ImageInByte");
                                String DocumentTypeName = jObj.getString("DocumentTypeName");

                                if (DocumentTypeName.equals("X-Rays")) {
                                    xRayList.add("XRay");
                                    if (xRayList.size() > 1) {

                                    } else {
                                        if (ImageInByte.equals("0")) {
                                            img_xRay.setVisibility(View.GONE);
                                            txt_xRay.setVisibility(View.VISIBLE);
                                        } else {
                                            img_xRay.setVisibility(View.VISIBLE);
                                            txt_xRay.setVisibility(View.GONE);
                                            convertXRayIntoImage(ImageInByte);
                                        }
                                    }
                                } else if (DocumentTypeName.equals("Others")) {
                                    OtherDocList.add("Others");
                                    if (OtherDocList.size() > 1) {

                                    } else {
                                        if (ImageInByte.equals("0")) {
                                            img_Others.setVisibility(View.GONE);
                                            txt_Others.setVisibility(View.VISIBLE);
                                        } else {
                                            img_Others.setVisibility(View.VISIBLE);
                                            txt_Others.setVisibility(View.GONE);
                                            convertOthersIntoImage(ImageInByte);
                                        }
                                    }
                                } else if (DocumentTypeName.equals("Prescriptions")) {
                                    prescriptionList.add("Prescriptions");
                                    if (prescriptionList.size() > 1) {

                                    } else {
                                        if (ImageInByte.equals("0")) {
                                            img_Prescription.setVisibility(View.GONE);
                                            text_Prescription.setVisibility(View.VISIBLE);
                                        } else {
                                            img_Prescription.setVisibility(View.VISIBLE);
                                            text_Prescription.setVisibility(View.GONE);
                                            convertPrescriptionIntoImage(ImageInByte);
                                        }
                                    }
                                }
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No Document Uploaded!", Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), RespMsg.toString(), Toast.LENGTH_SHORT).show();
//                        intent.putExtra("flag", 0);
                    }
                } catch (JSONException e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void convertXRayIntoImage(String imageInByte) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(imageInByte, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        img_xRay.setImageBitmap(decodedImage);

    }

    private void convertOthersIntoImage(String imageInByte) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(imageInByte, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        img_Others.setImageBitmap(decodedImage);

    }

    private void convertPrescriptionIntoImage(String imageInByte) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(imageInByte, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        img_Prescription.setImageBitmap(decodedImage);

    }
}
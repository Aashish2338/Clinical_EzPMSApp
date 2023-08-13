package com.ezpms.ezpmsapp.Patients;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ezpms.ezpmsapp.Activities.HomeActivity;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.ezpms.ezpmsapp.Utilities.ConfigurationData.Base_Url;

public class NewRegistrationActivity extends AppCompatActivity {

    private Context mContext;
    private TextView Tv_Title;
    private ImageView backMenu, calanderDOB_Im;
    private Spinner titleSpinner, bloodGroupSpinner, medicalHistorySpinner, countrySpinner, stateSpinner, citySpinner;
    private EditText edt_patientId, edt_FirstName, edt_LastName, edt_age, edt_mobileNo, edt_email, edtMedicalHistory, edt_address1;
    private EditText edt_address2, edt_postalCode;
    private static EditText edt_dob;
    private String str_title, str_patientID, str_firstName, str_lastName, str_dob, str_age, str_mobileNo, str_email, str_medicalHistory, str_address1, str_address2,
            str_postalCode, str_MedicalHistoryId, str_countryId, str_cityId, str_stateId, str_bloodgroupId;
    private String str_gender;
    private CheckBox cb_male, cb_female;

    private int bloodGrpId, MedicalHistoryId, countryId, selectedCountryId, selectedCountryId1, selectedCityId, selectedCityId2, cityId, clinicId,
            selectedStateId, selectedStateId2, stateId, selectedBloodgroupId, selectedBloodgroupId2, selectedMedicalId, selectedMedicalId2;
    private String bloodGroupName, medicalHistoryName, countryName, stateName, cityName;

    private SessionManager sessionManager;

    private ArrayAdapter<String> bloodGroupAdapter;
    private List<String> BloodGroupNameList = new ArrayList<>();
    private List<Integer> bloodGroupIdList = new ArrayList<>();

    private ArrayAdapter<String> medicalHistoryAdapter;
    private List<String> MedicalHistoryNameList = new ArrayList<>();
    private List<Integer> MedicalHistoryIdList = new ArrayList<>();

    private ArrayAdapter<String> countryAdapter;
    private List<String> countryNameList = new ArrayList<>();
    private List<Integer> countryIdList = new ArrayList<>();

    private ArrayAdapter<String> cityAdapter;
    private List<String> cityNameList = new ArrayList<>();
    private List<Integer> cityIdList = new ArrayList<>();

    private ArrayAdapter<String> stateAdapter;
    private List<String> stateNameList = new ArrayList<>();
    private List<Integer> stateIdList = new ArrayList<>();

    private String patientTitle[] = {"Select Title", "Mr.", "Ms.", "Mrs."};

    private RequestQueue requestQueue;
    private Button btn_login;
    private static String dateFormat, str_startDate = "", str_startTime = "";

    private TextView title_TvM, patientId_TvM, firstName_TvM, lastName_TvM, dateOfBirth_TvM, age_TvM, bloodGroup_TvM;
    private TextView medicalHistory_TvM, addressFirst_TvM, addressSecond_TvM;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);
        mContext = this;

        getTextLayoutUiId();
        setTextMandatoryLayout();

        sessionManager = new SessionManager(mContext);
        requestQueue = Volley.newRequestQueue(mContext);

        edt_patientId.setFocusable(false);
        edt_patientId.setEnabled(false);
        edt_patientId.setClickable(false);

        Tv_Title.setText("Patient Registration");

        ArrayAdapter<String> spinnerBoxArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, patientTitle);
        spinnerBoxArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        titleSpinner.setAdapter(spinnerBoxArrayAdapter);
        clinicId = sessionManager.getUserCliniId();
        str_startTime = getCurrentDate();

        getBloodGroupAPI();
        getMedicalHistory();
        getCountryList();
        getStateList("0");
        getCityList("0");
        getClickEventsForActionPerform();
    }

    private void getClickEventsForActionPerform() {
        try {
            calanderDOB_Im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }
            });

            bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedBloodgroupId = (int) bloodGroupSpinner.getSelectedItemId();
                    selectedBloodgroupId2 = bloodGroupIdList.get(selectedBloodgroupId);
                    Log.d("bloodId::", "" + selectedBloodgroupId + "\n" + selectedItem + "\n" + selectedBloodgroupId2);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            medicalHistorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedMedicalId = (int) medicalHistorySpinner.getSelectedItemId();
                    selectedMedicalId2 = MedicalHistoryIdList.get(selectedMedicalId);

//                Log.d("Medical::", "" + selectedMedicalId + "\n" + selectedItem + "\n" + selectedMedicalId2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedCountryId = (int) countrySpinner.getSelectedItemId();
                    selectedCountryId1 = countryIdList.get(selectedCountryId);
                    getStateList("" + selectedCountryId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedStateId = (int) stateSpinner.getSelectedItemId();
                    selectedStateId2 = stateIdList.get(selectedStateId);
                    getCityList("" + selectedStateId2);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedCityId = (int) citySpinner.getSelectedItemId();
                    selectedCityId2 = cityIdList.get(selectedCityId);

                    Log.d("CountryId::", "" + selectedCityId + "\n" + selectedItem + "\n" + selectedCityId2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            cb_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb_male.isChecked();
                        cb_female.setChecked(false);
                    } else {
                        cb_female.isChecked();
                        cb_male.setChecked(false);
                    }
                }
            });

            cb_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb_female.isChecked();
                        cb_male.setChecked(false);
                    } else {
                        cb_male.isChecked();
                        cb_female.setChecked(false);
                    }
                }
            });

            backMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (cb_female.isChecked()) {
                        str_gender = "Female";
                    } else if (cb_male.isChecked()) {
                        str_gender = "Male";
                    } else {
                        Toast.makeText(mContext, "Please Select Gender!", Toast.LENGTH_SHORT).show();
                    }

                    str_title = titleSpinner.getSelectedItem().toString();
                    str_patientID = edt_patientId.getText().toString();
                    str_firstName = edt_FirstName.getText().toString();
                    str_lastName = edt_LastName.getText().toString();
                    str_dob = edt_dob.getText().toString();
                    str_age = edt_age.getText().toString();
                    str_bloodgroupId = "" + selectedBloodgroupId2;
                    str_mobileNo = edt_mobileNo.getText().toString();
                    str_email = edt_email.getText().toString();
                    str_MedicalHistoryId = "" + selectedMedicalId2;
                    str_medicalHistory = edtMedicalHistory.getText().toString();
                    if (str_medicalHistory.isEmpty()) {
                        str_medicalHistory = "";
                    }

                    str_address1 = edt_address1.getText().toString();
                    str_address2 = edt_address2.getText().toString();

                    str_postalCode = edt_postalCode.getText().toString();
                    if (str_postalCode.isEmpty()) {
                        str_postalCode = "";
                    }

                    str_countryId = "" + selectedCountryId1;
                    str_stateId = "" + selectedStateId2;
                    str_cityId = "" + selectedCityId2;

               /* if (str_patientID.isEmpty()) {
                    edt_patientId.setError("Please Enter PatientID");
                    edt_patientId.requestFocus();
                } else*/
                    if (str_firstName.isEmpty()) {
                        edt_FirstName.setError("Please Enter FirstName");
                        edt_FirstName.requestFocus();
                    } else if (str_lastName.isEmpty()) {
                        edt_LastName.setError("Please Enter LastName");
                        edt_LastName.requestFocus();
                    } else if (str_age.isEmpty()) {
                        edt_age.setError("Please Enter DOB");
                        edt_age.requestFocus();
                    } else if (str_mobileNo.isEmpty()) {
                        edt_mobileNo.setError("Please Enter Mobile No");
                        edt_mobileNo.requestFocus();
                    } else if (str_mobileNo.length() < 10) {
                        edt_mobileNo.setError("Invalid Mobile No");
                        edt_mobileNo.requestFocus();
                    } else {
                        if (str_email.isEmpty()) {
                            str_email = "";
                        }
                        getPatientRegistration(str_title, str_patientID, str_firstName, str_lastName, str_dob, str_age, str_bloodgroupId, str_gender,
                                str_mobileNo, str_email, str_MedicalHistoryId, str_medicalHistory, str_address1, str_address2, str_countryId,
                                str_stateId, str_cityId, str_postalCode);
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void updateDateDOB() {
        try {
            String myFormatDOB = "dd-MMM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormatDOB, Locale.US);
            edt_dob.setText(sdf.format(myCalendar.getTime()));
            edt_dob.setClickable(false);

            Date birthdate = sdf.parse(sdf.format(myCalendar.getTime()));
            edt_age.setText(String.valueOf(calculateAge(birthdate)));
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

    private void getTextLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            backMenu = (ImageView) findViewById(R.id.backMenu);

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

            titleSpinner = (Spinner) findViewById(R.id.titleSpinner);
            bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
            medicalHistorySpinner = (Spinner) findViewById(R.id.medicalHistorySpinner);
            countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
            stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
            citySpinner = (Spinner) findViewById(R.id.citySpinner);
            edt_patientId = (EditText) findViewById(R.id.edt_patientId);
            edt_FirstName = (EditText) findViewById(R.id.edt_FirstName);
            edt_LastName = (EditText) findViewById(R.id.edt_LastName);
            edt_dob = (EditText) findViewById(R.id.edt_dob);
            edt_age = (EditText) findViewById(R.id.edt_age);
            edt_mobileNo = (EditText) findViewById(R.id.edt_mobileNo);
            edt_email = (EditText) findViewById(R.id.edt_email);
            edtMedicalHistory = (EditText) findViewById(R.id.edtMedicalHistory);
            edt_address1 = (EditText) findViewById(R.id.edt_address1);
            edt_address2 = (EditText) findViewById(R.id.edt_address2);
            edt_postalCode = (EditText) findViewById(R.id.edt_postalCode);
            btn_login = (Button) findViewById(R.id.btn_login);
            cb_male = (CheckBox) findViewById(R.id.cb_male);
            cb_female = (CheckBox) findViewById(R.id.cb_female);
            calanderDOB_Im = (ImageView) findViewById(R.id.calanderDOB_Im);

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

    private void getPatientRegistration(String Title, String PatientID, String FirstName, String LastName, String Dob, String Age,
                                        String BloodgroupId, String Gender, String MobileNo, String Email, String MedicalHistoryId,
                                        String MedicalHistory, String Address1, String Address2, String CountryId,
                                        String StateId, String CityId, String PostalCode) {
        JSONObject jsonObj;
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            HashMap<String, String> param = new HashMap<String, String>();
            param.put("PatientIdDisp", "" + PatientID);
            param.put("Title", Title);
            param.put("PatientFirstName", FirstName);
            param.put("PatientLastName", LastName);
            param.put("Gender", Gender);
            param.put("PatientType", "");
            param.put("Age", Age);
            param.put("EmailAddress", Email);
            param.put("PhoneNumber", MobileNo);
            param.put("PatientAddress1", Address1);
            param.put("PatientAddress2", Address2);
            param.put("CityId", CityId);
            param.put("StateId", StateId);
            param.put("CountryId", CountryId);
            param.put("PostalCode", PostalCode);
            param.put("CountryCode", "+91");
            param.put("CreatedBy", sessionManager.getLoginUserId());
            param.put("DOB", Dob);
            param.put("BloodGroup", BloodgroupId);
            param.put("MedicalHistory", MedicalHistoryId);
            param.put("OtherMedicalHistory", MedicalHistory);
            param.put("LastVisitDate", "1-Jan-2000");
            param.put("ClinicId", "" + clinicId);

            jsonObj = new JSONObject(param);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Base_Url + "PatientDetailsRegistration",
                    jsonObj, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Orderdeatil:", "" + response);
                    try {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        String responseMessage = response.getString("RespMsg");
                        if (responseMessage.equals("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Registration Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewRegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Log.d("LoginDataE:", "" + e.toString());
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Failed to register, Try Again!", Toast.LENGTH_SHORT).show();
                    Log.d("Orderdeatil:", "" + error);
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getBloodGroupAPI() {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url + "GetAllBloodGroup?bloodGroupId=0", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Orderdeatil:", "" + response);
                try {
                    BloodGroupNameList.clear();
                    bloodGroupIdList.clear();
                    String RespCode = response.getString("RespCode");
                    String RespMsg = response.getString("RespMsg");
                    if (RespMsg.equals("SUCCESS")) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        JSONArray jsonArray = response.getJSONArray("DATA");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            bloodGrpId = jObj.getInt("BloodGroupId");
                            bloodGroupName = jObj.getString("BloodGroupName");

                            BloodGroupNameList.add(bloodGroupName);
                            bloodGroupIdList.add(bloodGrpId);
                            bloodGroupAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, BloodGroupNameList);
                            bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bloodGroupSpinner.setAdapter(bloodGroupAdapter);
//                            getMedicalHistory();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), RespMsg.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.d("LoginDataE:", "" + e.toString());
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
                Log.d("Orderdeatil:", "" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getMedicalHistory() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url + "GetAllMedicalHistory?medicalHistoryId=0", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Orderdeatil:", "" + response);
                try {
                    MedicalHistoryNameList.clear();
                    MedicalHistoryIdList.clear();
                    String RespCode = response.getString("RespCode");
                    String RespMsg = response.getString("RespMsg");
                    if (RespMsg.equals("SUCCESS")) {
                        JSONArray jsonArray = response.getJSONArray("DATA");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            MedicalHistoryId = jObj.getInt("MedicalHistoryId");
                            medicalHistoryName = jObj.getString("MedicalHistoryName");

                            MedicalHistoryNameList.add(medicalHistoryName);
                            MedicalHistoryIdList.add(MedicalHistoryId);
                            medicalHistoryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, MedicalHistoryNameList);
                            medicalHistoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            medicalHistorySpinner.setAdapter(medicalHistoryAdapter);
//                            getCountryList();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), RespMsg.toString(), Toast.LENGTH_SHORT).show();
//                        intent.putExtra("flag", 0);
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

    private void getCountryList() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url + "GetAllCountry?countryId=0",
                null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Orderdeatil:", "" + response);
                try {
                    countryNameList.clear();
                    countryIdList.clear();
                    String RespCode = response.getString("RespCode");
                    String RespMsg = response.getString("RespMsg");
                    if (RespMsg.equals("SUCCESS")) {
                        JSONArray jsonArray = response.getJSONArray("DATA");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            countryId = jObj.getInt("CountryId");
                            countryName = jObj.getString("CountryName");

                            countryNameList.add(countryName);
                            countryIdList.add(countryId);
                            countryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countryNameList);
                            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            countrySpinner.setAdapter(countryAdapter);
//                            getStateList("0");

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), RespMsg.toString(), Toast.LENGTH_SHORT).show();
//                        intent.putExtra("flag", 0);
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

    private void getStateList(String countryId) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url
                + "GetAllStates?countryId=" + countryId + "&stateId=0", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Orderdeatil:2", "" + response);
                try {
                    stateNameList.clear();
                    stateIdList.clear();
//                    stateAdapter.clear();
                    String RespCode = response.getString("RespCode");
                    String RespMsg = response.getString("RespMsg");
                    if (RespMsg.equals("SUCCESS")) {
                        JSONArray jsonArray = response.getJSONArray("DATA");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            stateId = jObj.getInt("StateId");
                            stateName = jObj.getString("StateName");

                            stateNameList.add(stateName);
                            stateIdList.add(stateId);
                            stateAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, stateNameList);
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            stateSpinner.setAdapter(stateAdapter);
//                            getCityList("0");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), RespMsg.toString(), Toast.LENGTH_SHORT).show();
//                        intent.putExtra("flag", 0);
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
                Log.d("Orderdeatil:Err", "" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getCityList(String stateId) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Base_Url
                + "GetAllCity?stateId=" + stateId + "&cityId=0", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Orderdeatil:1", "" + response);
                try {
                    cityNameList.clear();
                    cityIdList.clear();
                    String RespCode = response.getString("RespCode");
                    String RespMsg = response.getString("RespMsg");
                    if (RespMsg.equals("SUCCESS")) {
                        JSONArray jsonArray = response.getJSONArray("DATA");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            cityId = jObj.getInt("CityId");
                            cityName = jObj.getString("CityName");

                            cityNameList.add(cityName);
                            cityIdList.add(cityId);
                            cityAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, cityNameList);
                            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            citySpinner.setAdapter(cityAdapter);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), RespMsg.toString(), Toast.LENGTH_SHORT).show();
//                        intent.putExtra("flag", 0);
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
                Log.d("Orderdeatil:Err", "" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void openDateDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
//            dialog.
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            int txt_month = month + 1;
            dateFormat = day + "-" + txt_month + "-" + year;
            SimpleDateFormat fmt = new SimpleDateFormat("dd-mm-yyyy");
            try {
                Date date = fmt.parse(dateFormat);
                String month_show = (String) DateFormat.format("mm", date);
                str_startDate = year + "-" + month_show + "-" + day;
                edt_dob.setText(str_startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = simpleDateFormat.format(currentDate);
//        String formattedTime = simpleTimeFormat.format(currentDate);
//        str_startTime = formattedDate;
        return formattedDate;
    }
}
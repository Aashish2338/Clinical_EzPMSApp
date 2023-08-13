package com.ezpms.ezpmsapp.Treatments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Appointments.UpdateAppointmentsActivity;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.DocumentTypesData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.ResponseModels.DocumentTypesResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTreatmentsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, cancel_Btn, bTN_Cancel;
    private ImageView backMenu, calander_Im, documents;
    private LinearLayout image_Llll;
    private EditText treatmentDate_Et, symptomsName_Et, treatmentDetails_Et, medicinePrescribed_Et;
    private Button addDetails_Btn, uploadDocuments_Btn, upload_Btn;
    private Spinner documentsTypeSpinner;
    private List<DocumentTypesData> documentTypesData = null;
    private String[] documentsTypeArray;
    private String documentsTypeName = "", documentsTypeId, documentsName, documentsNameInByte;
    private List<String> documentsTypeIdList = new ArrayList<>();
    private SessionManager sessionManager;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private static final int MY_GALLERY_PERMISSION_CODE = 11;
    private int treatmentId, appointmentId, appAppointmentId, patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treatments);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();
        setCurrentDate();
        Tv_Title.setText("New Treatment");

        backMenu.setOnClickListener(this);
        addDetails_Btn.setOnClickListener(this);
        cancel_Btn.setOnClickListener(this);
        uploadDocuments_Btn.setOnClickListener(this);
        upload_Btn.setOnClickListener(this);
        bTN_Cancel.setOnClickListener(this);
        calander_Im.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            treatmentId = extras.getInt("TreatmentId");
            appointmentId = extras.getInt("AppointmentId");
            appAppointmentId = extras.getInt("AppAppointmentId");
            patientId = extras.getInt("PatientId");
        }

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllDocumentsTypes();
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentDate() {
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            treatmentDate_Et.setText(formattedDate);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllDocumentsTypes() {
        try {
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<DocumentTypesResponse> typesResponseCall = apiClientNetwork.getAllDocumentTypes();
            typesResponseCall.enqueue(new Callback<DocumentTypesResponse>() {
                @Override
                public void onResponse(Call<DocumentTypesResponse> call, Response<DocumentTypesResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getDocumentTypesData() != null) {
                                documentTypesData = response.body().getDocumentTypesData();
                                if (documentTypesData.size() >= 0) {
                                    documentsTypeArray = new String[response.body().getDocumentTypesData().size()];
                                    for (int i = 0; i < response.body().getDocumentTypesData().size(); i++) {
                                        documentsTypeIdList.add(response.body().getDocumentTypesData().get(i).getDocumentID().toString());
                                        documentsTypeArray[i] = response.body().getDocumentTypesData().get(i).getDocumentName();
                                    }
                                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, documentsTypeArray);
                                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    documentsTypeSpinner.setAdapter(countryAdapter);
                                    documentsTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            documentsTypeId = documentsTypeIdList.get(i);
                                            documentsTypeName = documentsTypeArray[i];
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "Document data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Document data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DocumentTypesResponse> call, Throwable t) {
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
            bTN_Cancel = (TextView) findViewById(R.id.bTN_Cancel);

            backMenu = (ImageView) findViewById(R.id.backMenu);
            calander_Im = (ImageView) findViewById(R.id.calander_Im);
            documents = (ImageView) findViewById(R.id.documents);
            image_Llll = (LinearLayout) findViewById(R.id.image_Llll);

            treatmentDate_Et = (EditText) findViewById(R.id.treatmentDate_Et);
            symptomsName_Et = (EditText) findViewById(R.id.symptomsName_Et);
            treatmentDetails_Et = (EditText) findViewById(R.id.treatmentDetails_Et);
            medicinePrescribed_Et = (EditText) findViewById(R.id.medicinePrescribed_Et);

            addDetails_Btn = (Button) findViewById(R.id.addDetails_Btn);
            uploadDocuments_Btn = (Button) findViewById(R.id.uploadDocuments_Btn);
            upload_Btn = (Button) findViewById(R.id.upload_Btn);

            documentsTypeSpinner = (Spinner) findViewById(R.id.documentsTypeSpinner);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.backMenu:
                    onBackPressed();
                    break;

                case R.id.addDetails_Btn:
                    if (valide()) {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            addDetailsMethod(treatmentDate_Et.getText().toString(), symptomsName_Et.getText().toString(),
                                    treatmentDetails_Et.getText().toString(), medicinePrescribed_Et.getText().toString());
                        } else {
                            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case R.id.cancel_Btn:
                    clearAllFields();
                    break;

                case R.id.uploadDocuments_Btn:
                    if (mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_GALLERY_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        cameraIntent.setType("*/*");
                        cameraIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        try {
                            someActivityResultLauncher.launch(cameraIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(mContext, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case R.id.upload_Btn:
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        UploadDocumentsDetails(documentsTypeId, patientId, treatmentId, appointmentId);
                    } else {
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.bTN_Cancel:
                    clearAllFields();
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
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedFileURI = data.getData();
                        File file = new File(selectedFileURI.getPath());
                        documentsName = file.getName();
                        ConvertToString(selectedFileURI);

                    }
                }
            });

    public void ConvertToString(Uri uri) {
        try {
            byte[] bytes;
            InputStream in = getContentResolver().openInputStream(uri);
            bytes = getBytes(in);
            String ansValue = Base64.encodeToString(bytes, Base64.DEFAULT);
            documentsNameInByte = Base64.encodeToString(bytes, Base64.DEFAULT);

            //decode base64 string to image
            bytes = Base64.decode(ansValue, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (decodedImage != null) {
                image_Llll.setVisibility(View.VISIBLE);
                documents.setImageBitmap(decodedImage);
            } else {
                image_Llll.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(InputStream inputStream) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while (true) {
            try {
                if (!((len = inputStream.read(buffer)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private boolean valide() {
        if (treatmentDate_Et.getText().toString().isEmpty()) {
            treatmentDate_Et.setError("Select Treatment Date");
            treatmentDate_Et.requestFocus();
            return false;
        } else if (symptomsName_Et.getText().toString().isEmpty()) {
            symptomsName_Et.setError("Enter Symptoms");
            symptomsName_Et.requestFocus();
            return false;
        } else if (treatmentDetails_Et.getText().toString().isEmpty()) {
            treatmentDetails_Et.setError("Enter Treatment Details");
            treatmentDetails_Et.requestFocus();
            return false;
        } else if (medicinePrescribed_Et.getText().toString().isEmpty()) {
            medicinePrescribed_Et.setError("Enter Medicine Prescribed");
            medicinePrescribed_Et.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_GALLERY_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "Storage permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(cameraIntent);
            } else {
                Toast.makeText(mContext, "Storage permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateDate() {
        try {
            String myFormat = "dd-MMM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            treatmentDate_Et.setText(sdf.format(myCalendar.getTime()));
            treatmentDate_Et.setClickable(false);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void clearAllFields() {
        try {
            symptomsName_Et.getText().clear();
            treatmentDetails_Et.getText().clear();
            medicinePrescribed_Et.getText().clear();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void addDetailsMethod(String treatmentDate, String symptomsName, String treatmentDetails, String medicinePrescribed) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> registerResponseCall = apiClientNetwork.addTreatmentDetail(appAppointmentId, sessionManager.getLoginUserId(),
                    symptomsName, treatmentDate, treatmentDetails, medicinePrescribed, "");
            registerResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            treatmentId = response.body().getData();
                            openDailogForSuccess(response.body().getData());
//                            Toast.makeText(mContext, "Added Treatments Details Successfully!", Toast.LENGTH_SHORT).show();
//                            clearAllFields();
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

    private void openDailogForSuccess(Integer data) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.treatment_updates_layouts);

            Button btn_Yes = (Button) dialog.findViewById(R.id.btn_Yes);
            TextView btn_No = (TextView) dialog.findViewById(R.id.btn_No);

            btn_Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.setRegisterClinicId(data);
                    addDetails_Btn.setText("Update Details");
                    dialog.dismiss();
                }
            });

            btn_No.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UpdateAppointmentsActivity.class);
                    intent.putExtra("AppAppointmentId", appAppointmentId);
                    intent.putExtra("PatientId", patientId);
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

    private void UploadDocumentsDetails(String documentsTypeId, int patientId, int treatmentId, int appointmentId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<ClinicRegisterResponse> clinicRegisterResponseCall = apiClientNetwork.addAppDocument(Integer.valueOf(documentsTypeId), patientId, treatmentId,
                    appointmentId, documentsName, documentsNameInByte, sessionManager.getLoginUserId());

            clinicRegisterResponseCall.enqueue(new Callback<ClinicRegisterResponse>() {
                @Override
                public void onResponse(Call<ClinicRegisterResponse> call, Response<ClinicRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            getSuccessDialog();
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
//                        Toast.makeText(mContext, response.message(), Toast.LENGTH_LONG).show();
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

    private void getSuccessDialog() {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.treatment_updates_layouts);

            TextView tv_TitleSuccessfully = (TextView) dialog.findViewById(R.id.tv_TitleSuccessfully);
            tv_TitleSuccessfully.setText("Documents Uploaded Successfully\nDo you want to upload more documents.");

            Button btn_Yes = (Button) dialog.findViewById(R.id.btn_Yes);
            TextView btn_No = (TextView) dialog.findViewById(R.id.btn_No);

            btn_Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btn_No.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UpdateAppointmentsActivity.class);
                    intent.putExtra("TreatmentId", treatmentId);
                    intent.putExtra("AppointmentId", appointmentId);
                    intent.putExtra("AppAppointmentId", appAppointmentId);
                    intent.putExtra("PatientId", patientId);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }
}
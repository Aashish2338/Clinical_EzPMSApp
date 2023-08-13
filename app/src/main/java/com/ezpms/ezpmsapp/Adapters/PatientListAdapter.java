package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Models.PatientListData;
import com.ezpms.ezpmsapp.Patients.UpdatePatientDetailsActivity;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ViewHolders.PatientListViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListViewHolder> {

    private Context mContext;
    private List<PatientListData> patientListData;
    private SimpleDateFormat formatter, formatters;
    private String date, dates, title;
    String emailID = "", gender = "", OtherMedicalHistory = "", secondAddress = "", PostalCode = "", str_Address = "";

    public PatientListAdapter(Context mContext, List<PatientListData> patientListData) {
        this.mContext = mContext;
        this.patientListData = patientListData;
    }

    @NonNull
    @Override
    public PatientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.patient_list_layout, parent, false);
        return new PatientListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientListViewHolder holder, int position) {
        if (patientListData.get(position).getTitle() == 1) {
            title = "Mr.";
        } else if (patientListData.get(position).getTitle() == 2) {
            title = "Ms.";
        } else if (patientListData.get(position).getTitle() == 3) {
            title = "Mrs.";
        } else {
            title = " ";
        }
        holder.patientName_Pt.setText(title + " " + patientListData.get(position).getFirstName() + " " + patientListData.get(position).getLastName());

        int lenght = String.valueOf(patientListData.get(position).getPatientId()).length();
        if (lenght == 6) {
            holder.patientId_Pts.setText("0" + patientListData.get(position).getPatientId().toString());
        } else if (lenght == 5) {
            holder.patientId_Pts.setText("00" + patientListData.get(position).getPatientId().toString());
        } else if (lenght == 4) {
            holder.patientId_Pts.setText("000" + patientListData.get(position).getPatientId().toString());
        } else if (lenght == 3) {
            holder.patientId_Pts.setText("0000" + patientListData.get(position).getPatientId().toString());
        } else if (lenght == 2) {
            holder.patientId_Pts.setText("00000" + patientListData.get(position).getPatientId().toString());
        } else {
            holder.patientId_Pts.setText("000000" + patientListData.get(position).getPatientId().toString());
        }

        holder.mobileNo_Pt.setText(patientListData.get(position).getPhoneNumber());
        if (patientListData.get(position).getGender() != null) {
            holder.maleFemale_Pt.setText(patientListData.get(position).getGender().toString());
        } else {
            holder.maleFemale_Pt.setText(" ");
        }

        try {
            formatter = new SimpleDateFormat("dd-MMM-yyyy");
            date = formatter.format(Date.parse(patientListData.get(position).getDob()));
            holder.dateOfBirth_Pt.setText(date);

            formatters = new SimpleDateFormat("dd-MMM-yyyy");
            dates = formatters.format(Date.parse(patientListData.get(position).getLastVisitDate()));
            holder.lastVisitDate_Pt.setText(dates);
        } catch (Exception exp) {
            exp.getStackTrace();
        }

        if (patientListData.get(position).getRcdInsTs() != null) {
            String str = patientListData.get(position).getRcdInsTs().toString();
            String treatmentDate = str.substring(0, 10);
            holder.registrationDate_Pt.setText(treatmentDate);
        } else {
            holder.registrationDate_Pt.setText("");
        }

        setClickEventForUpdate(holder, position);
    }

    private void setClickEventForUpdate(PatientListViewHolder holder, int positions) {
        if (patientListData.get(positions).getGender() == null) {
            gender = "";
        } else {
            gender = patientListData.get(positions).getGender().toString();
        }

        try {
            holder.patientList_RC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (patientListData.get(positions).getEmail() == null) {
                        emailID = "";
                    } else {
                        emailID = patientListData.get(positions).getEmail().toString();
                    }

                    if (patientListData.get(positions).getOtherMedicalHistory() == null) {
                        OtherMedicalHistory = "";
                    } else {
                        OtherMedicalHistory = patientListData.get(positions).getOtherMedicalHistory().toString();
                    }

                    if (patientListData.get(positions).getAddress2() == null) {
                        secondAddress = "";
                    } else {
                        secondAddress = patientListData.get(positions).getAddress2().toString();
                    }

                    if (patientListData.get(positions).getPostalCode() == null) {
                        PostalCode = "";
                    } else {
                        PostalCode = patientListData.get(positions).getPostalCode().toString();
                    }

                    if (patientListData.get(positions).getAddress1() == null) {
                        str_Address = "";
                    } else {
                        str_Address = patientListData.get(positions).getAddress1().toString();
                    }

                    Intent intent = new Intent(mContext, UpdatePatientDetailsActivity.class);
                    intent.putExtra("Title", patientListData.get(positions).getTitle());
                    intent.putExtra("FirstName", patientListData.get(positions).getFirstName());
                    intent.putExtra("LastName", patientListData.get(positions).getLastName());
                    intent.putExtra("PatientId", patientListData.get(positions).getPatientId());
                    intent.putExtra("PhoneNumber", patientListData.get(positions).getPhoneNumber());
                    intent.putExtra("Email", "" + emailID);
                    intent.putExtra("Gender", "" + gender);
                    intent.putExtra("DOB", "" + patientListData.get(positions).getDob());
                    intent.putExtra("BloodGroup", patientListData.get(positions).getBloodGroup());
                    intent.putExtra("MedicalHistory", patientListData.get(positions).getMedicalHistory());
                    intent.putExtra("OtherMedicalHistory", "" + OtherMedicalHistory);
                    intent.putExtra("AddressFirst", "" + str_Address);
                    intent.putExtra("AddressSecond", secondAddress);
                    intent.putExtra("PostalCode", PostalCode);
                    intent.putExtra("CityId", patientListData.get(positions).getCityId());
                    intent.putExtra("StateId", patientListData.get(positions).getStateId());
                    intent.putExtra("CountryId", patientListData.get(positions).getCountryId());
                    intent.putExtra("AppAppointmentId", patientListData.get(positions).getAppointmentId());
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return patientListData.size();
    }

    public void updatePatientList(List<PatientListData> list) {
        patientListData = list;
        notifyDataSetChanged();
    }
}
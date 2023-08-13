package com.ezpms.ezpmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.AllDoctorsMonthViewAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AppointmentCountData;
import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentCountResponse;
import com.ezpms.ezpmsapp.ResponseModels.DoctorAvailableResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.AllDoctorsWeekViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthViewsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, remaining, completed, allAppointment;
    private ImageView backMenu;
    private RecyclerView doctorsMonthViewsRc;
    private CalendarView calendarView;
    private AllDoctorsWeekViewAdapter allDoctorsMonthViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<DoctorAvailableData> doctorAvailableData = null;
    private SessionManager sessionManager;
    private String todate, fromDate, str_year, str_month;
    private int toSelectedDate, fromSelectedDate;
    private Spinner spn_Month, spn_Year;
    private String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] YearList = {"1990", "1991", "1992", "1993", "1994", "1995", "1996", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005",
            "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022",
            "2023", "2024", "2025"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_views);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();
        Calendar cal = Calendar.getInstance();
        String month1 = monthList[cal.get(Calendar.MONTH)];
        String[] mnth = {month1, "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        LinkedHashSet<String> stringSet = new LinkedHashSet<>(Arrays.asList(mnth));
        String[] filteredArray = stringSet.toArray(new String[0]);
        int year1 = Calendar.getInstance().get(Calendar.YEAR);

        String[] currentYear = {String.valueOf(year1), "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005",
                "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022",
                "2023", "2024", "2025"};

        LinkedHashSet<String> stringSet1 = new LinkedHashSet<>(Arrays.asList(currentYear));
        String[] filteredYear = stringSet1.toArray(new String[0]);

        ArrayAdapter<String> spinnerYear = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, filteredYear);
        spinnerYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spn_Year.setAdapter(spinnerYear);

        ArrayAdapter<String> spinnerMonth = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, filteredArray);
        spinnerMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spn_Month.setAdapter(spinnerMonth);

        Tv_Title.setText("Month Appointments");
        backMenu.setOnClickListener(this);

        spn_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_month = spn_Month.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_year = spn_Year.getSelectedItem().toString();
                switch (str_month) {
                    case "January":
                        fromDate = str_year + "-01-01";
                        todate = str_year + "-01-31";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "February":
                        fromDate = str_year + "-02-01";
                        todate = str_year + "-02-29";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "March":
                        fromDate = str_year + "-03-01";
                        todate = str_year + "-03-31";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "April":
                        fromDate = str_year + "-04-01";
                        todate = str_year + "-04-30";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "May":
                        fromDate = str_year + "-05-01";
                        todate = str_year + "-05-31";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "June":
                        fromDate = str_year + "-06-01";
                        todate = str_year + "-06-30";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "July":
                        fromDate = str_year + "-07-01";
                        todate = str_year + "-07-31";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "August":
                        fromDate = str_year + "-08-01";
                        todate = str_year + "-08-31";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "September":
                        fromDate = str_year + "-09-01";
                        todate = str_year + "-09-30";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "October":
                        fromDate = str_year + "-10-01";
                        todate = str_year + "-10-31";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "November":
                        fromDate = str_year + "-11-01";
                        todate = str_year + "-11-30";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    case "December":
                        fromDate = str_year + "-12-01";
                        todate = str_year + "-12-31";
                        getAllAppointmentCountMethod(fromDate, todate);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAvailableDoctorsList(sessionManager.getUserCliniId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

//        getSearchDataByName();
    }

    private void getAvailableDoctorsList(int userCliniId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<DoctorAvailableResponse> doctorAvailableResponseCall = apiClientNetwork.getGetDoctorList(userCliniId);
            doctorAvailableResponseCall.enqueue(new Callback<DoctorAvailableResponse>() {
                @Override
                public void onResponse(Call<DoctorAvailableResponse> call, Response<DoctorAvailableResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getDoctorAvailableData() != null) {
                                doctorAvailableData = response.body().getDoctorAvailableData();
                                if (doctorAvailableData.size() >= 0) {
                                    getRecyclerForMonthViews(doctorAvailableData);
                                } else {
                                    Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Not found available doctors!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<DoctorAvailableResponse> call, Throwable t) {
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

    private void getSearchDataByName() {
//        try {
//            Et_SearchingByName.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    filter(charSequence.toString());
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    filter(editable.toString());
//                }
//            });
//        } catch (Exception exp) {
//            exp.getStackTrace();
//        }
    }

    private void filter(String toString) {
        try {
            List<DoctorAvailableData> temp = new ArrayList();
            for (DoctorAvailableData patient : doctorAvailableData) {
                if (patient.getDoctorName().toLowerCase().contains(toString)) {
                    temp.add(patient);
                }
            }
            allDoctorsMonthViewAdapter.updateDoctorsDayViewList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForMonthViews(List<DoctorAvailableData> doctorAvailableData) {
        try {
            allDoctorsMonthViewAdapter = new AllDoctorsWeekViewAdapter(mContext, doctorAvailableData);
            linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            doctorsMonthViewsRc.setLayoutManager(linearLayoutManager);
            doctorsMonthViewsRc.setAdapter(allDoctorsMonthViewAdapter);
            allDoctorsMonthViewAdapter.notifyDataSetChanged();
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }


    public class AllDoctorsWeekViewAdapter extends RecyclerView.Adapter<AllDoctorsWeekViewHolder> {

        private Context mContext;
        private List<DoctorAvailableData> doctorAvailableData;

        public AllDoctorsWeekViewAdapter(Context mContext, List<DoctorAvailableData> doctorAvailableData) {
            this.mContext = mContext;
            this.doctorAvailableData = doctorAvailableData;
            sessionManager = new SessionManager(mContext);
        }

        @Override
        public AllDoctorsWeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.alldoctors_weekviews_layout, parent, false);
            return new AllDoctorsWeekViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AllDoctorsWeekViewHolder holder, int position) {
            holder.doctorName.setText(doctorAvailableData.get(position).getDoctorName());
            setClickEventForAssignment(holder, position);
        }

        private void setClickEventForAssignment(AllDoctorsWeekViewHolder holder, int position) {
            holder.viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WeekViewAppointmentActivity.class);
                    intent.putExtra("FromDate", /*fromDate*/fromDate);
                    intent.putExtra("Todate", /*todate*/todate);
                    intent.putExtra("Day", "Month Appointments");
                    intent.putExtra("UserID", doctorAvailableData.get(position).getUserID());
                    startActivity(intent);
                }
            });

            holder.id_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WeekViewAppointmentActivity.class);
                    intent.putExtra("FromDate", /*fromDate*/fromDate);
                    intent.putExtra("Todate", /*todate*/todate);
                    intent.putExtra("Day", "Month Appointments");
                    intent.putExtra("UserID", doctorAvailableData.get(position).getUserID());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return doctorAvailableData.size();
        }

        public void updateDoctorsDayViewList(List<DoctorAvailableData> doctor) {
            doctorAvailableData = doctor;
            notifyDataSetChanged();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            calendarView = (CalendarView) findViewById(R.id.calendarView);
            doctorsMonthViewsRc = (RecyclerView) findViewById(R.id.doctorsMonthViewsRc);
            spn_Year = findViewById(R.id.spn_Year);
            spn_Month = findViewById(R.id.spn_Month);
            remaining = (TextView) findViewById(R.id.remaining);
            completed = (TextView) findViewById(R.id.completed);
            allAppointment = (TextView) findViewById(R.id.allAppointment);

//            Et_SearchingByName = (EditText) findViewById(R.id.Et_SearchingByName);

        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getAllAppointmentCountMethod(String fromDate, String todate) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllAppointmentCountResponse> allAppointmentCountResponseCall = apiClientNetwork.getAllAppointmentCount(fromDate, todate);
            allAppointmentCountResponseCall.enqueue(new Callback<AllAppointmentCountResponse>() {
                @Override
                public void onResponse(Call<AllAppointmentCountResponse> call, Response<AllAppointmentCountResponse> response) {
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (response.body().getAppointmentCountData() != null) {
                                setTextFieldsValues(response.body().getAppointmentCountData());
                            } else {
                                Toast.makeText(mContext, "Data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
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
                public void onFailure(Call<AllAppointmentCountResponse> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void setTextFieldsValues(AppointmentCountData appointmentCountData) {
        try {
            allAppointment.setText(appointmentCountData.getAllAppintment().toString());
            completed.setText(appointmentCountData.getCompleted().toString());
            remaining.setText(appointmentCountData.getPending().toString());

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
        }
    }
}
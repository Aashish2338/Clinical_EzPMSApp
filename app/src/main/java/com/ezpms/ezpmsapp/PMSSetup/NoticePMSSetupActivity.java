package com.ezpms.ezpmsapp.PMSSetup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Adapters.NoticeListAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllAppNoticesData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllAppNoticesResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticePMSSetupActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, textNoticeData;
    private ImageView backMenu;
    private EditText et_SearchingNoticeTitle;
    private RecyclerView noticeListRc;
    private NoticeListAdapter noticeListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<AllAppNoticesData> appNoticesData = null;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_p_m_s_setup);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        Tv_Title.setText("Notice List");
        backMenu.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllUserNotice(sessionManager.getClinicId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
        getSearchNoticeTitle();
    }

    private void getAllUserNotice(int clinicId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllAppNoticesResponse> noticesResponseCall = apiClientNetwork.getAllAppNotices(clinicId);
            noticesResponseCall.enqueue(new Callback<AllAppNoticesResponse>() {
                @Override
                public void onResponse(Call<AllAppNoticesResponse> call, Response<AllAppNoticesResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getAppNoticesData() != null) {
                                appNoticesData = response.body().getAppNoticesData();
                                if (appNoticesData.size() >= 0) {
                                    textNoticeData.setVisibility(View.GONE);
                                    noticeListRc.setVisibility(View.VISIBLE);
                                    getRecyclerForNoticeList(appNoticesData);
                                } else {
                                    textNoticeData.setVisibility(View.VISIBLE);
                                    noticeListRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "Notice data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                textNoticeData.setVisibility(View.VISIBLE);
                                noticeListRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "Notice data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            textNoticeData.setVisibility(View.VISIBLE);
                            noticeListRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        textNoticeData.setVisibility(View.VISIBLE);
                        noticeListRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllAppNoticesResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    textNoticeData.setVisibility(View.VISIBLE);
                    noticeListRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForNoticeList(List<AllAppNoticesData> appNoticesData) {
        try {
            if (appNoticesData.size() == 0) {
                textNoticeData.setVisibility(View.VISIBLE);
                noticeListRc.setVisibility(View.GONE);
            } else {
                textNoticeData.setVisibility(View.GONE);
                noticeListRc.setVisibility(View.VISIBLE);
                noticeListAdapter = new NoticeListAdapter(mContext, appNoticesData);
                linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                noticeListRc.setLayoutManager(linearLayoutManager);
                noticeListRc.setAdapter(noticeListAdapter);
                noticeListAdapter.notifyDataSetChanged();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getSearchNoticeTitle() {
        try {
            et_SearchingNoticeTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    filter(editable.toString());
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void filter(String toString) {
        try {
            List<AllAppNoticesData> temp = new ArrayList();
            for (AllAppNoticesData patient : appNoticesData) {
                if (patient.getNoticeTitle().toLowerCase().contains(toString) || patient.getNoticeDate().toLowerCase().contains(toString)) {
                    temp.add(patient);
                }
            }
            noticeListAdapter.updateNoticeList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            textNoticeData = (TextView) findViewById(R.id.textNoticeData);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            noticeListRc = (RecyclerView) findViewById(R.id.noticeListRc);
            et_SearchingNoticeTitle = (EditText) findViewById(R.id.et_SearchingNoticeTitle);

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
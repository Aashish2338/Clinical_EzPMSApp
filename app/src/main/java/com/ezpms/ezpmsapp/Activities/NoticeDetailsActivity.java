package com.ezpms.ezpmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.NoticesDetailData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.NoticesDetailResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, noticeTitle_Tv, noticeDate_Tv, noticeDetails;
    private ImageView backMenu;
    private int noticeId;
    private SimpleDateFormat formatter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);
        mContext = this;

        getLayoutUiId();

        Tv_Title.setText("Notice Details");
        backMenu.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noticeId = extras.getInt("NoticeId");
        }

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getUserNoticesDetails(noticeId);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserNoticesDetails(int noticeId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<NoticesDetailResponse> detailResponseCall = apiClientNetwork.getAppNoticesDetailByid(noticeId);
            detailResponseCall.enqueue(new Callback<NoticesDetailResponse>() {
                @Override
                public void onResponse(Call<NoticesDetailResponse> call, Response<NoticesDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getNoticesDetailData() != null) {
                                setNoticesDetailDataInTextView(response.body().getNoticesDetailData());
                            } else {
                                Toast.makeText(mContext, "Notice details data not found!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<NoticesDetailResponse> call, Throwable t) {
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

    private void setNoticesDetailDataInTextView(NoticesDetailData noticesDetailData) {
        try {
            noticeTitle_Tv.setText(noticesDetailData.getNoticeTitle());
            formatter = new SimpleDateFormat("dd-MMM-yyyy");
            date = formatter.format(Date.parse(noticesDetailData.getNoticeDate()));
            noticeDate_Tv.setText(date);
            noticeDetails.setText(noticesDetailData.getNoticeDetails());
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            noticeTitle_Tv = (TextView) findViewById(R.id.noticeTitle_Tv);
            noticeDate_Tv = (TextView) findViewById(R.id.noticeDate_Tv);
            noticeDetails = (TextView) findViewById(R.id.noticeDetails);
            backMenu = (ImageView) findViewById(R.id.backMenu);

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
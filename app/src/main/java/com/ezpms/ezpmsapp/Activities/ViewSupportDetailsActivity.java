package com.ezpms.ezpmsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.SupportDetailData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.SupportDetailResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSupportDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, reasonType_Tv, supportStatus_Tv, supportDate_Tv, reasonDetails_Tv;
    private ImageView backMenu;
    private int supportId;
    private SimpleDateFormat formatter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_support_details);
        mContext = this;

        getLayoutUiId();

        Tv_Title.setText("Support Details");
        backMenu.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            supportId = extras.getInt("SupportId");
        }

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getUserSupportDetail(supportId);
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

    }

    private void getUserSupportDetail(int supportId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<SupportDetailResponse> supportDetailResponseCall = apiClientNetwork.getSupportDetailByid(supportId);
            supportDetailResponseCall.enqueue(new Callback<SupportDetailResponse>() {
                @Override
                public void onResponse(Call<SupportDetailResponse> call, Response<SupportDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getSupportDetailData() != null) {
                                setSupportDetailDataInTextView(response.body().getSupportDetailData());
                            } else {
                                Toast.makeText(mContext, "Support details data not found!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<SupportDetailResponse> call, Throwable t) {
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

    private void setSupportDetailDataInTextView(SupportDetailData supportDetailData) {
        try {
            reasonType_Tv.setText(supportDetailData.getSupportTypeValue());
            supportStatus_Tv.setText(supportDetailData.getStrReasonStatus());
            formatter = new SimpleDateFormat("dd-MMM-yyyy");
            date = formatter.format(Date.parse(supportDetailData.getRcdInsTs()));
            supportDate_Tv.setText(date);
            reasonDetails_Tv.setText(supportDetailData.getSupportDetails());
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            reasonType_Tv = (TextView) findViewById(R.id.reasonType_Tv);
            supportStatus_Tv = (TextView) findViewById(R.id.supportStatus_Tv);
            supportDate_Tv = (TextView) findViewById(R.id.supportDate_Tv);
            reasonDetails_Tv = (TextView) findViewById(R.id.reasonDetails_Tv);

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
package com.ezpms.ezpmsapp.PMSSetup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Activities.AddUserActivity;
import com.ezpms.ezpmsapp.Adapters.UsersListAdapter;
import com.ezpms.ezpmsapp.Interfaces.ApiClientNetwork;
import com.ezpms.ezpmsapp.Models.AllAppUsersData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.ResponseModels.AllAppUsersResponse;
import com.ezpms.ezpmsapp.Utilities.ApiClients;
import com.ezpms.ezpmsapp.Utilities.NetworkStatus;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersPMSSetupActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, textUsersData;
    private ImageView backMenu, addNewUser_ImBtn;
    private EditText et_SearchingByNameNumber;
    private RecyclerView usersListRc;
    private UsersListAdapter usersListAdapter;
    private GridLayoutManager linearLayoutManager;
    private List<AllAppUsersData> allAppUsersData = null;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_p_m_s_setup);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayouUiId();

        Tv_Title.setText("Users List");
        backMenu.setOnClickListener(this);
        addNewUser_ImBtn.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getAllAppUsersForEdit(sessionManager.getLoginUserId());
        } else {
            Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
        }

        getSearchUsers();
    }

    private void getAllAppUsersForEdit(String loginUserId) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiClientNetwork apiClientNetwork = ApiClients.getRetrofit().create(ApiClientNetwork.class);
            Call<AllAppUsersResponse> appUsersResponseCall = apiClientNetwork.getAllAppUsers(Integer.valueOf(loginUserId));
            appUsersResponseCall.enqueue(new Callback<AllAppUsersResponse>() {
                @Override
                public void onResponse(Call<AllAppUsersResponse> call, Response<AllAppUsersResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getRespMsg().equalsIgnoreCase("SUCCESS")) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.body().getAllAppUsersData() != null) {
                                allAppUsersData = response.body().getAllAppUsersData();
                                if (allAppUsersData.size() >= 0) {
                                    textUsersData.setVisibility(View.GONE);
                                    usersListRc.setVisibility(View.VISIBLE);
                                    getRecyclerForUsersList(allAppUsersData);
                                } else {
                                    textUsersData.setVisibility(View.VISIBLE);
                                    usersListRc.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "User data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                textUsersData.setVisibility(View.VISIBLE);
                                usersListRc.setVisibility(View.GONE);
                                Toast.makeText(mContext, "User data not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            textUsersData.setVisibility(View.VISIBLE);
                            usersListRc.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        textUsersData.setVisibility(View.VISIBLE);
                        usersListRc.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.please_check_your_connection, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllAppUsersResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    textUsersData.setVisibility(View.VISIBLE);
                    usersListRc.setVisibility(View.GONE);
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getRecyclerForUsersList(List<AllAppUsersData> allAppUsersData) {
        try {
            if (allAppUsersData.size() == 0) {
                textUsersData.setVisibility(View.VISIBLE);
                usersListRc.setVisibility(View.GONE);
            } else {
                textUsersData.setVisibility(View.GONE);
                usersListRc.setVisibility(View.VISIBLE);
                usersListAdapter = new UsersListAdapter(mContext, allAppUsersData);
                linearLayoutManager = new GridLayoutManager(mContext, 2);
                usersListRc.setLayoutManager(linearLayoutManager);
                usersListRc.setAdapter(usersListAdapter);
                usersListAdapter.notifyDataSetChanged();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getSearchUsers() {
        try {
            et_SearchingByNameNumber.addTextChangedListener(new TextWatcher() {
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
            List<AllAppUsersData> temp = new ArrayList();
            for (AllAppUsersData patient : allAppUsersData) {
                if (patient.getFirstName().toLowerCase().contains(toString) || patient.getLastName().toLowerCase().contains(toString)
                        || patient.getPhoneNumber().toLowerCase().contains(toString)) {
                    temp.add(patient);
                }
            }
            usersListAdapter.updateAllUsersList(temp);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayouUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            textUsersData = (TextView) findViewById(R.id.textUsersData);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            addNewUser_ImBtn = (ImageView) findViewById(R.id.addNewUser_ImBtn);
            usersListRc = (RecyclerView) findViewById(R.id.usersListRc);
            et_SearchingByNameNumber = (EditText) findViewById(R.id.et_SearchingByNameNumber);

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

                case R.id.addNewUser_ImBtn:
                    startActivity(new Intent(mContext, AddUserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    break;
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }
}
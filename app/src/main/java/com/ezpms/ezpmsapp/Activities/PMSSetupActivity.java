package com.ezpms.ezpmsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezpms.ezpmsapp.PMSSetup.AboutPMSActivity;
import com.ezpms.ezpmsapp.PMSSetup.EditAdminProfileActivity;
import com.ezpms.ezpmsapp.PMSSetup.NoticePMSSetupActivity;
import com.ezpms.ezpmsapp.PMSSetup.UpdateUserIdActivity;
import com.ezpms.ezpmsapp.PMSSetup.UsersPMSSetupActivity;
import com.ezpms.ezpmsapp.PMSSetup.VatechSVCActivity;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

public class PMSSetupActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title;
    private ImageView backMenu;
    private LinearLayout Users, notice, vatechsvc, updateUserId, editAdminProfile, aboutPMS;
    private SessionManager sessionManager;
    protected boolean isClinicAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_m_s_setup);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        getLayoutUiId();

        Tv_Title.setText("PMS Setup");

        isClinicAdmin = sessionManager.getIsDoctorClinicAdmin();

        backMenu.setOnClickListener(this);
        Users.setOnClickListener(this);
        notice.setOnClickListener(this);
        vatechsvc.setOnClickListener(this);
        updateUserId.setOnClickListener(this);
        editAdminProfile.setOnClickListener(this);
        aboutPMS.setOnClickListener(this);
    }

    private void getLayoutUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            backMenu = (ImageView) findViewById(R.id.backMenu);
            Users = (LinearLayout) findViewById(R.id.Users);
            notice = (LinearLayout) findViewById(R.id.notice);
            vatechsvc = (LinearLayout) findViewById(R.id.vatechsvc);
            updateUserId = (LinearLayout) findViewById(R.id.updateUserId);
            editAdminProfile = (LinearLayout) findViewById(R.id.editAdminProfile);
            aboutPMS = (LinearLayout) findViewById(R.id.aboutPMS);

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

            case R.id.Users:
                startActivity(new Intent(mContext, UsersPMSSetupActivity.class));
                break;

            case R.id.notice:
                startActivity(new Intent(mContext, NoticePMSSetupActivity.class));
                break;

            case R.id.vatechsvc:
                startActivity(new Intent(mContext, VatechSVCActivity.class));
                break;

            case R.id.updateUserId:
                startActivity(new Intent(mContext, UpdateUserIdActivity.class));
                break;

            case R.id.editAdminProfile:
                startActivity(new Intent(mContext, EditAdminProfileActivity.class));
               /* if (isClinicAdmin) {

                } else {
                    Toast.makeText(mContext, "You are not clinic admin!", Toast.LENGTH_SHORT).show();
                }*/
                break;

            case R.id.aboutPMS:
                startActivity(new Intent(mContext, AboutPMSActivity.class));
                break;
        }
    }
}
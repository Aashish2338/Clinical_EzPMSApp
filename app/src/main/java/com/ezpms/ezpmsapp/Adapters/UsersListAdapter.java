package com.ezpms.ezpmsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.Activities.UserUpdateActivity;
import com.ezpms.ezpmsapp.Models.AllAppUsersData;
import com.ezpms.ezpmsapp.R;
import com.ezpms.ezpmsapp.Utilities.SessionManager;
import com.ezpms.ezpmsapp.ViewHolders.UsersListViewHolder;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListViewHolder> {

    private Context mContext;
    private List<AllAppUsersData> allAppUsersData;
    private SessionManager sessionManager;
    private boolean isClinicAdmin = true;

    public UsersListAdapter(Context mContext, List<AllAppUsersData> allAppUsersData) {
        this.mContext = mContext;
        this.allAppUsersData = allAppUsersData;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public UsersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.users_list_layout, parent, false);
        return new UsersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersListViewHolder holder, int position) {
        holder.usersName.setText(allAppUsersData.get(position).getFirstName() + " " + allAppUsersData.get(position).getLastName());
        holder.usersMobile.setText(allAppUsersData.get(position).getPhoneNumber());

        if (allAppUsersData.get(position).getDoctorClinicAdmin() == true) {
            holder.doctorProfile.setText("Doctor");
        } else {
            holder.doctorProfile.setText("Dentist");
        }

        if (allAppUsersData.get(position).getGender().equalsIgnoreCase("Male")) {
            holder.usersImageP.setImageResource(R.drawable.doctor_avatar_org);
        } else {
            holder.usersImageP.setImageResource(R.drawable.doctor_female_avatar);
        }

        setOptionMenuForEdit(holder, position);
    }

    private void setOptionMenuForEdit(UsersListViewHolder holder, int position) {
        try {
            holder.moreOptions_Tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(mContext, holder.moreOptions_Tv);
                    popup.inflate(R.menu.users_pms_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.editProfile_Menu:
                                    isClinicAdmin = allAppUsersData.get(position).getDoctorClinicAdmin();
                                    if (isClinicAdmin == false) {
                                        Intent intent = new Intent(mContext, UserUpdateActivity.class);
                                        intent.putExtra("FirstName", allAppUsersData.get(position).getFirstName());
                                        intent.putExtra("LastName", allAppUsersData.get(position).getLastName());
                                        intent.putExtra("PhoneNumber", allAppUsersData.get(position).getPhoneNumber());
                                        intent.putExtra("Email", allAppUsersData.get(position).getEmail());
                                        intent.putExtra("RoleId", allAppUsersData.get(position).getRoleId());
                                        intent.putExtra("UserId", allAppUsersData.get(position).getUserId());
                                        intent.putExtra("Password", allAppUsersData.get(position).getEncryptedPassword());
                                        intent.putExtra("IsActive", allAppUsersData.get(position).getIsActive());
                                        intent.putExtra("Gender", allAppUsersData.get(position).getGender());
                                        mContext.startActivity(intent);
                                    } else {
                                        Toast.makeText(mContext, "You are not clinic admin!", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                }
            });
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allAppUsersData.size();
    }

    public void updateAllUsersList(List<AllAppUsersData> list) {
        allAppUsersData = list;
        notifyDataSetChanged();
    }
}
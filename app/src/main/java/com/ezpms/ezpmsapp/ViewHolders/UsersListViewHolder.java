package com.ezpms.ezpmsapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezpms.ezpmsapp.R;

public class UsersListViewHolder extends RecyclerView.ViewHolder {

    public ImageView usersImageP;
    public TextView usersName, usersMobile, doctorProfile, moreOptions_Tv;

    public UsersListViewHolder(@NonNull View itemView) {
        super(itemView);

        usersImageP = (ImageView) itemView.findViewById(R.id.usersImageP);
        usersName = (TextView) itemView.findViewById(R.id.usersName);
        usersMobile = (TextView) itemView.findViewById(R.id.usersMobile);
        doctorProfile = (TextView) itemView.findViewById(R.id.doctorProfile);
        moreOptions_Tv = (TextView) itemView.findViewById(R.id.moreOptions_Tv);

    }
}

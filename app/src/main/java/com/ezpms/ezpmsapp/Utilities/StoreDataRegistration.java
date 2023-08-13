package com.ezpms.ezpmsapp.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreDataRegistration {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String APP_SHARED_PREFS;
    private Context mContext;

    public StoreDataRegistration(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        APP_SHARED_PREFS = "EZPMS.com";
    }

    public void setRegUserTitle(String userTitle) {
        editor.putString("userTitle", userTitle);
        editor.commit();
    }

    public String getRegUserTitle() {
        return sharedPreferences.getString("userTitle", " ");
    }

    public void setRegUserFirstName(String userFirstName) {
        editor.putString("userFirstName", userFirstName);
        editor.commit();
    }

    public String getRegUserFirstName() {
        return sharedPreferences.getString("userFirstName", " ");
    }

    public void setRegUserLastName(String userLastName) {
        editor.putString("userLastName", userLastName);
        editor.commit();
    }

    public String getRegUserLastName() {
        return sharedPreferences.getString("userLastName", " ");
    }

    public void setRegUserMaleFemal(String userMaleFemal) {
        editor.putString("userMaleFemal", userMaleFemal);
        editor.commit();
    }

    public String getRegUserMaleFemal() {
        return sharedPreferences.getString("userMaleFemal", " ");
    }

    public void setRegUserClinicName(String userClinicName) {
        editor.putString("userClinicName", userClinicName);
        editor.commit();
    }

    public String getRegUserClinicName() {
        return sharedPreferences.getString("userClinicName", " ");
    }

    public void setRegUserAddressFirst(String userAddressFirst) {
        editor.putString("userAddressFirst", userAddressFirst);
        editor.commit();
    }

    public String getRegUserAddressFirst() {
        return sharedPreferences.getString("userAddressFirst", " ");
    }

    public void setRegUserAddressSecond(String userAddressSecond) {
        editor.putString("userAddressSecond", userAddressSecond);
        editor.commit();
    }

    public String getRegUserAddressSecond() {
        return sharedPreferences.getString("userAddressSecond", " ");
    }

    public void setRegUserCity(String userCity) {
        editor.putString("userCity", userCity);
        editor.commit();
    }

    public String getRegUserCity() {
        return sharedPreferences.getString("userCity", " ");
    }

    public void setRegUserState(String userState) {
        editor.putString("userState", userState);
        editor.commit();
    }

    public String getRegUserState() {
        return sharedPreferences.getString("userState", " ");
    }

    public void setRegUserCountry(String userCountry) {
        editor.putString("userCountry", userCountry);
        editor.commit();
    }

    public String getRegUserCountry() {
        return sharedPreferences.getString("userCountry", " ");
    }

    public void setRegUserPinCode(String userPinCode) {
        editor.putString("userPinCode", userPinCode);
        editor.commit();
    }

    public String getRegUserPinCode() {
        return sharedPreferences.getString("userPinCode", " ");
    }

    public void setRegUserEmailAddress(String userEmailAddress) {
        editor.putString("userEmailAddress", userEmailAddress);
        editor.commit();
    }

    public String getRegUserEmailAddress() {
        return sharedPreferences.getString("userEmailAddress", " ");
    }

    public void setRegUserMobileNumber(String userMobileNumber) {
        editor.putString("userMobileNumber", userMobileNumber);
        editor.commit();
    }

    public String getRegUserMobileNumber() {
        return sharedPreferences.getString("userMobileNumber", " ");
    }

    public void setRegUserPatientRegi(String userPatientRegi) {
        editor.putString("userPatientRegi", userPatientRegi);
        editor.commit();
    }

    public String getRegUserPatientRegi() {
        return sharedPreferences.getString("userPatientRegi", " ");
    }

    public void setRegUserPackageName(String userPackageName) {
        editor.putString("userPackageName", userPackageName);
        editor.commit();
    }

    public String getRegUserPackageName() {
        return sharedPreferences.getString("userPackageName", " ");
    }

    public void setRegUserClinicImageUpload(String userClinicImageUpload) {
        editor.putString("userClinicImageUpload", userClinicImageUpload);
        editor.commit();
    }

    public String getRegUserClinicImageUpload() {
        return sharedPreferences.getString("userClinicImageUpload", " ");
    }

    public void setRegUserPassword(String userPassword) {
        editor.putString("userPassword", userPassword);
        editor.commit();
    }

    public String getRegUserPassword() {
        return sharedPreferences.getString("userPassword", " ");
    }

    public void setRegUserClinicAdmin(String userClinicAdmin) {
        editor.putString("userClinicAdmin", userClinicAdmin);
        editor.commit();
    }

    public String getRegUserClinicAdmin() {
        return sharedPreferences.getString("userClinicAdmin", " ");
    }
}
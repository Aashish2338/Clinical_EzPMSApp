package com.ezpms.ezpmsapp.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String APP_SHARED_PREFS;
    private Context mContext;

    public SessionManager(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        APP_SHARED_PREFS = "ezPMS.com";
    }

    public void setLoginUserId(String loginUserId) {
        editor.putString("loginUserId", loginUserId);
        editor.commit();
    }

    public String getLoginUserId() {
        return sharedPreferences.getString("loginUserId", " ");
    }

    public void setUserFirstName(String FirstName) {
        editor.putString("loginUserFirstName", FirstName);
        editor.commit();
    }

    public String getUserFirstName() {
        return sharedPreferences.getString("loginUserFirstName", " ");
    }

    public void setUserLastName(String LastName) {
        editor.putString("loginUserLastName", LastName);
        editor.commit();
    }

    public String getUserLastName() {
        return sharedPreferences.getString("loginUserLastName", " ");
    }

    public void setOTP(String otp) {
        editor.putString("otp", otp);
        editor.commit();
    }

    public String getOTP() {
        return sharedPreferences.getString("otp", " ");
    }

    public void setUserFullName(String FullName) {
        editor.putString("loginUserFullName", FullName);
        editor.commit();
    }

    public String getUserFullName() {
        return sharedPreferences.getString("loginUserFullName", " ");
    }

    public void setUserEmail(String loginUserEmail) {
        editor.putString("loginUserEmail", loginUserEmail);
        editor.commit();
    }

    public String getUserEmail() {
        return sharedPreferences.getString("loginUserEmail", " ");
    }

    public void setUserPassword(String loginPassword) {
        editor.putString("loginPassword", loginPassword);
        editor.commit();
    }

    public String getUserPassword() {
        return sharedPreferences.getString("loginPassword", " ");
    }

    public void setUserPhoneNumber(String phoneNumber) {
        editor.putString("phoneNumber", phoneNumber);
        editor.commit();
    }

    public String getUserPhoneNumber() {
        return sharedPreferences.getString("phoneNumber", " ");
    }

    public void setUserClinicId(int ClinicId) {
        editor.putInt("ClinicId", ClinicId);
        editor.commit();
    }

    public int getUserCliniId() {
        return sharedPreferences.getInt("ClinicId", 0);
    }

    public void setRegisterClinicId(int regUserId) {
        editor.putInt("regUserId", regUserId);
        editor.commit();
    }

    public int getRegisterCliniId() {
        return sharedPreferences.getInt("regUserId", 0);
    }

    public void setUserClinicName(String ClinicName) {
        editor.putString("ClinicName", ClinicName);
        editor.commit();
    }

    public String getUserClinicName() {
        return sharedPreferences.getString("ClinicName", " ");
    }

    public void setUserClinicAddressFi(String ClinicAddressF) {
        editor.putString("ClinicAddressF", ClinicAddressF);
        editor.commit();
    }

    public String getUserClinicAddressFi() {
        return sharedPreferences.getString("ClinicAddressF", " ");
    }


    public void setUserClinicAddressSe(String ClinicAddressSe) {
        editor.putString("ClinicAddressSe", ClinicAddressSe);
        editor.commit();
    }

    public String getUserClinicAddressSe() {
        return sharedPreferences.getString("ClinicAddressSe", " ");
    }

    public void setDoctorId(int doctorId) {
        editor.putInt("doctorId", doctorId);
        editor.commit();
    }

    public int getDoctorId() {
        return sharedPreferences.getInt("doctorId", 0);
    }

    public void setUserImage(String loginUserImage) {
        editor.putString("loginUserImage", loginUserImage);
        editor.commit();
    }

    public String getUserImage() {
        return sharedPreferences.getString("loginUserImage", " ");
    }

    public void setAdminFirstName(String AdminFirstName) {
        editor.putString("AdminFirstName", AdminFirstName);
        editor.commit();
    }

    public String getAdminFirstName() {
        return sharedPreferences.getString("AdminFirstName", " ");
    }

    public void setAdminLastName(String AdminLastName) {
        editor.putString("AdminLastName", AdminLastName);
        editor.commit();
    }

    public String getAdminLastName() {
        return sharedPreferences.getString("AdminLastName", " ");
    }

    public void setAdminGender(String AdminGender) {
        editor.putString("AdminGender", AdminGender);
        editor.commit();
    }

    public String getAdminGender() {
        return sharedPreferences.getString("AdminGender", " ");
    }

    public void setUserGender(String UserGender) {
        editor.putString("UserGender", UserGender);
        editor.commit();
    }

    public String getUserGender() {
        return sharedPreferences.getString("UserGender", " ");
    }

    public void setIsDoctorClinicAdmin(Boolean IsAdmin) {
        editor.putBoolean("IsAdmin", IsAdmin);
        editor.commit();
    }

    public Boolean getIsDoctorClinicAdmin() {
        return sharedPreferences.getBoolean("IsAdmin", false);
    }

    public void setPostalCode(String PostanCode) {
        editor.putString("PostanCode", PostanCode);
        editor.commit();
    }

    public String getPostalCode() {
        return sharedPreferences.getString("PostanCode", " ");
    }

    public void setAddress(String Address) {
        editor.putString("Address", Address);
        editor.commit();
    }

    public String getAddress() {
        return sharedPreferences.getString("Address", " ");
    }

    public void setLogInDirection(String logInDirection) {
        editor.putString("logInDirection", logInDirection);
        editor.commit();
    }

    public String getLogInDirection() {
        return sharedPreferences.getString("logInDirection", "start");
    }

    public void setAppointmentId(int appointmentId) {
        editor.putInt("AppointmentId", appointmentId);
        editor.commit();
    }

    public int getAppointmentId() {
        return sharedPreferences.getInt("AppointmentId", 0);
    }

    public void setPatientId(int patientId) {
        editor.putInt("patientId", patientId);
        editor.commit();
    }

    public int getPatientId() {
        return sharedPreferences.getInt("patientId", 0);
    }

    public void setClinicId(int clinicId) {
        editor.putInt("clinicId", clinicId);
        editor.commit();
    }

    public int getClinicId() {
        return sharedPreferences.getInt("clinicId", 0);
    }

    public void ClearPreferences() {
        editor.clear().commit();
    }
}
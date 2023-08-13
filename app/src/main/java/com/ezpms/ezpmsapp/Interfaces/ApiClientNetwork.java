package com.ezpms.ezpmsapp.Interfaces;

import com.ezpms.ezpmsapp.ResponseModels.AllAppNoticesResponse;
import com.ezpms.ezpmsapp.ResponseModels.AllAppUsersResponse;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentBydateResponse;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentCountResponse;
import com.ezpms.ezpmsapp.ResponseModels.AllAppointmentResponse;
import com.ezpms.ezpmsapp.ResponseModels.AllTitleResponse;
import com.ezpms.ezpmsapp.ResponseModels.AllTreatmentResponse;
import com.ezpms.ezpmsapp.ResponseModels.AppointmentStatusResponse;
import com.ezpms.ezpmsapp.ResponseModels.BloodGroupResponse;
import com.ezpms.ezpmsapp.ResponseModels.CityResponse;
import com.ezpms.ezpmsapp.ResponseModels.ClinicRegisterResponse;
import com.ezpms.ezpmsapp.ResponseModels.ConsultationTypesResponse;
import com.ezpms.ezpmsapp.ResponseModels.CountryResponse;
import com.ezpms.ezpmsapp.ResponseModels.DoctorAvailableResponse;
import com.ezpms.ezpmsapp.ResponseModels.DocumentTypesResponse;
import com.ezpms.ezpmsapp.ResponseModels.LoginResponse;
import com.ezpms.ezpmsapp.ResponseModels.MedicalHistoryResponse;
import com.ezpms.ezpmsapp.ResponseModels.NoticesDetailResponse;
import com.ezpms.ezpmsapp.ResponseModels.PackagesResponse;
import com.ezpms.ezpmsapp.ResponseModels.PatientDetailByIDResponse;
import com.ezpms.ezpmsapp.ResponseModels.PatientListResponse;
import com.ezpms.ezpmsapp.ResponseModels.StateResponse;
import com.ezpms.ezpmsapp.ResponseModels.SupportDetailResponse;
import com.ezpms.ezpmsapp.ResponseModels.SupportReasonResponse;
import com.ezpms.ezpmsapp.ResponseModels.SupportResponse;
import com.ezpms.ezpmsapp.ResponseModels.SupportTypeResponse;
import com.ezpms.ezpmsapp.ResponseModels.TimeSlotsResponse;
import com.ezpms.ezpmsapp.ResponseModels.TreatmentDetailPatientResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClientNetwork {

    @FormUrlEncoded
    @POST("GetUsersAccLogin")
    Call<LoginResponse> getUsersAccLogin(@Field("UserName") String uerName, @Field("Password") String password);

    @FormUrlEncoded
    @POST("AppClinicRegistration")
    Call<ClinicRegisterResponse> createAppClinicRegistration(@Field("Title") String userTitle, @Field("FirstName") String firstName,
                                                             @Field("LastName") String lastName, @Field("Gender") String gender,
                                                             @Field("ClinicName") String clinicName, @Field("ClinicAddress") String clinicAddress,
                                                             @Field("OtherAddress") String otherAddress, @Field("CityId") String cityId,
                                                             @Field("StateId") String stateId, @Field("CountryId") String countryId,
                                                             @Field("PostalCode") String postalCode, @Field("IsActive") String isActive,
                                                             @Field("IsDeleted") String isDeleted, @Field("UserEmail") String userEmail,
                                                             @Field("strPasswordHash") String strPasswordHash, @Field("ConcurrencyStamp") String ConcurrencyStamp,
                                                             @Field("PhoneNumber") String phoneNumber, @Field("StartPatientId") String startPatientId,
                                                             @Field("PackageId") String packageId, @Field("PackageSubscribed") String packageSubscribed,
                                                             @Field("CreatedBy") String createdBy, @Field("ModifiedBy") String modifiedBy,
                                                             @Field("VerificationCode") String verificationCode, @Field("AvatarImage") String avatarImage,
                                                             @Field("EncryptedPassword") String encryptedPassword, @Field("RoleId") String roleId,
                                                             @Field("DoctorClinicAdmin") String doctorClinicAdmin);

    @FormUrlEncoded
    @POST("GetAppVarificationCode")
    Call<ClinicRegisterResponse> getAppVarificationCode(@Field("ClinicId") int clinicId, @Field("VerificationCode") String verificationCode);

    @FormUrlEncoded
    @POST("PatientDetailsRegistration")
    Call<ClinicRegisterResponse> createPatientRegistration(@Field("PatientIdDisp") String patientIdDisp, @Field("Title") String userTitle,
                                                           @Field("PatientFirstName") String firstName, @Field("PatientLastName") String lastName,
                                                           @Field("Gender") String gender, @Field("PatientType") String patientType,
                                                           @Field("Age") String Age, @Field("EmailAddress") String EmailAddress,
                                                           @Field("PhoneNumber") String PhoneNumber, @Field("PatientAddress1") String PatientAddress1,
                                                           @Field("PatientAddress2") String PatientAddress2, @Field("CityId") String CityId,
                                                           @Field("StateId") String StateId, @Field("CountryId") String CountryId,
                                                           @Field("PostalCode") String PostalCode, @Field("CountryCode") String CountryCode,
                                                           @Field("CreatedBy") String CreatedBy, @Field("DOB") String DOB,
                                                           @Field("BloodGroup") String BloodGroup, @Field("MedicalHistory") String MedicalHistory,
                                                           @Field("OtherMedicalHistory") String OtherMedicalHistory,
                                                           @Field("LastVisitDate") String LastVisitDate, @Field("ClinicId") String ClinicId);

    @FormUrlEncoded
    @POST("UpdatePatientDetails")
    Call<ClinicRegisterResponse> updatePatientDetails(@Field("PatientId") int patientId, @Field("PatientIdDisp") String patientIdDisp,
                                                      @Field("Title") String userTitle, @Field("PatientFirstName") String firstName,
                                                      @Field("PatientLastName") String lastName, @Field("Gender") String gender,
                                                      @Field("PatientType") String patientType, @Field("Age") String Age,
                                                      @Field("EmailAddress") String EmailAddress, @Field("PhoneNumber") String PhoneNumber,
                                                      @Field("PatientAddress1") String PatientAddress1, @Field("PatientAddress2") String PatientAddress2,
                                                      @Field("CityId") String CityId, @Field("StateId") String StateId, @Field("CountryId") String CountryId,
                                                      @Field("PostalCode") String PostalCode, @Field("CountryCode") String CountryCode,
                                                      @Field("CreatedBy") String CreatedBy, @Field("DOB") String DOB,
                                                      @Field("BloodGroup") String BloodGroup, @Field("MedicalHistory") String MedicalHistory,
                                                      @Field("OtherMedicalHistory") String OtherMedicalHistory, @Field("LastVisitDate") String LastVisitDate,
                                                      @Field("LocalInsUpdtTime") String LocalInsUpdtTime, @Field("ServerInsUpdtTime") String ServerInsUpdtTime,
                                                      @Field("rcdInsTs") String rcdInsTs, @Field("rcdUpdtTs") String rcdUpdtTs,
                                                      @Field("IsActive") String IsActive, @Field("IsDeleted") String IsDeleted,
                                                      @Field("ClinicId") String ClinicId);

    @FormUrlEncoded
    @POST("ManageAppAppointments")
    Call<ClinicRegisterResponse> getManageAppAppointments(@Field("PatientId") int patientId, @Field("ClinicId") int clinicId,
                                                          @Field("UserId") String userId, @Field("AppointmentDate") String appointmentDate,
                                                          @Field("ConsultationType") String consultationType,
                                                          @Field("AppointmentStatus") String appointmentStatus, @Field("TimeSlot") int timeSlot,
                                                          @Field("KeyNotes") String keyNotes, @Field("CreatedBy") String createdBy);

    @FormUrlEncoded
    @POST("RescheduledAppointments")
    Call<ClinicRegisterResponse> getRescheduledAppointments(@Field("AppointmentId") int appointmentId, @Field("PatientId") int patientId,
                                                            @Field("ClinicId") int clinicId, @Field("UserId") String userId, @Field("AppointmentDate") String appointmentDate,
                                                            @Field("ConsultationType") String consultationType, @Field("AppointmentStatus") String appointmentStatus,
                                                            @Field("TimeSlot") int timeSlot, @Field("KeyNotes") String keyNotes, @Field("CreatedBy") String createdBy);

    @FormUrlEncoded
    @POST("CancelAppAppointments")
    Call<ClinicRegisterResponse> getCancelAppAppointments(@Field("AppointmentId") int appointmentId, @Field("PatientId") int patientId, @Field("ClinicId") int clinicId,
                                                          @Field("UserId") String userId, @Field("AppointmentStatus") String appointmentStatus,
                                                          @Field("CancellationUpdtTs") int cancellationUpdtTs, @Field("CancellationReason") String cancellationReason,
                                                          @Field("CreatedBy") String createdBy);

    @FormUrlEncoded
    @POST("AddTreatmentDetail")
    Call<ClinicRegisterResponse> addTreatmentDetail(@Field("appAppointmentId") int appAppointmentId, @Field("UserId") String userId, @Field("DiseaseSymptoms") String diseaseSymptoms,
                                                    @Field("TreatmentDate") String treatmentDate, @Field("DiseaseTreatment") String diseaseTreatment,
                                                    @Field("DiseaseMedicinePrescribed") String diseaseMedicinePrescribed, @Field("CreatedBy") String createdBy);

    @FormUrlEncoded
    @POST("AddAppDocument")
    Call<ClinicRegisterResponse> addAppDocument(@Field("DocumentTypeID") int documentTypeID, @Field("PatientId") int patientId, @Field("TreatmentId") int treatmentId,
                                                @Field("AppointmentId") int appointmentId, @Field("ImageName") String imageName, @Field("ImageInByte") String imageInByte,
                                                @Field("CreatedBy") String createdBy);

    @FormUrlEncoded
    @POST("UpdateUserProfile")
    Call<ClinicRegisterResponse> updateUserProfile(@Field("UserID") int userID, @Field("FirstName") String firstName, @Field("LastName") String lastName,
                                                   @Field("Gender") String gender, @Field("RoleId") int roleId, @Field("Title") String title,
                                                   @Field("UserEmail") String userEmail, @Field("PhoneNumber") String phoneNumber, @Field("ClinicAddress") String clinicAddress,
                                                   @Field("OtherAddress") String otherAddress, @Field("StateId") String stateId, @Field("CityId") String cityId,
                                                   @Field("CountryId") String cuntryId, @Field("PostalCode") String postalCode, @Field("ConcurrencyStamp") String concurrencyStamp);

    @FormUrlEncoded
    @POST("AddSupportDetail")
    Call<ClinicRegisterResponse> addSupportDetail(@Field("SupportTypeId") int supportTypeId, @Field("ClinicId") int clinicId, @Field("SupportDetails") String supportDetails,
                                                  @Field("ReasonStatus") String reasonStatus, @Field("CreatedBy") String createdBy);

    @FormUrlEncoded
    @POST("UpdateUserProfile")
    Call<ClinicRegisterResponse> updateUsersProfiles(@Field("UserID") String userID, @Field("FirstName") String firstName, @Field("LastName") String lastName,
                                                     @Field("Gender") String gender, @Field("Email") String email, @Field("PhoneNumber") String phoneNumber,
                                                     @Field("Address1") String address1, @Field("Address2") String address2, @Field("StateId") String stateId,
                                                     @Field("CityId") String cityId, @Field("CountryId") String countryId, @Field("PostalCode") String postalCode);

    @FormUrlEncoded
    @POST("UpdateUserIDDetail")
    Call<ClinicRegisterResponse> updateUserIDDetail(@Field("appUserId") String appUserId, @Field("Email") String email, @Field("PhoneNumber") String phoneNumber,
                                                    @Field("userName") String userName, @Field("ModifiedBy") String modifiedBy);

    @FormUrlEncoded
    @POST("ChangePasswordDetail")
    Call<ClinicRegisterResponse> changePasswordDetail(@Field("appUserId") String appUserId, @Field("Email") String email, @Field("PhoneNumber") String phoneNumber,
                                                      @Field("OldPassword") String oldPassword, @Field("Password") String password, @Field("ModifiedBy") String modifiedBy);

    @POST("SendOtp")
    Call<ClinicRegisterResponse> sendOtp(@Field("userid") int userid, @Field("mobileNo") String mobileNo, @Field("email") String email);

    @FormUrlEncoded
    @POST("ForgotPassword")
    Call<ClinicRegisterResponse> getForgotPassword(@Field("Password") String password, @Field("appUserId") int appUserId,
                                                   @Field("Email") String email, @Field("PhoneNumber") String phoneNumber,
                                                   @Field("ModifiedBy") int modifiedBy);

    @FormUrlEncoded
    @POST("GetAllAppointmentCount")
    Call<AllAppointmentCountResponse> getAllAppointmentCount(@Field("FromDate") String fromDate, @Field("ToDate") String toDate);

    @FormUrlEncoded
    @POST("GetAllAppointmentBydate")
    Call<AllAppointmentBydateResponse> getAllAppointmentBydate(@Field("FromDate") String fromDate, @Field("ToDate") String toDate/*, @Field("UserID") int userID*/);

    @GET("GetAllCountry")
    Call<CountryResponse> getCountryList(@Query("countryId") int countryId);

    @GET("GetAllStates")
    Call<StateResponse> getAllStateName(@Query("countryId") int countryId, @Query("stateId") int stateId);

    @GET("GetAllCity")
    Call<CityResponse> getAllCityName(@Query("stateId") int countryId, @Query("cityId") int stateId);

    @GET("GetAllTitle")
    Call<AllTitleResponse> getAllTitle();

    @GET("GetAllAppointmentStatus")
    Call<AppointmentStatusResponse> getAllAppointmentStatus();

    @GET("GetAllAppointment")
    Call<AllAppointmentResponse> getAllAppointment(@Query("Clinicid") int clinicid, @Query("UserId") String userId);

    @GET("GetGetDoctorList")
    Call<DoctorAvailableResponse> getGetDoctorList(@Query("ClinicId") int clinicId);

    @GET("GetAllPackages")
    Call<PackagesResponse> getAllPackages(@Query("packageId") int packageId);

    @GET("GetAllConsultationTypes")
    Call<ConsultationTypesResponse> getAllConsultationTypes(@Query("consultationType") int consultationType);

    @GET("GetAllTimeSlots")
    Call<TimeSlotsResponse> getAllTimeSlots(@Query("timeSlotId") int timeSlotId);

    @GET("GetAllTreatment")
    Call<AllTreatmentResponse> getAllTreatment(@Query("UserId") String userId);

    @GET("GetTreatmentDetailByPatientID")
    Call<TreatmentDetailPatientResponse> getTreatmentDetailByPatientID(@Query("PatientID") int patientID);

    @GET("GetAllDocumentTypes")
    Call<DocumentTypesResponse> getAllDocumentTypes();

    @GET("GetAllBloodGroup")
    Call<BloodGroupResponse> getAllBloodGroup(@Query("bloodGroupId") int bloodGroupId);

    @GET("GetAllMedicalHistory")
    Call<MedicalHistoryResponse> getAllMedicalHistory(@Query("medicalHistoryId") int medicalHistoryId);

    @GET("GetAllPatient")
    Call<PatientListResponse> getAllPatient(@Query("ClinicId") int clinicId);

    @GET("GetAllAppUsers")
    Call<AllAppUsersResponse> getAllAppUsers(@Query("userID") int userID);

/*    @GET("GetAllAppNotices")GetAllAppNoticesByClinicID
    Call<AllAppNoticesResponse> getAllAppNotices();*/

    @GET("GetAllAppNoticesByClinicID")
    Call<AllAppNoticesResponse> getAllAppNotices(@Query("clinicID") int clinicID);

    @GET("GetAppNoticesDetailByid")
    Call<NoticesDetailResponse> getAppNoticesDetailByid(@Query("NoticeId") int noticeId);

    @GET("GetAllSUpport")
    Call<SupportResponse> getAllSUpport(@Query("ClinicId") int clinicId);

    @GET("GetSupportDetailByid")
    Call<SupportDetailResponse> getSupportDetailByid(@Query("Supportid") int supportid);

    @GET("GetAllSupportType")
    Call<SupportTypeResponse> getAllSupportType(@Query("supportTypeId") int supportTypeId);

    @GET("GetAllSupportResion")
    Call<SupportReasonResponse> getAllSupportResion();

    @GET("GetPatientDetailByID")
    Call<PatientDetailByIDResponse> getPatientDetailByID(@Query("PatientId") int patientId);
}
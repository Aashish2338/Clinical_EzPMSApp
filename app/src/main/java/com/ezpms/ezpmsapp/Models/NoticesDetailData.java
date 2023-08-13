package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticesDetailData {
    @SerializedName("NoticeId")
    @Expose
    private Integer noticeId;
    @SerializedName("ClinicId")
    @Expose
    private Integer clinicId;
    @SerializedName("NoticeDate")
    @Expose
    private String noticeDate;
    @SerializedName("NoticeTitle")
    @Expose
    private String noticeTitle;
    @SerializedName("NoticeDetails")
    @Expose
    private String noticeDetails;
    @SerializedName("IsRead")
    @Expose
    private Boolean isRead;
    @SerializedName("srno")
    @Expose
    private Integer srno;
    @SerializedName("NoticeFullTitle")
    @Expose
    private Object noticeFullTitle;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(String noticeDetails) {
        this.noticeDetails = noticeDetails;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public Object getNoticeFullTitle() {
        return noticeFullTitle;
    }

    public void setNoticeFullTitle(Object noticeFullTitle) {
        this.noticeFullTitle = noticeFullTitle;
    }
}

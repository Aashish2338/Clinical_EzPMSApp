package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.PackageData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackagesResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<PackageData> packageData = null;

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public List<PackageData> getPackageData() {
        return packageData;
    }

    public void setPackageData(List<PackageData> packageData) {
        this.packageData = packageData;
    }
}

package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.DocumentTypesData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentTypesResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<DocumentTypesData> documentTypesData = null;

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

    public List<DocumentTypesData> getDocumentTypesData() {
        return documentTypesData;
    }

    public void setDocumentTypesData(List<DocumentTypesData> documentTypesData) {
        this.documentTypesData = documentTypesData;
    }
}

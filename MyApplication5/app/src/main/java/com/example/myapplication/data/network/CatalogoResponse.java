package com.example.myapplication.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatalogoResponse {
    @SerializedName("Sanit_abastecimiento")
    @Expose
    private List<SanitAbastecimiento> sanitAbastecimiento;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SanitAbastecimiento> getSanitAbastecimiento() {
        return sanitAbastecimiento;
    }

    public void setSanitAbastecimiento(List<SanitAbastecimiento> sanitAbastecimiento) {
        this.sanitAbastecimiento = sanitAbastecimiento;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

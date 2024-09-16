package com.umda.poetryapp.Response;

import com.umda.poetryapp.Models.PoetryModel;

import java.util.List;

public class GetPoetryResponse {
    String status;
    String message;
    List<PoetryModel> data;

    public GetPoetryResponse(List<PoetryModel> data, String status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public List<PoetryModel> getData() {
        return data;
    }

    public List<PoetryModel> setData(List<PoetryModel> data) {
        this.data = data;
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

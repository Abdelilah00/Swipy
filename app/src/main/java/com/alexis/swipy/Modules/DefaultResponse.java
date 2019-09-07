package com.alexis.swipy.Modules;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;


    public DefaultResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}

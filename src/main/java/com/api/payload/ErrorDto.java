package com.api.payload;

import java.util.Date;

public class ErrorDto {
    private String errorMsg;
    private Date date;
    private String uri;

    public ErrorDto(String errorMsg, Date date, String uri) {
        this.errorMsg = errorMsg;
        this.date = date;
        this.uri = uri;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Date getDate() {
        return date;
    }

    public String getUri() {
        return uri;
    }
}

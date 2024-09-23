package com.external.calculator.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

    private String message;

    private Object data;

    private String errorCode;

    public RestResponse(final Boolean success, final String message, final Object data) {
        this(success, message, data, null);
    }

    public RestResponse(String errorMessage, String errorCode) {
        this(Boolean.FALSE, errorMessage, null, errorCode);
    }

}
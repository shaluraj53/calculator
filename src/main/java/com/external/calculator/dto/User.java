package com.external.calculator.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {

    private String id;

    private String name;

    private String mobile;

    private String role;

    private LocalDate createdOn;

}

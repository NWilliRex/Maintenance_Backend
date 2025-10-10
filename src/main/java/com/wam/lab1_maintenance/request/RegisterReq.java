package com.wam.lab1_maintenance.request;

public record RegisterReq(
        String fname,
        String lname,
        String username,
        Integer age,
        String password
){}

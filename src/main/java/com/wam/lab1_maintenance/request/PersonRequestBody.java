package com.wam.lab1_maintenance.request;

import com.wam.lab1_maintenance.model.Gender;

//un record c'est pas modifiable, il ya juste des getter
public record PersonRequestBody(
        String fname,
        String lname,
        Integer age,
        Gender gender
) {}

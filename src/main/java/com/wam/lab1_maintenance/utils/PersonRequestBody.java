package com.wam.lab1_maintenance.utils;

//un record c'est pas modifiable, il ya juste des getter

public record PersonRequestBody(String name, Integer age, String gender) {

}

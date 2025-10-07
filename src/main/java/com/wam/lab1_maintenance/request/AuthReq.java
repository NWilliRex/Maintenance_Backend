package com.wam.lab1_maintenance.request;

import lombok.Builder;

@Builder
public record AuthReq(
       String username,
       String password
) {}

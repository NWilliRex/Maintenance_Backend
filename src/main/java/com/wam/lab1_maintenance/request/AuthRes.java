package com.wam.lab1_maintenance.request;

import lombok.Builder;

@Builder
public record AuthRes(
        String token
) {}

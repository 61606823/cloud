package com.viki.cloud.gateway.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeBetweenConfigBean {
    private LocalTime start;
    private LocalTime end;
}

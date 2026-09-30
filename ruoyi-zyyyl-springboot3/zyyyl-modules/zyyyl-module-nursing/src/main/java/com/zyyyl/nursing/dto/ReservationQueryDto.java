package com.zyyyl.nursing.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ReservationQueryDto {

    private String name;

    private String mobile;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private Integer status;

    private Integer type;
}

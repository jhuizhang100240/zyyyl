package com.zyyyl.nursing.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReservationDto {

    private String name;

    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private String visitor;

    private Integer type;

    private Integer status;

    private Long elderId;
}

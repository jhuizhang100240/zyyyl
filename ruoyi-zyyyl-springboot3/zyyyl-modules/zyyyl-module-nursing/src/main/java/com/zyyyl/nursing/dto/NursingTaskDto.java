package com.zyyyl.nursing.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class NursingTaskDto {

    private Integer pageNum;

    private Integer pageSize;

    private String elderName;

    private Long nurseId;

    private Long projectId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private Integer status;
}

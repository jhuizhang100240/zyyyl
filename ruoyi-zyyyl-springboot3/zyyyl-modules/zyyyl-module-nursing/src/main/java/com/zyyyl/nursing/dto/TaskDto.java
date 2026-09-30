package com.zyyyl.nursing.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TaskDto {

    private Long taskId;

    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime estimatedServerTime;

    private String taskImage;

    private String mark;
}

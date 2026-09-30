package com.zyyyl.nursing.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TimeCountVo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;

    private Integer count;
}

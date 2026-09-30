package com.zyyyl.nursing.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyyyl.nursing.domain.CheckInConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入住详情中的入住配置。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckInConfigVo extends CheckInConfig {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String bedNumber;
}

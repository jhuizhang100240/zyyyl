package com.zyyyl.nursing.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 入住配置请求参数。
 */
@Data
public class CheckInConfigDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feeStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feeEndDate;

    private Long nursingLevelId;

    private String nursingLevelName;

    private Long bedId;

    private BigDecimal deposit;

    private BigDecimal nursingFee;

    private BigDecimal bedFee;

    private BigDecimal otherFees;

    private BigDecimal insurancePayment;

    private BigDecimal governmentSubsidy;

    private Long roomId;

    private Long floorId;

    private String floorName;

    private String code;
}

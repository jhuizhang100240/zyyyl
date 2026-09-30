package com.zyyyl.nursing.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 入住签约请求参数。
 */
@Data
public class CheckInContractDto {

    private String contractName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signDate;

    private String thirdPartyName;

    private String thirdPartyPhone;

    private String agreementPath;
}

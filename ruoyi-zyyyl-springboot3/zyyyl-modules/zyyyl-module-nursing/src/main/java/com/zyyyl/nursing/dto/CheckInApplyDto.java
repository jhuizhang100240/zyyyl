package com.zyyyl.nursing.dto;

import java.util.List;

import lombok.Data;

/**
 * 入住申请聚合请求。
 */
@Data
public class CheckInApplyDto {

    private CheckInElderDto checkInElderDto;

    private List<ElderFamilyDto> elderFamilyDtoList;

    private CheckInConfigDto checkInConfigDto;

    private CheckInContractDto checkInContractDto;
}

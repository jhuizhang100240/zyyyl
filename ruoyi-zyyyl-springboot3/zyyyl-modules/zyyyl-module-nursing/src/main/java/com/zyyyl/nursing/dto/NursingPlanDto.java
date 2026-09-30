package com.zyyyl.nursing.dto;

import java.util.List;

import lombok.Data;

/**
 * 护理计划聚合请求。
 */
@Data
public class NursingPlanDto {

    private Long id;

    private Integer sortNo;

    private String planName;

    private Integer status;

    private List<NursingProjectPlanDto> projectPlans;
}

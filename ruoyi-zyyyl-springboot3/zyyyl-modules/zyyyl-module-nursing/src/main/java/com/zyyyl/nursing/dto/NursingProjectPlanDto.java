package com.zyyyl.nursing.dto;

import lombok.Data;

/**
 * 护理计划与项目关联参数。
 */
@Data
public class NursingProjectPlanDto {

    private Long id;

    private Long planId;

    private Long projectId;

    private String executeTime;

    private Long executeCycle;

    private Long executeFrequency;
}

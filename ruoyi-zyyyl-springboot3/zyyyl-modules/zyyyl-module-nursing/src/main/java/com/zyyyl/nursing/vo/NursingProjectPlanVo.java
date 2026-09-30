package com.zyyyl.nursing.vo;

import lombok.Data;

/**
 * 护理计划关联项目视图。
 */
@Data
public class NursingProjectPlanVo {

    private Long id;

    private Long planId;

    private Long projectId;

    private String executeTime;

    private Long executeCycle;

    private Long executeFrequency;
}

package com.zyyyl.nursing.vo;

import java.util.List;

import lombok.Data;

/**
 * 护理计划详情视图。
 */
@Data
public class NursingPlanVo {

    private Long id;

    private Integer sortNo;

    private String planName;

    private Integer status;

    private List<NursingProjectPlanVo> projectPlans;
}

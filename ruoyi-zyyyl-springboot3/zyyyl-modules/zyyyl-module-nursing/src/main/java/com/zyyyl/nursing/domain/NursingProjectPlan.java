package com.zyyyl.nursing.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;
import com.zyyyl.common.core.domain.UserIdAuditEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 护理计划与项目关联对象 nursing_project_plan
 */
@Table("nursing_project_plan")
@Data
@EqualsAndHashCode(callSuper = true)
public class NursingProjectPlan extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "计划id")
    private Long planId;

    @Excel(name = "项目id")
    private Long projectId;

    @Excel(name = "计划执行时间")
    private String executeTime;

    @Excel(name = "执行周期", readConverterExp = "0=天,1=周,2=月")
    private Long executeCycle;

    @Excel(name = "执行频次")
    private Long executeFrequency;
}

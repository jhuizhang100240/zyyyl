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
 * 护理计划对象 nursing_plan
 */
@Table("nursing_plan")
@Data
@EqualsAndHashCode(callSuper = true)
public class NursingPlan extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "排序号")
    private Integer sortNo;

    @Excel(name = "名称")
    private String planName;

    @Excel(name = "状态", readConverterExp = "0=禁用,1=启用")
    private Integer status;
}

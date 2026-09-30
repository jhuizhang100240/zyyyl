package com.zyyyl.nursing.domain;

import java.math.BigDecimal;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;
import com.zyyyl.common.core.domain.UserIdAuditEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 护理等级对象 nursing_level
 */
@Table("nursing_level")
@Data
@EqualsAndHashCode(callSuper = true)
public class NursingLevel extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "等级名称")
    private String name;

    @Excel(name = "护理计划ID")
    private Long planId;

    @Excel(name = "护理费用")
    private BigDecimal fee;

    @Excel(name = "状态", readConverterExp = "0=禁用,1=启用")
    private Integer status;

    @Excel(name = "等级说明")
    private String description;
}

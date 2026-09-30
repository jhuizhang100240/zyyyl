package com.zyyyl.nursing.domain;

import java.math.BigDecimal;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 护理项目对象 nursing_project
 */
@Table("nursing_project")
@Data
@EqualsAndHashCode(callSuper = true)
public class NursingProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "排序号")
    private Integer orderNo;

    @Excel(name = "单位")
    private String unit;

    @Excel(name = "价格")
    private BigDecimal price;

    @Excel(name = "图片")
    private String image;

    @Excel(name = "护理要求")
    private String nursingRequirement;

    @Excel(name = "状态", readConverterExp = "0=禁用,1=启用")
    private Integer status;
}

package com.zyyyl.nursing.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入住配置对象 check_in_config
 */
@Table("check_in_config")
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckInConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "入住表ID")
    private Long checkInId;

    @Excel(name = "护理等级ID")
    private Long nursingLevelId;

    @Excel(name = "护理等级名称")
    private String nursingLevelName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "费用开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime feeStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "费用结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime feeEndDate;

    @Excel(name = "押金")
    private BigDecimal deposit;

    @Excel(name = "护理费用")
    private BigDecimal nursingFee;

    @Excel(name = "床位费用")
    private BigDecimal bedFee;

    @Excel(name = "医保支付")
    private BigDecimal insurancePayment;

    @Excel(name = "政府补贴")
    private BigDecimal governmentSubsidy;

    @Excel(name = "其他费用")
    private BigDecimal otherFees;

    @Excel(name = "排序编号")
    private Integer sortOrder;
}

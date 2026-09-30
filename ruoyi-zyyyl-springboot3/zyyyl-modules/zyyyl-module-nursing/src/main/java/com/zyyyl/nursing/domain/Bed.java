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
 * 床位对象 bed
 */
@Table("bed")
@Data
@EqualsAndHashCode(callSuper = true)
public class Bed extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "床位编号")
    private String bedNumber;

    @Excel(name = "床位状态: 未入住0, 已入住1 入住申请中2")
    private Integer bedStatus;

    @Excel(name = "床位号")
    private Long sort;

    @Excel(name = "房间ID")
    private Long roomId;
}

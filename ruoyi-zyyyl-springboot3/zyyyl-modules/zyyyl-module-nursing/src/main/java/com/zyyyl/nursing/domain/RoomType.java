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
 * 房型对象 room_type
 */
@Table("room_type")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomType extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "房型名称")
    private String name;

    @Excel(name = "床位数量")
    private Long bedCount;

    @Excel(name = "床位费用")
    private BigDecimal price;

    @Excel(name = "介绍")
    private String introduction;

    @Excel(name = "照片")
    private String photo;

    @Excel(name = "状态", readConverterExp = "0=禁用,1=启用")
    private Long status;
}

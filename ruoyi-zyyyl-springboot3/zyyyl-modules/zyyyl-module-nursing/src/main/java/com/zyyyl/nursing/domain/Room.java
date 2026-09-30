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
 * 房间对象 room
 */
@Table("room")
@Data
@EqualsAndHashCode(callSuper = true)
public class Room extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "房间编号")
    private String code;

    @Excel(name = "排序号")
    private Long sort;

    @Excel(name = "房间类型ID")
    private Long roomTypeId;

    @Excel(name = "楼层id")
    private Long floorId;

    @Excel(name = "是否删除")
    private Integer isDeleted;
}

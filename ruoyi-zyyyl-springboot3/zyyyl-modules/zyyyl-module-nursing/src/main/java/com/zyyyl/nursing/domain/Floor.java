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
 * 楼层对象 floor
 */
@Table("floor")
@Data
@EqualsAndHashCode(callSuper = true)
public class Floor extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "编号")
    private Long code;
}

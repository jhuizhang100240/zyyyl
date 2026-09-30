package com.zyyyl.nursing.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入住家属联系人对象 check_in_family
 */
@Table("check_in_family")
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckInFamily extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    private Long checkInId;

    private String familyName;

    private String relation;

    private String phone;

    private Integer isPrimary;
}

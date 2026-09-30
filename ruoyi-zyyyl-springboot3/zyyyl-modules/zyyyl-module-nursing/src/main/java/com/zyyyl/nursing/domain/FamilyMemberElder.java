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
 * 客户老人关联对象 family_member_elder
 */
@Table("family_member_elder")
@Data
@EqualsAndHashCode(callSuper = true)
public class FamilyMemberElder extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "家属id")
    private Long familyMemberId;

    @Excel(name = "老人id")
    private Long elderId;
}

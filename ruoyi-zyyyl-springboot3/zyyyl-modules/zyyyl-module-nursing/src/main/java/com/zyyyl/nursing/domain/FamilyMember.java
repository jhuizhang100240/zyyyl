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
 * 老人家属对象 family_member
 */
@Table("family_member")
@Data
@EqualsAndHashCode(callSuper = true)
public class FamilyMember extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "手机号")
    private String phone;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "头像")
    private String avatar;

    @Excel(name = "OpenID")
    private String openId;

    @Excel(name = "性别")
    private Integer gender;
}

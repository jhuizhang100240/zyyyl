package com.zyyyl.nursing.vo;

import com.zyyyl.nursing.domain.FamilyMemberElder;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 我的家人列表项，保留原 FamilyMemberElder + elderName 结构。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FamilyMemberElderVo extends FamilyMemberElder {

    private static final long serialVersionUID = 1L;

    /** 老人姓名 */
    private String elderName;
}

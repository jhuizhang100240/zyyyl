package com.zyyyl.nursing.mapper;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.FamilyMember;

public interface FamilyMemberMapper extends BaseMapper<FamilyMember> {

    FamilyMember selectByOpenId(@Param("openId") String openId);

    FamilyMember selectByPhone(@Param("phone") String phone);
}

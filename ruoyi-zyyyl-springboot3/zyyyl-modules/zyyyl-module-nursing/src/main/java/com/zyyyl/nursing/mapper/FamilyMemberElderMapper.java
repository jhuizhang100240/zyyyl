package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.FamilyMemberElder;
import com.zyyyl.nursing.vo.FamilyMemberElderVo;
import com.zyyyl.nursing.vo.MemberElderListVo;

public interface FamilyMemberElderMapper extends BaseMapper<FamilyMemberElder> {

    List<FamilyMemberElderVo> selectByMemberId(@Param("familyMemberId") Long familyMemberId);

    List<MemberElderListVo> selectBindingsByPage(@Param("familyMemberId") Long familyMemberId);
}

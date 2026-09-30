package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.FamilyMemberElder;
import com.zyyyl.nursing.dto.MemberElderDto;
import com.zyyyl.nursing.vo.FamilyMemberElderVo;
import com.zyyyl.nursing.vo.MemberElderListVo;

public interface IFamilyMemberElderService extends IService<FamilyMemberElder> {

    /**
     * 绑定老人
     *
     * @param dto            绑定参数
     * @param familyMemberId 当前家属账号
     * @return 影响行数
     */
    int add(MemberElderDto dto, Long familyMemberId);

    /**
     * 我的家人（原接口结构）
     */
    List<FamilyMemberElderVo> my(Long familyMemberId);

    /**
     * 分页查询绑定记录（文档结构）
     */
    List<MemberElderListVo> listByPage(Long familyMemberId);

    /**
     * 解绑，必须校验绑定关系归属
     */
    int deleteById(Long id, Long familyMemberId);

    /**
     * 校验当前家属是否已绑定指定老人
     */
    boolean isBound(Long familyMemberId, Long elderId);
}

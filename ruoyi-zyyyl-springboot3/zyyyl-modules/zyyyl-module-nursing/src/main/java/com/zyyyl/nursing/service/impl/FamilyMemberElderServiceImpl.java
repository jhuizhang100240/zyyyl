package com.zyyyl.nursing.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.domain.FamilyMemberElder;
import com.zyyyl.nursing.dto.MemberElderDto;
import com.zyyyl.nursing.mapper.ElderMapper;
import com.zyyyl.nursing.mapper.FamilyMemberElderMapper;
import com.zyyyl.nursing.service.IFamilyMemberElderService;
import com.zyyyl.nursing.vo.FamilyMemberElderVo;
import com.zyyyl.nursing.vo.MemberElderListVo;

@Service
public class FamilyMemberElderServiceImpl extends ServiceImpl<FamilyMemberElderMapper, FamilyMemberElder>
        implements IFamilyMemberElderService {

    private final ElderMapper elderMapper;

    public FamilyMemberElderServiceImpl(ElderMapper elderMapper) {
        this.elderMapper = elderMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int add(MemberElderDto dto, Long familyMemberId) {
        if (dto == null) {
            throw new ServiceException("绑定参数不能为空");
        }
        Elder elder = resolveElder(dto);
        if (elder == null) {
            throw new ServiceException("该老人未入住，请检查输入信息");
        }
        if (isBound(familyMemberId, elder.getId())) {
            throw new ServiceException("该老人已绑定，请勿重复绑定");
        }
        FamilyMemberElder binding = new FamilyMemberElder();
        binding.setFamilyMemberId(familyMemberId);
        binding.setElderId(elder.getId());
        binding.setRemark(dto.getRemark());
        try {
            return save(binding) ? 1 : 0;
        } catch (DuplicateKeyException e) {
            throw new ServiceException("该老人已绑定，请勿重复绑定");
        }
    }

    @Override
    public List<FamilyMemberElderVo> my(Long familyMemberId) {
        List<FamilyMemberElderVo> list = mapper.selectByMemberId(familyMemberId);
        return list == null ? Collections.emptyList() : list;
    }

    @Override
    public List<MemberElderListVo> listByPage(Long familyMemberId) {
        List<MemberElderListVo> list = mapper.selectBindingsByPage(familyMemberId);
        return list == null ? Collections.emptyList() : list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(Long id, Long familyMemberId) {
        if (id == null) {
            throw new ServiceException("绑定记录ID不能为空");
        }
        QueryWrapper wrapper = QueryWrapper.create()
                .where(FamilyMemberElder::getId).eq(id)
                .and(FamilyMemberElder::getFamilyMemberId).eq(familyMemberId);
        long count = mapper.selectCountByQuery(wrapper);
        if (count <= 0) {
            throw new ServiceException("绑定记录不存在或无权操作");
        }
        return removeById(id) ? 1 : 0;
    }

    @Override
    public boolean isBound(Long familyMemberId, Long elderId) {
        if (familyMemberId == null || elderId == null) {
            return false;
        }
        QueryWrapper wrapper = QueryWrapper.create()
                .where(FamilyMemberElder::getFamilyMemberId).eq(familyMemberId)
                .and(FamilyMemberElder::getElderId).eq(elderId);
        return mapper.selectCountByQuery(wrapper) > 0;
    }

    private Elder resolveElder(MemberElderDto dto) {
        if (dto.getElderId() != null) {
            return elderMapper.selectOneById(dto.getElderId());
        }
        if (StringUtils.isNotEmpty(dto.getIdCard())) {
            QueryWrapper wrapper = QueryWrapper.create()
                    .where(Elder::getIdCardNo).eq(dto.getIdCard());
            return elderMapper.selectOneByQuery(wrapper);
        }
        throw new ServiceException("请提供老人ID或身份证号");
    }
}

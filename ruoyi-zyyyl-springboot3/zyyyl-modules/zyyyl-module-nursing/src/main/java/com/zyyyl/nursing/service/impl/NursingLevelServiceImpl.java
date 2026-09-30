package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.nursing.domain.NursingLevel;
import com.zyyyl.nursing.mapper.NursingLevelMapper;
import com.zyyyl.nursing.service.INursingLevelService;
import com.zyyyl.nursing.vo.NursingLevelVo;

@Service
public class NursingLevelServiceImpl extends ServiceImpl<NursingLevelMapper, NursingLevel>
        implements INursingLevelService {

    @Override
    public NursingLevel selectNursingLevelById(Long id) {
        return getById(id);
    }

    @Override
    public List<NursingLevelVo> selectNursingLevelList(NursingLevel nursingLevel) {
        return mapper.selectNursingLevelList(nursingLevel);
    }

    @Override
    public int insertNursingLevel(NursingLevel nursingLevel) {
        validate(nursingLevel);
        if (nursingLevel.getStatus() == null) {
            nursingLevel.setStatus(1);
        }
        return save(nursingLevel) ? 1 : 0;
    }

    @Override
    public int updateNursingLevel(NursingLevel nursingLevel) {
        validate(nursingLevel);
        return updateById(nursingLevel) ? 1 : 0;
    }

    @Override
    public int deleteNursingLevelByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteNursingLevelById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    @Override
    public List<NursingLevel> listAll() {
        return mapper.listAll();
    }

    private void validate(NursingLevel nursingLevel) {
        if (nursingLevel == null || nursingLevel.getName() == null || nursingLevel.getPlanId() == null
                || nursingLevel.getFee() == null) {
            throw new ServiceException("护理等级名称、护理计划和护理费用不能为空");
        }
    }
}

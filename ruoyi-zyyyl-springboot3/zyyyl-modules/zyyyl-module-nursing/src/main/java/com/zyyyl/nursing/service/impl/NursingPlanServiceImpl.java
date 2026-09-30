package com.zyyyl.nursing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.domain.NursingPlan;
import com.zyyyl.nursing.dto.NursingPlanDto;
import com.zyyyl.nursing.dto.NursingProjectPlanDto;
import com.zyyyl.nursing.mapper.NursingPlanMapper;
import com.zyyyl.nursing.mapper.NursingProjectPlanMapper;
import com.zyyyl.nursing.service.INursingPlanService;
import com.zyyyl.nursing.vo.NursingPlanVo;
import com.zyyyl.nursing.vo.NursingProjectPlanVo;

@Service
public class NursingPlanServiceImpl extends ServiceImpl<NursingPlanMapper, NursingPlan>
        implements INursingPlanService {

    @Autowired
    private NursingProjectPlanMapper nursingProjectPlanMapper;

    @Override
    public NursingPlanVo selectNursingPlanById(Long id) {
        NursingPlan plan = getById(id);
        if (plan == null) {
            return null;
        }
        NursingPlanVo vo = new NursingPlanVo();
        vo.setId(plan.getId());
        vo.setSortNo(plan.getSortNo());
        vo.setPlanName(plan.getPlanName());
        vo.setStatus(plan.getStatus());
        List<NursingProjectPlanVo> projectPlans = nursingProjectPlanMapper.selectByPlanId(id);
        vo.setProjectPlans(projectPlans);
        return vo;
    }

    @Override
    public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan) {
        return mapper.selectNursingPlanList(nursingPlan);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertNursingPlan(NursingPlanDto dto) {
        validatePlan(dto);
        NursingPlan plan = new NursingPlan();
        plan.setSortNo(dto.getSortNo() == null ? 0 : dto.getSortNo());
        plan.setPlanName(dto.getPlanName());
        plan.setStatus(dto.getStatus() == null ? 0 : dto.getStatus());
        save(plan);
        insertProjectPlans(dto.getProjectPlans(), plan.getId());
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateNursingPlan(NursingPlanDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new ServiceException("护理计划主键不能为空");
        }
        NursingPlan plan = getById(dto.getId());
        if (plan == null) {
            throw new ServiceException("护理计划不存在");
        }
        if (StringUtils.isNotEmpty(dto.getPlanName())) {
            plan.setPlanName(dto.getPlanName());
        }
        if (dto.getSortNo() != null) {
            plan.setSortNo(dto.getSortNo());
        }
        if (dto.getStatus() != null) {
            plan.setStatus(dto.getStatus());
        }
        updateById(plan);

        if (dto.getProjectPlans() != null && !dto.getProjectPlans().isEmpty()) {
            nursingProjectPlanMapper.deleteByPlanId(plan.getId());
            insertProjectPlans(dto.getProjectPlans(), plan.getId());
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteNursingPlanByIds(Long[] ids) {
        for (Long id : ids) {
            nursingProjectPlanMapper.deleteByPlanId(id);
        }
        return removeByIds(java.util.Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteNursingPlanById(Long id) {
        return deleteNursingPlanByIds(new Long[] { id });
    }

    @Override
    public List<NursingPlan> listAll() {
        return mapper.listAll();
    }

    private void validatePlan(NursingPlanDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.getPlanName())) {
            throw new ServiceException("护理计划名称不能为空");
        }
    }

    private void insertProjectPlans(List<NursingProjectPlanDto> projectPlans, Long planId) {
        if (projectPlans == null || projectPlans.isEmpty()) {
            return;
        }
        for (NursingProjectPlanDto item : projectPlans) {
            if (item.getProjectId() == null || StringUtils.isEmpty(item.getExecuteTime())
                    || item.getExecuteCycle() == null || item.getExecuteFrequency() == null) {
                throw new ServiceException("护理计划项目、执行时间、执行周期和执行频次不能为空");
            }
        }
        nursingProjectPlanMapper.batchInsert(projectPlans, planId);
    }
}

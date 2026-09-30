package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.NursingPlan;
import com.zyyyl.nursing.dto.NursingPlanDto;
import com.zyyyl.nursing.vo.NursingPlanVo;

public interface INursingPlanService extends IService<NursingPlan> {

    NursingPlanVo selectNursingPlanById(Long id);

    List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan);

    int insertNursingPlan(NursingPlanDto dto);

    int updateNursingPlan(NursingPlanDto dto);

    int deleteNursingPlanByIds(Long[] ids);

    int deleteNursingPlanById(Long id);

    List<NursingPlan> listAll();
}

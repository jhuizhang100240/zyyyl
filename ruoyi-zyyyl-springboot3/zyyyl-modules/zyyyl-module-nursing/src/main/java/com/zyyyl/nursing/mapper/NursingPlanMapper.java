package com.zyyyl.nursing.mapper;

import java.util.List;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.NursingPlan;

public interface NursingPlanMapper extends BaseMapper<NursingPlan> {

    List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan);

    List<NursingPlan> listAll();
}

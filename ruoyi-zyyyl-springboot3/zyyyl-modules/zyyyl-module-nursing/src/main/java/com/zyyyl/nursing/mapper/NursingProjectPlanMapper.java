package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.NursingProjectPlan;
import com.zyyyl.nursing.dto.NursingProjectPlanDto;
import com.zyyyl.nursing.vo.NursingProjectPlanVo;

public interface NursingProjectPlanMapper extends BaseMapper<NursingProjectPlan> {

    List<NursingProjectPlanVo> selectByPlanId(@Param("planId") Long planId);

    int batchInsert(@Param("list") List<NursingProjectPlanDto> projectPlans,
            @Param("planId") Long planId);

    int deleteByPlanId(@Param("planId") Long planId);
}

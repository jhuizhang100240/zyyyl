package com.zyyyl.nursing.mapper;

import java.util.List;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.HealthAssessment;

public interface HealthAssessmentMapper extends BaseMapper<HealthAssessment> {

    List<HealthAssessment> selectHealthAssessmentList(HealthAssessment healthAssessment);
}

package com.zyyyl.nursing.mapper;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.HealthAssessmentDetail;

public interface HealthAssessmentDetailMapper extends BaseMapper<HealthAssessmentDetail> {

    HealthAssessmentDetail selectByAssessmentId(@Param("healthAssessmentId") Long healthAssessmentId);

    int deleteByAssessmentIds(@Param("ids") Long[] ids);
}

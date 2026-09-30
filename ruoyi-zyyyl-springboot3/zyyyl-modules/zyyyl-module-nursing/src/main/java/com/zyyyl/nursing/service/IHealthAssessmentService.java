package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.HealthAssessment;
import com.zyyyl.nursing.dto.HealthAssessmentDto;
import com.zyyyl.nursing.vo.HealthAssessmentVo;

public interface IHealthAssessmentService extends IService<HealthAssessment> {

    HealthAssessmentVo selectHealthAssessmentById(Long id);

    List<HealthAssessment> selectHealthAssessmentList(HealthAssessment healthAssessment);

    Long insertHealthAssessment(HealthAssessmentDto dto);

    int updateHealthAssessment(HealthAssessment healthAssessment);

    int deleteHealthAssessmentByIds(Long[] ids);

    int deleteHealthAssessmentById(Long id);
}

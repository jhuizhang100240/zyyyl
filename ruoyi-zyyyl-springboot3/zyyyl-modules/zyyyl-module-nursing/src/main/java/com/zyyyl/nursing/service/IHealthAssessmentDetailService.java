package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.HealthAssessmentDetail;

public interface IHealthAssessmentDetailService extends IService<HealthAssessmentDetail> {

    HealthAssessmentDetail selectHealthAssessmentDetailById(Long id);

    List<HealthAssessmentDetail> selectHealthAssessmentDetailList(HealthAssessmentDetail detail);

    int insertHealthAssessmentDetail(HealthAssessmentDetail detail);

    int updateHealthAssessmentDetail(HealthAssessmentDetail detail);

    int deleteHealthAssessmentDetailByIds(Long[] ids);

    int deleteHealthAssessmentDetailById(Long id);
}

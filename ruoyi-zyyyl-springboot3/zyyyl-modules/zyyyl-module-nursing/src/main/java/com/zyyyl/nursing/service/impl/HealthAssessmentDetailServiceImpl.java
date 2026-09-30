package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.nursing.domain.HealthAssessmentDetail;
import com.zyyyl.nursing.mapper.HealthAssessmentDetailMapper;
import com.zyyyl.nursing.service.IHealthAssessmentDetailService;

@Service
public class HealthAssessmentDetailServiceImpl
        extends ServiceImpl<HealthAssessmentDetailMapper, HealthAssessmentDetail>
        implements IHealthAssessmentDetailService {

    @Override
    public HealthAssessmentDetail selectHealthAssessmentDetailById(Long id) {
        return getById(id);
    }

    @Override
    public List<HealthAssessmentDetail> selectHealthAssessmentDetailList(HealthAssessmentDetail detail) {
        return queryChain()
                .eq(HealthAssessmentDetail::getHealthAssessmentId, detail.getHealthAssessmentId())
                .eq(HealthAssessmentDetail::getRiskLevel, detail.getRiskLevel())
                .list();
    }

    @Override
    public int insertHealthAssessmentDetail(HealthAssessmentDetail detail) {
        return save(detail) ? 1 : 0;
    }

    @Override
    public int updateHealthAssessmentDetail(HealthAssessmentDetail detail) {
        return updateById(detail) ? 1 : 0;
    }

    @Override
    public int deleteHealthAssessmentDetailByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteHealthAssessmentDetailById(Long id) {
        return removeById(id) ? 1 : 0;
    }
}

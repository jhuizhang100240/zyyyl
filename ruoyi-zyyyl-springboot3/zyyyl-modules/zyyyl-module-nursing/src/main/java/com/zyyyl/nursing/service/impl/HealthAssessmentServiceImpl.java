package com.zyyyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.IdCardUtils;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.domain.HealthAssessment;
import com.zyyyl.nursing.domain.HealthAssessmentDetail;
import com.zyyyl.nursing.dto.HealthAssessmentDto;
import com.zyyyl.nursing.mapper.HealthAssessmentDetailMapper;
import com.zyyyl.nursing.mapper.HealthAssessmentMapper;
import com.zyyyl.nursing.service.IHealthAssessmentService;
import com.zyyyl.nursing.vo.HealthAssessmentVo;

@Service
public class HealthAssessmentServiceImpl extends ServiceImpl<HealthAssessmentMapper, HealthAssessment>
        implements IHealthAssessmentService {

    @Autowired
    private HealthAssessmentDetailMapper healthAssessmentDetailMapper;

    @Override
    public HealthAssessmentVo selectHealthAssessmentById(Long id) {
        HealthAssessment assessment = getById(id);
        if (assessment == null) {
            return null;
        }
        HealthAssessmentVo vo = new HealthAssessmentVo();
        HealthAssessmentDetail detail = healthAssessmentDetailMapper.selectByAssessmentId(id);
        if (detail != null) {
            BeanUtils.copyProperties(detail, vo);
        }
        BeanUtils.copyProperties(assessment, vo);
        vo.setId(assessment.getId());
        return vo;
    }

    @Override
    public List<HealthAssessment> selectHealthAssessmentList(HealthAssessment healthAssessment) {
        return mapper.selectHealthAssessmentList(healthAssessment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long insertHealthAssessment(HealthAssessmentDto dto) {
        validate(dto);

        HealthAssessment assessment = new HealthAssessment();
        assessment.setElderName(dto.getElderName());
        assessment.setIdCard(dto.getIdCard());
        assessment.setHealthScore(dto.getHealthScore());
        assessment.setSuggestionForAdmission(dto.getSuggestionForAdmission());
        assessment.setNursingLevelName(dto.getNursingLevelName());
        assessment.setAdmissionStatus(1);
        assessment.setTotalCheckDate(dto.getTotalCheckDate());
        assessment.setAssessmentTime(LocalDateTime.now());
        save(assessment);

        HealthAssessmentDetail detail = new HealthAssessmentDetail();
        detail.setHealthAssessmentId(assessment.getId());
        detail.setBirthDate(IdCardUtils.getBirthDate(dto.getIdCard()));
        detail.setAge(IdCardUtils.getAge(dto.getIdCard()));
        detail.setGender(IdCardUtils.getGender(dto.getIdCard()));
        detail.setRiskLevel(dto.getRiskLevel());
        detail.setPhysicalExamInstitution(dto.getPhysicalExamInstitution());
        detail.setPhysicalReportUrl(dto.getPhysicalReportUrl());
        detail.setReportSummary(dto.getReportSummary());
        detail.setAbnormalAnalysis(dto.getAbnormalAnalysis());
        detail.setSystemScore(dto.getSystemScore());
        healthAssessmentDetailMapper.insert(detail);
        return assessment.getId();
    }

    @Override
    public int updateHealthAssessment(HealthAssessment healthAssessment) {
        return updateById(healthAssessment) ? 1 : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteHealthAssessmentByIds(Long[] ids) {
        healthAssessmentDetailMapper.deleteByAssessmentIds(ids);
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteHealthAssessmentById(Long id) {
        return deleteHealthAssessmentByIds(new Long[] { id });
    }

    private void validate(HealthAssessmentDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.getElderName()) || StringUtils.isEmpty(dto.getIdCard())) {
            throw new ServiceException("老人姓名和身份证号不能为空");
        }
        try {
            IdCardUtils.getBirthDate(dto.getIdCard());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("身份证号格式错误");
        }
    }
}

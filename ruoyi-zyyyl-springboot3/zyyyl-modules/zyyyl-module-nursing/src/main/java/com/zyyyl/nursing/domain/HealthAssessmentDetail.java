package com.zyyyl.nursing.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 健康评估详情对象 health_assessment_detail
 */
@Table("health_assessment_detail")
@Data
@EqualsAndHashCode(callSuper = true)
public class HealthAssessmentDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "健康评估id")
    private Long healthAssessmentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime birthDate;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    private Integer gender;

    @Excel(name = "风险等级")
    private String riskLevel;

    @Excel(name = "体检机构")
    private String physicalExamInstitution;

    @Excel(name = "体检报告URL链接")
    private String physicalReportUrl;

    @Excel(name = "报告总结")
    private String reportSummary;

    @Excel(name = "异常分析")
    private String abnormalAnalysis;

    @Excel(name = "健康系统分值")
    private String systemScore;
}

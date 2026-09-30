package com.zyyyl.nursing.dto;

import lombok.Data;

/**
 * 健康评估新增参数。
 */
@Data
public class HealthAssessmentDto {

    private String elderName;

    private String idCard;

    private String physicalExamInstitution;

    private String physicalReportUrl;

    /**
     * 以下字段允许报告分析完成时由受控流程填入；为空时保持空值，不自行推算编码。
     */
    private String healthScore;

    private Integer suggestionForAdmission;

    private String nursingLevelName;

    private String totalCheckDate;

    private String riskLevel;

    private String reportSummary;

    private String abnormalAnalysis;

    private String systemScore;
}

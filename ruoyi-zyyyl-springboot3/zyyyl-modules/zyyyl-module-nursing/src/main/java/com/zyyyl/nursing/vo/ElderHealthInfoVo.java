package com.zyyyl.nursing.vo;

import lombok.Data;

/**
 * 老人健康信息模型，供 Dify 智能体查询使用。
 */
@Data
public class ElderHealthInfoVo {

    private String elderName;

    private String healthScore;

    private String riskLevel;

    private String reportSummary;

    private String totalCheckDate;

    private String assessmentTime;
}

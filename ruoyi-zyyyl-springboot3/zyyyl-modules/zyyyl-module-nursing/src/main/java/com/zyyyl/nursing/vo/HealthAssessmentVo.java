package com.zyyyl.nursing.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyyyl.nursing.domain.HealthAssessmentDetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 健康评估主表与详情合并响应。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HealthAssessmentVo extends HealthAssessmentDetail {

    private Long id;

    private String elderName;

    private String idCard;

    private String healthScore;

    private Integer suggestionForAdmission;

    private String nursingLevelName;

    private Integer admissionStatus;

    private String totalCheckDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assessmentTime;
}

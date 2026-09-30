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
 * 健康评估对象 health_assessment
 */
@Table("health_assessment")
@Data
@EqualsAndHashCode(callSuper = true)
public class HealthAssessment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "老人姓名")
    private String elderName;

    @Excel(name = "身份证号")
    private String idCard;

    @Excel(name = "健康评分")
    private String healthScore;

    @Excel(name = "是否建议入住", readConverterExp = "0=建议,1=不建议")
    private Integer suggestionForAdmission;

    @Excel(name = "推荐护理等级")
    private String nursingLevelName;

    @Excel(name = "入住情况", readConverterExp = "0=已入住,1=未入住")
    private Integer admissionStatus;

    @Excel(name = "总检日期")
    private String totalCheckDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "评估时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime assessmentTime;
}

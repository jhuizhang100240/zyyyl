package com.zyyyl.nursing.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;
import com.zyyyl.common.core.domain.UserIdAuditEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 护理任务对象 nursing_task
 */
@Table("nursing_task")
@Data
@EqualsAndHashCode(callSuper = true)
public class NursingTask extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "护理员id")
    private String nursingId;

    @Excel(name = "项目id")
    private Long projectId;

    @Excel(name = "护理项目名称")
    private String projectName;

    @Excel(name = "老人id")
    private Long elderId;

    @Excel(name = "老人姓名")
    private String elderName;

    @Excel(name = "床位编号")
    private String bedNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预计服务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime estimatedServerTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际服务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime realServerTime;

    @Excel(name = "执行记录")
    private String mark;

    @Excel(name = "取消原因")
    private String cancelReason;

    @Excel(name = "状态", readConverterExp = "1=待执行,2=已执行,3=已关闭")
    private Integer status;

    @Excel(name = "执行图片")
    private String taskImage;

    @Column(ignore = true)
    private List<String> nursingName;
}

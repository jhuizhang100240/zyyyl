package com.zyyyl.nursing.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;
import com.zyyyl.common.core.domain.UserIdAuditEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 预约信息对象 reservation
 */
@Table("reservation")
@Data
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "预约人姓名")
    private String name;

    @Excel(name = "预约人手机号")
    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime time;

    @Excel(name = "探访人")
    private String visitor;

    @Excel(name = "预约类型", readConverterExp = "0=参观预约,1=探访预约")
    private Integer type;

    @Excel(name = "预约状态", readConverterExp = "0=待报道,1=已完成,2=取消,3=过期")
    private Integer status;
}

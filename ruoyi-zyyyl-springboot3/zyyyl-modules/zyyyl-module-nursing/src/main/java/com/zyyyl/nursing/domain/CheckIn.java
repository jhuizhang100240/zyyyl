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
 * 入住对象 check_in
 */
@Table("check_in")
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckIn extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "老人姓名")
    private String elderName;

    @Excel(name = "老人ID")
    private Long elderId;

    @Excel(name = "身份证号")
    private String idCardNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "入住开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "入住结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime endDate;

    @Excel(name = "护理等级名称")
    private String nursingLevelName;

    @Excel(name = "入住床位")
    private String bedNumber;

    @Excel(name = "状态", readConverterExp = "0=已入住,1=已退住")
    private Integer status;

    @Excel(name = "排序编号")
    private Integer sortOrder;
}

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
 * 合同对象 contract
 */
@Table("contract")
@Data
@EqualsAndHashCode(callSuper = true)
public class Contract extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "老人ID")
    private Long elderId;

    @Excel(name = "合同名称")
    private String contractName;

    @Excel(name = "合同编号")
    private String contractNumber;

    @Excel(name = "协议地址")
    private String agreementPath;

    @Excel(name = "丙方手机号")
    private String thirdPartyPhone;

    @Excel(name = "丙方姓名")
    private String thirdPartyName;

    @Excel(name = "老人姓名")
    private String elderName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime endDate;

    @Excel(name = "状态", readConverterExp = "0=未生效,1=已生效,2=已过期,3=已失效")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime signDate;

    @Excel(name = "解除提交人")
    private String terminationSubmitter;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "解除日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime terminationDate;

    @Excel(name = "解除协议地址")
    private String terminationAgreementPath;

    @Excel(name = "排序编号")
    private Integer sortOrder;
}

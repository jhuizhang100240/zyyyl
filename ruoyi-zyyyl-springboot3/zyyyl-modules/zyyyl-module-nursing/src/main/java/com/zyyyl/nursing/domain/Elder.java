package com.zyyyl.nursing.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zyyyl.common.annotation.Excel;
import com.zyyyl.common.core.domain.BaseEntity;
import com.zyyyl.common.core.domain.UserIdAuditEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 老人对象 elder
 */
@Table("elder")
@Data
@EqualsAndHashCode(callSuper = true)
public class Elder extends BaseEntity implements UserIdAuditEntity {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto, before = false)
    private Long id;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "图片")
    private String image;

    @Excel(name = "身份证号")
    private String idCardNo;

    @Excel(name = "性别", readConverterExp = "0=女,1=男")
    private Integer sex;

    @Excel(name = "状态", readConverterExp = "0=禁用,1=启用,2=请假,3=退住中,4=入住中,5=已退住")
    private Integer status;

    @Excel(name = "手机号")
    private String phone;

    @Excel(name = "出生日期")
    private String birthday;

    @Excel(name = "家庭住址")
    private String address;

    @Excel(name = "身份证国徽面")
    private String idCardNationalEmblemImg;

    @Excel(name = "身份证人像面")
    private String idCardPortraitImg;

    @Excel(name = "床位编号")
    private String bedNumber;

    @Excel(name = "床位id")
    private Long bedId;
}

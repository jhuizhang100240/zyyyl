package com.zyyyl.nursing.vo;

import lombok.Data;

/**
 * 老人基本信息模型，供 Dify 智能体查询使用。
 */
@Data
public class ElderBasicInfoVo {

    private Long elderId;

    private String name;

    private Integer age;

    /** 男 / 女 */
    private String sex;

    private String birthday;

    private String idCardNo;

    private String phone;

    private String status;

    private String checkInTime;

    private String nursingLevel;

    private String roomNumber;

    private String bedNumber;
}

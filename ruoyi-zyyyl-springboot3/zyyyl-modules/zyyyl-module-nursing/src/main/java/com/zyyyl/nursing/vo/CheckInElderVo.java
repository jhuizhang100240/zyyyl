package com.zyyyl.nursing.vo;

import lombok.Data;

/**
 * 入住详情中的老人信息。
 */
@Data
public class CheckInElderVo {

    private Long id;

    private String name;

    private String idCardNo;

    private String birthday;

    private Integer sex;

    private String phone;

    private String address;

    private String image;

    private String idCardNationalEmblemImg;

    private String idCardPortraitImg;

    private Integer age;
}

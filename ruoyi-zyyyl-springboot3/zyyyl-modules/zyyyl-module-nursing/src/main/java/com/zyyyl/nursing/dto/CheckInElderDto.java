package com.zyyyl.nursing.dto;

import lombok.Data;

/**
 * 入住申请中的老人信息。
 */
@Data
public class CheckInElderDto {

    private String name;

    private String idCardNo;

    private String birthday;

    private Integer age;

    private Integer sex;

    private String phone;

    private String address;

    private String image;

    private String idCardNationalEmblemImg;

    private String idCardPortraitImg;
}

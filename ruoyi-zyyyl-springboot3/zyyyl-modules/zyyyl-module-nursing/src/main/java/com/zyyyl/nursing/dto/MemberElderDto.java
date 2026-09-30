package com.zyyyl.nursing.dto;

import lombok.Data;

/**
 * 家属绑定老人请求。
 * 同时兼容原身份证绑定方式与文档中的 elderId 绑定方式，二者至少提供一个。
 */
@Data
public class MemberElderDto {

    /** 老人ID，文档方式 */
    private Long elderId;

    /** 老人姓名，原身份证绑定方式 */
    private String name;

    /** 老人身份证号，原身份证绑定方式 */
    private String idCard;

    private String remark;
}

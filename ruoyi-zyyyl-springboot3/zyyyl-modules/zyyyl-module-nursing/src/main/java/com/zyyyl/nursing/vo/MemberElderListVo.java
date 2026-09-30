package com.zyyyl.nursing.vo;

import lombok.Data;

/**
 * 家属端「我的家人」列表项，字段与文档示例保持一致。
 */
@Data
public class MemberElderListVo {

    /** 绑定记录ID */
    private Long id;

    private Long elderId;

    private String elderName;

    /** 0=女，1=男 */
    private Integer sex;

    private Integer age;

    private String bedNumber;

    private Integer status;

    private String nursingLevelName;
}

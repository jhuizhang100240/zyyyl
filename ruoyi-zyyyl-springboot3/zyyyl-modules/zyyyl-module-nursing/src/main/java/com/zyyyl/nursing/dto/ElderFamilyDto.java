package com.zyyyl.nursing.dto;

import lombok.Data;

/**
 * 入住申请中的家属信息。
 */
@Data
public class ElderFamilyDto {

    private String name;

    private String phone;

    private String kinship;
}

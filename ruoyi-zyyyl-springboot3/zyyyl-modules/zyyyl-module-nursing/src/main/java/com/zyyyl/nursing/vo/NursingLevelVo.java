package com.zyyyl.nursing.vo;

import com.zyyyl.nursing.domain.NursingLevel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 护理等级视图。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NursingLevelVo extends NursingLevel {

    private String planName;
}

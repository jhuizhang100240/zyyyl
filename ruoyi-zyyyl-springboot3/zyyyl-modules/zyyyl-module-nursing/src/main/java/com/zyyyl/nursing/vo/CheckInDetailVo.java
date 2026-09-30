package com.zyyyl.nursing.vo;

import java.util.List;

import com.zyyyl.nursing.domain.Contract;

import lombok.Data;

/**
 * 入住详情响应模型。
 */
@Data
public class CheckInDetailVo {

    private CheckInElderVo checkInElderVo;

    private List<ElderFamilyVo> elderFamilyVoList;

    private CheckInConfigVo checkInConfigVo;

    private Contract contract;
}

package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.CheckIn;
import com.zyyyl.nursing.dto.CheckInApplyDto;
import com.zyyyl.nursing.vo.CheckInDetailVo;

public interface ICheckInService extends IService<CheckIn> {

    CheckIn selectCheckInById(Long id);

    List<CheckIn> selectCheckInList(CheckIn checkIn);

    int insertCheckIn(CheckIn checkIn);

    int updateCheckIn(CheckIn checkIn);

    int deleteCheckInByIds(Long[] ids);

    int deleteCheckInById(Long id);

    void apply(CheckInApplyDto dto);

    CheckInDetailVo detail(Long id);
}

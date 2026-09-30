package com.zyyyl.nursing.mapper;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.CheckInConfig;

public interface CheckInConfigMapper extends BaseMapper<CheckInConfig> {

    CheckInConfig selectByCheckInId(@Param("checkInId") Long checkInId);
}

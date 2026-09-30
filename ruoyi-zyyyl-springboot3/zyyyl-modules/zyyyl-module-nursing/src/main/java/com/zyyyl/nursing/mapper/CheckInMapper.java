package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.CheckIn;

public interface CheckInMapper extends BaseMapper<CheckIn> {

    List<CheckIn> selectCheckInList(CheckIn checkIn);

    int updateHealthAssessmentStatus(@Param("idCardNo") String idCardNo);
}

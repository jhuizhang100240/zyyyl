package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.Bed;

public interface BedMapper extends BaseMapper<Bed> {

    List<Bed> selectBedList(Bed bed);

    Bed selectBedForUpdate(@Param("id") Long id);
}

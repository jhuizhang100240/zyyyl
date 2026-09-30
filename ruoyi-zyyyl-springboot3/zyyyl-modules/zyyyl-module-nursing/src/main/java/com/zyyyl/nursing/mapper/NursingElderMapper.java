package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.NursingElder;

public interface NursingElderMapper extends BaseMapper<NursingElder> {

    List<Long> selectNursingIdsByElderId(@Param("elderId") Long elderId);
}

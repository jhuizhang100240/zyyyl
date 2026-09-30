package com.zyyyl.nursing.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.NursingTask;
import com.zyyyl.nursing.dto.NursingTaskDto;

public interface NursingTaskMapper extends BaseMapper<NursingTask> {

    List<NursingTask> selectByPage(NursingTaskDto dto);

    int countByUniqueKey(@Param("elderId") Long elderId, @Param("projectId") Long projectId,
            @Param("estimatedServerTime") LocalDateTime estimatedServerTime);
}

package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.NursingTaskAssignee;

public interface NursingTaskAssigneeMapper extends BaseMapper<NursingTaskAssignee> {

    int batchInsert(@Param("taskId") Long taskId, @Param("nursingIds") List<Long> nursingIds);

    List<Long> selectNursingIdsByTaskId(@Param("taskId") Long taskId);

    int deleteByTaskIds(@Param("taskIds") List<Long> taskIds);
}

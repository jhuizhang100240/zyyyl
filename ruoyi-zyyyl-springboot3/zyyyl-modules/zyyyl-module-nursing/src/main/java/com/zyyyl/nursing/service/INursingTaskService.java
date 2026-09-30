package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.domain.NursingTask;
import com.zyyyl.nursing.dto.NursingTaskDto;
import com.zyyyl.nursing.dto.TaskDto;
import com.zyyyl.nursing.vo.NursingTaskVo;

public interface INursingTaskService extends IService<NursingTask> {

    NursingTaskVo selectNursingTaskById(Long id);

    TableDataInfo selectNursingTaskList(NursingTaskDto dto);

    int insertNursingTask(NursingTask nursingTask);

    int updateNursingTask(NursingTask nursingTask);

    int deleteNursingTaskByIds(Long[] ids);

    void createMonthTask(Elder elder);

    int cancelTask(TaskDto dto);

    int rescheduleTask(TaskDto dto);

    int executeTask(TaskDto dto);
}

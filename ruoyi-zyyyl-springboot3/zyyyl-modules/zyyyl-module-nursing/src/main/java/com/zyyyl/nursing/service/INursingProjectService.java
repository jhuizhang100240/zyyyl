package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.NursingProject;
import com.zyyyl.nursing.vo.NursingProjectVo;

public interface INursingProjectService extends IService<NursingProject> {

    NursingProject selectNursingProjectById(Long id);

    List<NursingProject> selectNursingProjectList(NursingProject nursingProject);

    int insertNursingProject(NursingProject nursingProject);

    int updateNursingProject(NursingProject nursingProject);

    int deleteNursingProjectByIds(Long[] ids);

    int deleteNursingProjectById(Long id);

    List<NursingProjectVo> listAll();
}

package com.zyyyl.nursing.mapper;

import java.util.List;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.NursingProject;
import com.zyyyl.nursing.vo.NursingProjectVo;

public interface NursingProjectMapper extends BaseMapper<NursingProject> {

    List<NursingProject> selectNursingProjectList(NursingProject nursingProject);

    List<NursingProjectVo> listAll();
}

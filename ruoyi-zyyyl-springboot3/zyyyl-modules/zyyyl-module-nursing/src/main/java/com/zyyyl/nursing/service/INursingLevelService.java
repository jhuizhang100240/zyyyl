package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.NursingLevel;
import com.zyyyl.nursing.vo.NursingLevelVo;

public interface INursingLevelService extends IService<NursingLevel> {

    NursingLevel selectNursingLevelById(Long id);

    List<NursingLevelVo> selectNursingLevelList(NursingLevel nursingLevel);

    int insertNursingLevel(NursingLevel nursingLevel);

    int updateNursingLevel(NursingLevel nursingLevel);

    int deleteNursingLevelByIds(Long[] ids);

    int deleteNursingLevelById(Long id);

    List<NursingLevel> listAll();
}

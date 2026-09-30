package com.zyyyl.nursing.mapper;

import java.util.List;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.NursingLevel;
import com.zyyyl.nursing.vo.NursingLevelVo;

public interface NursingLevelMapper extends BaseMapper<NursingLevel> {

    List<NursingLevelVo> selectNursingLevelList(NursingLevel nursingLevel);

    List<NursingLevel> listAll();
}

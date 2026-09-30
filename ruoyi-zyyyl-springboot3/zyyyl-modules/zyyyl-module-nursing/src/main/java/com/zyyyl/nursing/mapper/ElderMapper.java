package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.Elder;

public interface ElderMapper extends BaseMapper<Elder> {

    List<Elder> selectElderList(Elder elder);

    Elder selectElderByIdCardAndStatusForUpdate(@Param("idCardNo") String idCardNo,
            @Param("status") Integer status);
}

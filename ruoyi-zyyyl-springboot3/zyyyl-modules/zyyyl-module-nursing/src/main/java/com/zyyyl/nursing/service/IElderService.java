package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.Elder;

public interface IElderService extends IService<Elder> {

    Elder selectElderById(Long id);

    List<Elder> selectElderList(Elder elder);

    int insertElder(Elder elder);

    int updateElder(Elder elder);

    int deleteElderByIds(Long[] ids);

    int deleteElderById(Long id);
}

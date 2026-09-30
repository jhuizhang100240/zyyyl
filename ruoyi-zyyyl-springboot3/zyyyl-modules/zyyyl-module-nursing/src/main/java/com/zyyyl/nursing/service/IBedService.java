package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.Bed;

public interface IBedService extends IService<Bed> {

    Bed selectBedById(Long id);

    List<Bed> selectBedList(Bed bed);

    int insertBed(Bed bed);

    int updateBed(Bed bed);

    int deleteBedByIds(Long[] ids);

    int deleteBedById(Long id);
}

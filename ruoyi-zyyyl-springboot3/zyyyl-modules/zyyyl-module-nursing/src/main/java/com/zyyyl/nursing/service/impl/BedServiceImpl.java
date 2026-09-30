package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.nursing.domain.Bed;
import com.zyyyl.nursing.mapper.BedMapper;
import com.zyyyl.nursing.service.IBedService;

@Service
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements IBedService {

    @Override
    public Bed selectBedById(Long id) {
        return getById(id);
    }

    @Override
    public List<Bed> selectBedList(Bed bed) {
        return mapper.selectBedList(bed);
    }

    @Override
    public int insertBed(Bed bed) {
        return save(bed) ? 1 : 0;
    }

    @Override
    public int updateBed(Bed bed) {
        return updateById(bed) ? 1 : 0;
    }

    @Override
    public int deleteBedByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteBedById(Long id) {
        return removeById(id) ? 1 : 0;
    }
}

package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.mapper.ElderMapper;
import com.zyyyl.nursing.service.IElderService;

@Service
public class ElderServiceImpl extends ServiceImpl<ElderMapper, Elder> implements IElderService {

    @Override
    public Elder selectElderById(Long id) {
        return getById(id);
    }

    @Override
    public List<Elder> selectElderList(Elder elder) {
        return mapper.selectElderList(elder);
    }

    @Override
    public int insertElder(Elder elder) {
        return save(elder) ? 1 : 0;
    }

    @Override
    public int updateElder(Elder elder) {
        return updateById(elder) ? 1 : 0;
    }

    @Override
    public int deleteElderByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteElderById(Long id) {
        return removeById(id) ? 1 : 0;
    }
}

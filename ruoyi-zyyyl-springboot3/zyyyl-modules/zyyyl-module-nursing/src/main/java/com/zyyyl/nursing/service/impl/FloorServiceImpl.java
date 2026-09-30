package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.nursing.domain.Floor;
import com.zyyyl.nursing.mapper.FloorMapper;
import com.zyyyl.nursing.service.IFloorService;
import com.zyyyl.nursing.vo.TreeVo;

@Service
public class FloorServiceImpl extends ServiceImpl<FloorMapper, Floor> implements IFloorService {

    @Override
    public Floor selectFloorById(Long id) {
        return getById(id);
    }

    @Override
    public List<Floor> selectFloorList(Floor floor) {
        return mapper.selectFloorList(floor);
    }

    @Override
    public int insertFloor(Floor floor) {
        return save(floor) ? 1 : 0;
    }

    @Override
    public int updateFloor(Floor floor) {
        return updateById(floor) ? 1 : 0;
    }

    @Override
    public int deleteFloorByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteFloorById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    @Override
    public List<TreeVo> getRoomAndBedByBedStatus(Integer status) {
        return mapper.getRoomAndBedByBedStatus(status);
    }

    @Override
    public List<Floor> selectAllByNur() {
        return mapper.selectAllByNur();
    }
}

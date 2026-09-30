package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.Floor;
import com.zyyyl.nursing.vo.TreeVo;

public interface IFloorService extends IService<Floor> {

    Floor selectFloorById(Long id);

    List<Floor> selectFloorList(Floor floor);

    int insertFloor(Floor floor);

    int updateFloor(Floor floor);

    int deleteFloorByIds(Long[] ids);

    int deleteFloorById(Long id);

    List<TreeVo> getRoomAndBedByBedStatus(Integer status);

    List<Floor> selectAllByNur();
}

package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.Floor;
import com.zyyyl.nursing.vo.TreeVo;

public interface FloorMapper extends BaseMapper<Floor> {

    List<Floor> selectFloorList(Floor floor);

    List<TreeVo> getRoomAndBedByBedStatus(@Param("status") Integer status);

    List<Floor> selectAllByNur();
}

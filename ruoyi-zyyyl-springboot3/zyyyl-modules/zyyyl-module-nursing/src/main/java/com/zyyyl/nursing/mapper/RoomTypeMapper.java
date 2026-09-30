package com.zyyyl.nursing.mapper;

import java.util.List;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.RoomType;

public interface RoomTypeMapper extends BaseMapper<RoomType> {

    List<RoomType> selectRoomTypeList(RoomType roomType);
}

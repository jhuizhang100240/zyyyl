package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.RoomType;

public interface IRoomTypeService extends IService<RoomType> {

    RoomType selectRoomTypeById(Long id);

    List<RoomType> selectRoomTypeList(RoomType roomType);

    int insertRoomType(RoomType roomType);

    int updateRoomType(RoomType roomType);

    int deleteRoomTypeByIds(Long[] ids);

    int deleteRoomTypeById(Long id);

    List<RoomType> findRoomTypeListByStatus(Integer status);
}

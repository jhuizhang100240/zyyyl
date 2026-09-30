package com.zyyyl.nursing.mapper;

import java.util.List;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.Room;
import com.zyyyl.nursing.vo.RoomVo;

public interface RoomMapper extends BaseMapper<Room> {

    List<Room> selectRoomList(Room room);

    List<RoomVo> selectByFloorId(Long floorId);

    RoomVo getRoomById(Long id);

    List<RoomVo> selectByFloorIdWithNur(Long floorId);
}

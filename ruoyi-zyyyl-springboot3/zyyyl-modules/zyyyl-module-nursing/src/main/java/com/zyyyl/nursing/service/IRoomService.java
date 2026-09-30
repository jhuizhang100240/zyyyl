package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.Room;
import com.zyyyl.nursing.vo.RoomVo;

public interface IRoomService extends IService<Room> {

    List<RoomVo> getRoomsWithNurByFloorId(Long floorId);

    Room selectRoomById(Long id);

    List<Room> selectRoomList(Room room);

    int insertRoom(Room room);

    int updateRoom(Room room);

    int deleteRoomByIds(Long[] ids);

    List<RoomVo> getRoomsByFloorId(Long floorId);

    RoomVo getRoomById(Long id);
}

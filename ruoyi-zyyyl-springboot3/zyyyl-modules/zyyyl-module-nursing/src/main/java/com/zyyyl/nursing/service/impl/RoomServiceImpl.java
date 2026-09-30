package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.nursing.domain.Room;
import com.zyyyl.nursing.mapper.RoomMapper;
import com.zyyyl.nursing.service.IRoomService;
import com.zyyyl.nursing.vo.RoomVo;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Override
    public List<RoomVo> getRoomsWithNurByFloorId(Long floorId) {
        return mapper.selectByFloorIdWithNur(floorId);
    }

    @Override
    public Room selectRoomById(Long id) {
        return getById(id);
    }

    @Override
    public List<Room> selectRoomList(Room room) {
        return mapper.selectRoomList(room);
    }

    @Override
    public int insertRoom(Room room) {
        return save(room) ? 1 : 0;
    }

    @Override
    public int updateRoom(Room room) {
        return updateById(room) ? 1 : 0;
    }

    @Override
    public int deleteRoomByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public List<RoomVo> getRoomsByFloorId(Long floorId) {
        return mapper.selectByFloorId(floorId);
    }

    @Override
    public RoomVo getRoomById(Long id) {
        return mapper.getRoomById(id);
    }
}

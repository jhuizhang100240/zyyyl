package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.nursing.domain.RoomType;
import com.zyyyl.nursing.mapper.RoomTypeMapper;
import com.zyyyl.nursing.service.IRoomTypeService;

@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType> implements IRoomTypeService {

    @Override
    public RoomType selectRoomTypeById(Long id) {
        return getById(id);
    }

    @Override
    public List<RoomType> selectRoomTypeList(RoomType roomType) {
        return mapper.selectRoomTypeList(roomType);
    }

    @Override
    public int insertRoomType(RoomType roomType) {
        return save(roomType) ? 1 : 0;
    }

    @Override
    public int updateRoomType(RoomType roomType) {
        return updateById(roomType) ? 1 : 0;
    }

    @Override
    public int deleteRoomTypeByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteRoomTypeById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    @Override
    public List<RoomType> findRoomTypeListByStatus(Integer status) {
        if (status == null) {
            throw new ServiceException("参数为空");
        }
        return queryChain().eq(RoomType::getStatus, status.longValue()).list();
    }
}

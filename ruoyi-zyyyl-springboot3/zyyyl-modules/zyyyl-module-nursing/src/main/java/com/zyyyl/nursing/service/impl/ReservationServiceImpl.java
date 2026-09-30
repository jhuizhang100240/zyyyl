package com.zyyyl.nursing.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.domain.Reservation;
import com.zyyyl.nursing.dto.ReservationDto;
import com.zyyyl.nursing.dto.ReservationQueryDto;
import com.zyyyl.nursing.mapper.ReservationMapper;
import com.zyyyl.nursing.service.IReservationService;
import com.zyyyl.nursing.vo.TimeCountVo;

@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation>
        implements IReservationService {

    @Override
    public Reservation selectReservationById(Long id) {
        return getById(id);
    }

    @Override
    public List<Reservation> selectReservationList(ReservationQueryDto query) {
        return mapper.selectReservationList(query);
    }

    @Override
    public int updateReservation(Reservation reservation) {
        if (reservation == null || reservation.getId() == null) {
            throw new ServiceException("预约主键不能为空");
        }
        return updateById(reservation) ? 1 : 0;
    }

    @Override
    public int deleteReservationByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertReservation(ReservationDto dto, Long userId) {
        validateReservation(dto);
        String lockName = buildTimeSlotLockName(dto.getTime());
        Integer locked = mapper.acquireTimeSlotLock(lockName);
        if (locked == null || locked != 1) {
            throw new ServiceException("当前时段预约繁忙，请稍后重试");
        }
        try {
            if (mapper.countByTimeAndMobile(dto.getTime(), dto.getMobile()) > 0) {
                throw new ServiceException("当前手机号已预约");
            }
            if (mapper.countByTime(dto.getTime()) >= 6) {
                throw new ServiceException("当前时段已约满");
            }

            Reservation reservation = new Reservation();
            reservation.setName(dto.getName());
            reservation.setMobile(dto.getMobile());
            reservation.setTime(dto.getTime());
            reservation.setVisitor(dto.getVisitor());
            reservation.setType(dto.getType() == null ? 0 : dto.getType());
            reservation.setStatus(0);
            reservation.setCreateBy(userId == null ? null : userId.toString());
            reservation.setUpdateBy(userId == null ? null : userId.toString());
            try {
                return save(reservation) ? 1 : 0;
            } catch (DuplicateKeyException e) {
                throw new ServiceException("当前手机号已预约，请勿重复提交");
            }
        } finally {
            mapper.releaseTimeSlotLock(lockName);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelReservation(Long id, Long userId) {
        Reservation reservation = getById(id);
        if (reservation == null) {
            throw new ServiceException("当前预约不存在");
        }
        if (!Integer.valueOf(0).equals(reservation.getStatus())) {
            throw new ServiceException("当前预约状态不允许取消");
        }
        reservation.setStatus(2);
        reservation.setUpdateBy(userId == null ? null : userId.toString());
        updateById(reservation);
    }

    @Override
    public Integer cancelledCount(Long userId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return mapper.cancelledCount(userId, start, end);
    }

    @Override
    public List<TimeCountVo> getCountByTime(Long time) {
        LocalDateTime dateTime = time == null
                ? LocalDateTime.now()
                : LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        LocalDateTime start = dateTime.toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        List<TimeCountVo> result = mapper.getCountByTime(start, end);
        return result == null ? new ArrayList<>() : result;
    }

    @Override
    public TableDataInfo selectByPage(Integer pageNum, Integer pageSize, Integer status, Long userId) {
        int pageNo = pageNum == null ? 1 : pageNum;
        int size = pageSize == null ? 10 : pageSize;
        int startIndex = (pageNo - 1) * size;
        List<Reservation> rows = mapper.selectByPage(startIndex, size, status, userId);
        int total = mapper.count(status, userId);
        TableDataInfo result = new TableDataInfo();
        result.setCode(200);
        result.setMsg("请求成功");
        result.setRows(rows);
        result.setTotal(total);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateReservationStatus() {
        List<Reservation> expired = mapper.selectExpired(LocalDateTime.now());
        if (expired == null || expired.isEmpty()) {
            return;
        }
        expired.forEach(reservation -> reservation.setStatus(3));
        updateBatch(expired);
    }

    private void validateReservation(ReservationDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getMobile())
                || dto.getTime() == null || StringUtils.isEmpty(dto.getVisitor())) {
            throw new ServiceException("预约人、手机号、时间和探访人不能为空");
        }
    }

    private String buildTimeSlotLockName(LocalDateTime time) {
        return "ZYYYL_RESERVATION_" + time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}

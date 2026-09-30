package com.zyyyl.nursing.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.Reservation;
import com.zyyyl.nursing.dto.ReservationQueryDto;
import com.zyyyl.nursing.vo.TimeCountVo;

public interface ReservationMapper extends BaseMapper<Reservation> {

    List<Reservation> selectReservationList(ReservationQueryDto query);

    List<Reservation> selectByPage(@Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize,
            @Param("status") Integer status,
            @Param("userId") Long userId);

    int count(@Param("status") Integer status, @Param("userId") Long userId);

    int countByTime(@Param("time") LocalDateTime time);

    List<Reservation> selectActiveByTimeForUpdate(@Param("time") LocalDateTime time);

    int countByTimeAndMobile(@Param("time") LocalDateTime time, @Param("mobile") String mobile);

    Integer acquireTimeSlotLock(@Param("lockName") String lockName);

    Integer releaseTimeSlotLock(@Param("lockName") String lockName);

    List<Reservation> selectExpired(@Param("now") LocalDateTime now);

    List<TimeCountVo> getCountByTime(@Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    int cancelledCount(@Param("userId") Long userId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}

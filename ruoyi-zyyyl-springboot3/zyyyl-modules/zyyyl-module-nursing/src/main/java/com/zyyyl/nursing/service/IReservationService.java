package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.nursing.domain.Reservation;
import com.zyyyl.nursing.dto.ReservationDto;
import com.zyyyl.nursing.dto.ReservationQueryDto;
import com.zyyyl.nursing.vo.TimeCountVo;

public interface IReservationService extends IService<Reservation> {

    Reservation selectReservationById(Long id);

    List<Reservation> selectReservationList(ReservationQueryDto query);

    int updateReservation(Reservation reservation);

    int deleteReservationByIds(Long[] ids);

    int insertReservation(ReservationDto dto, Long userId);

    void cancelReservation(Long id, Long userId);

    Integer cancelledCount(Long userId);

    List<TimeCountVo> getCountByTime(Long time);

    TableDataInfo selectByPage(Integer pageNum, Integer pageSize, Integer status, Long userId);

    void updateReservationStatus();
}

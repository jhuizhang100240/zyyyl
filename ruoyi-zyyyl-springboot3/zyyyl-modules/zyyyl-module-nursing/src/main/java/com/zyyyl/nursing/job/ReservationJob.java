package com.zyyyl.nursing.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zyyyl.nursing.service.IReservationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "zyyyl.scheduling", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ReservationJob {

    @Autowired
    private IReservationService reservationService;

    @Scheduled(cron = "${zyyyl.scheduling.reservation-cron:0 1,31 * * * *}")
    public void updateReservationStatus() {
        try {
            reservationService.updateReservationStatus();
            log.info("预约状态更新成功");
        } catch (Exception e) {
            log.error("预约状态更新失败", e);
        }
    }
}

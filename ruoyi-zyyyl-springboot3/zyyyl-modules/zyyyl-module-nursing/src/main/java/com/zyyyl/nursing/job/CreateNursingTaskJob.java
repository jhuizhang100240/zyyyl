package com.zyyyl.nursing.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.service.IElderService;
import com.zyyyl.nursing.service.INursingTaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "zyyyl.scheduling", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CreateNursingTaskJob {

    @Autowired
    private IElderService elderService;

    @Autowired
    private INursingTaskService nursingTaskService;

    @Scheduled(cron = "${zyyyl.scheduling.task-cron:0 0 1 1 * *}")
    public void createNursingTaskJob() {
        List<Elder> elders = elderService.list().stream()
                .filter(elder -> Integer.valueOf(1).equals(elder.getStatus()))
                .toList();
        for (Elder elder : elders) {
            try {
                nursingTaskService.createMonthTask(elder);
            } catch (Exception e) {
                log.error("护理任务生成失败，elderId={}", elder.getId(), e);
            }
        }
        log.info("护理任务生成任务执行完成，老人数量={}", elders.size());
    }
}

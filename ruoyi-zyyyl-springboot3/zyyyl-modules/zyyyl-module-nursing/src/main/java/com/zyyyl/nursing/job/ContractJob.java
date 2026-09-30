package com.zyyyl.nursing.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zyyyl.nursing.service.IContractService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "zyyyl.scheduling", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ContractJob {

    @Autowired
    private IContractService contractService;

    @Scheduled(cron = "${zyyyl.scheduling.contract-cron:0 0 1 * * *}")
    public void updateContractStatus() {
        try {
            contractService.updateContractStatus();
            log.info("合同状态更新成功");
        } catch (Exception e) {
            log.error("合同状态更新失败", e);
        }
    }
}

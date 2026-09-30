package com.zyyyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.common.utils.uuid.IdUtils;
import com.zyyyl.nursing.domain.Contract;
import com.zyyyl.nursing.mapper.ContractMapper;
import com.zyyyl.nursing.service.IContractService;

@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements IContractService {

    @Override
    public Contract selectContractById(Long id) {
        return getById(id);
    }

    @Override
    public List<Contract> selectContractList(Contract contract) {
        return mapper.selectContractList(contract);
    }

    @Override
    public int insertContract(Contract contract) {
        if (StringUtils.isEmpty(contract.getContractNumber())) {
            contract.setContractNumber(generateContractNumber());
        }
        ensureContractNumberUnique(contract.getContractNumber(), null);
        return save(contract) ? 1 : 0;
    }

    @Override
    public int updateContract(Contract contract) {
        if (contract.getId() == null) {
            throw new ServiceException("合同主键不能为空");
        }
        if (StringUtils.isNotEmpty(contract.getContractNumber())) {
            ensureContractNumberUnique(contract.getContractNumber(), contract.getId());
        }
        return updateById(contract) ? 1 : 0;
    }

    @Override
    public int deleteContractByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteContractById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    @Override
    public void updateContractStatus() {
        List<Contract> contracts = queryChain()
                .le(Contract::getStartDate, LocalDateTime.now())
                .eq(Contract::getStatus, 0)
                .list();
        if (contracts.isEmpty()) {
            return;
        }
        contracts.forEach(contract -> contract.setStatus(1));
        updateBatch(contracts);
    }

    private void ensureContractNumberUnique(String contractNumber, Long id) {
        int count = id == null
                ? mapper.countByContractNumber(contractNumber)
                : mapper.countByContractNumberExcludingId(contractNumber, id);
        if (count > 0) {
            throw new ServiceException("合同编号已存在");
        }
    }

    private String generateContractNumber() {
        return "HT" + IdUtils.fastSimpleUUID().substring(0, 16).toUpperCase();
    }
}

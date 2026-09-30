package com.zyyyl.nursing.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.Contract;

public interface IContractService extends IService<Contract> {

    Contract selectContractById(Long id);

    List<Contract> selectContractList(Contract contract);

    int insertContract(Contract contract);

    int updateContract(Contract contract);

    int deleteContractByIds(Long[] ids);

    int deleteContractById(Long id);

    void updateContractStatus();
}

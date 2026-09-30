package com.zyyyl.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatisflex.core.BaseMapper;
import com.zyyyl.nursing.domain.Contract;

public interface ContractMapper extends BaseMapper<Contract> {

    List<Contract> selectContractList(Contract contract);

    Contract selectByElderId(@Param("elderId") Long elderId);

    int countByContractNumber(@Param("contractNumber") String contractNumber);

    int countByContractNumberExcludingId(@Param("contractNumber") String contractNumber, @Param("id") Long id);
}

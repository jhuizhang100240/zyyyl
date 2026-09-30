package com.zyyyl.nursing.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.JSON;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.common.utils.uuid.IdUtils;
import com.zyyyl.nursing.domain.Bed;
import com.zyyyl.nursing.domain.CheckIn;
import com.zyyyl.nursing.domain.CheckInConfig;
import com.zyyyl.nursing.domain.Contract;
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.dto.CheckInApplyDto;
import com.zyyyl.nursing.dto.CheckInConfigDto;
import com.zyyyl.nursing.dto.CheckInContractDto;
import com.zyyyl.nursing.dto.CheckInElderDto;
import com.zyyyl.nursing.mapper.BedMapper;
import com.zyyyl.nursing.mapper.CheckInConfigMapper;
import com.zyyyl.nursing.mapper.CheckInMapper;
import com.zyyyl.nursing.mapper.ContractMapper;
import com.zyyyl.nursing.mapper.ElderMapper;
import com.zyyyl.nursing.service.ICheckInService;
import com.zyyyl.nursing.vo.CheckInConfigVo;
import com.zyyyl.nursing.vo.CheckInDetailVo;
import com.zyyyl.nursing.vo.CheckInElderVo;
import com.zyyyl.nursing.vo.ElderFamilyVo;

@Service
public class CheckInServiceImpl extends ServiceImpl<CheckInMapper, CheckIn> implements ICheckInService {

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private BedMapper bedMapper;

    @Autowired
    private CheckInConfigMapper checkInConfigMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public CheckIn selectCheckInById(Long id) {
        return getById(id);
    }

    @Override
    public List<CheckIn> selectCheckInList(CheckIn checkIn) {
        return mapper.selectCheckInList(checkIn);
    }

    @Override
    public int insertCheckIn(CheckIn checkIn) {
        return save(checkIn) ? 1 : 0;
    }

    @Override
    public int updateCheckIn(CheckIn checkIn) {
        return updateById(checkIn) ? 1 : 0;
    }

    @Override
    public int deleteCheckInByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteCheckInById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    @Override
    public CheckInDetailVo detail(Long id) {
        CheckIn checkIn = getById(id);
        if (checkIn == null) {
            throw new ServiceException("入住记录不存在");
        }

        CheckInDetailVo vo = new CheckInDetailVo();
        Elder elder = elderMapper.selectOneById(checkIn.getElderId());
        if (elder != null) {
            CheckInElderVo elderVo = new CheckInElderVo();
            BeanUtils.copyProperties(elder, elderVo);
            elderVo.setAge(resolveAge(elder.getBirthday()));
            vo.setCheckInElderVo(elderVo);
        }

        CheckInConfig config = checkInConfigMapper.selectByCheckInId(id);
        if (config != null) {
            CheckInConfigVo configVo = new CheckInConfigVo();
            BeanUtils.copyProperties(config, configVo);
            configVo.setStartDate(checkIn.getStartDate());
            configVo.setEndDate(checkIn.getEndDate());
            configVo.setBedNumber(checkIn.getBedNumber());
            vo.setCheckInConfigVo(configVo);
        }

        if (elder != null) {
            vo.setContract(contractMapper.selectByElderId(elder.getId()));
        }
        vo.setElderFamilyVoList(parseFamily(checkIn.getRemark()));
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void apply(CheckInApplyDto dto) {
        validateApplyDto(dto);

        CheckInElderDto elderDto = dto.getCheckInElderDto();
        CheckInConfigDto configDto = dto.getCheckInConfigDto();

        Elder activeElder = elderMapper.selectElderByIdCardAndStatusForUpdate(elderDto.getIdCardNo(), 1);
        if (activeElder != null) {
            throw new ServiceException("老人已入住");
        }

        Bed bed = bedMapper.selectBedForUpdate(configDto.getBedId());
        if (bed == null) {
            throw new ServiceException("床位不存在");
        }
        if (Integer.valueOf(1).equals(bed.getBedStatus())) {
            throw new ServiceException("床位已入住");
        }
        bed.setBedStatus(1);
        bedMapper.update(bed);

        Elder elder = insertOrUpdateElder(elderDto, bed);
        insertContract(dto, elder);
        CheckIn checkIn = insertCheckIn(dto, elder);
        insertCheckInConfig(configDto, checkIn);
        mapper.updateHealthAssessmentStatus(elder.getIdCardNo());
    }

    private void validateApplyDto(CheckInApplyDto dto) {
        if (dto == null || dto.getCheckInElderDto() == null
                || dto.getCheckInConfigDto() == null
                || dto.getCheckInContractDto() == null) {
            throw new ServiceException("入住申请参数不完整");
        }
        if (StringUtils.isEmpty(dto.getCheckInElderDto().getIdCardNo())) {
            throw new ServiceException("老人身份证号不能为空");
        }
        if (dto.getCheckInConfigDto().getBedId() == null) {
            throw new ServiceException("床位不能为空");
        }
    }

    private Elder insertOrUpdateElder(CheckInElderDto dto, Bed bed) {
        Elder saved = elderMapper.selectElderByIdCardAndStatusForUpdate(dto.getIdCardNo(), 0);
        Elder elder = saved == null ? new Elder() : saved;
        elder.setName(dto.getName());
        elder.setImage(dto.getImage());
        elder.setIdCardNo(dto.getIdCardNo());
        elder.setSex(dto.getSex());
        elder.setPhone(dto.getPhone());
        elder.setBirthday(dto.getBirthday());
        elder.setAddress(dto.getAddress());
        elder.setIdCardNationalEmblemImg(dto.getIdCardNationalEmblemImg());
        elder.setIdCardPortraitImg(dto.getIdCardPortraitImg());
        elder.setBedId(bed.getId());
        elder.setBedNumber(bed.getBedNumber());
        elder.setStatus(1);
        if (saved == null) {
            elderMapper.insert(elder);
        } else {
            elderMapper.update(elder);
        }
        return elder;
    }

    private void insertContract(CheckInApplyDto dto, Elder elder) {
        CheckInContractDto contractDto = dto.getCheckInContractDto();
        CheckInConfigDto configDto = dto.getCheckInConfigDto();
        Contract contract = new Contract();
        contract.setElderId(elder.getId());
        contract.setElderName(elder.getName());
        contract.setContractName(contractDto.getContractName());
        contract.setContractNumber(generateContractNumber());
        contract.setAgreementPath(StringUtils.isEmpty(contractDto.getAgreementPath())
                ? ""
                : contractDto.getAgreementPath());
        contract.setThirdPartyName(contractDto.getThirdPartyName());
        contract.setThirdPartyPhone(contractDto.getThirdPartyPhone());
        contract.setStartDate(configDto.getStartDate());
        contract.setEndDate(configDto.getEndDate());
        contract.setSignDate(contractDto.getSignDate() == null ? LocalDateTime.now() : contractDto.getSignDate());
        contract.setStatus(resolveContractStatus(configDto.getStartDate()));
        contract.setSortOrder(0);
        contractMapper.insert(contract);
    }

    private CheckIn insertCheckIn(CheckInApplyDto dto, Elder elder) {
        CheckIn checkIn = new CheckIn();
        checkIn.setStatus(0);
        checkIn.setBedNumber(elder.getBedNumber());
        checkIn.setElderId(elder.getId());
        checkIn.setElderName(elder.getName());
        checkIn.setIdCardNo(elder.getIdCardNo());
        checkIn.setStartDate(dto.getCheckInConfigDto().getStartDate());
        checkIn.setEndDate(dto.getCheckInConfigDto().getEndDate());
        checkIn.setNursingLevelName(dto.getCheckInConfigDto().getNursingLevelName());
        checkIn.setSortOrder(0);
        checkIn.setRemark(JSON.toJSONString(dto.getElderFamilyDtoList() == null
                ? Collections.emptyList()
                : dto.getElderFamilyDtoList()));
        mapper.insert(checkIn);
        return checkIn;
    }

    private void insertCheckInConfig(CheckInConfigDto dto, CheckIn checkIn) {
        CheckInConfig config = new CheckInConfig();
        config.setCheckInId(checkIn.getId());
        config.setNursingLevelId(dto.getNursingLevelId());
        config.setNursingLevelName(dto.getNursingLevelName());
        config.setFeeStartDate(dto.getFeeStartDate());
        config.setFeeEndDate(dto.getFeeEndDate());
        config.setDeposit(dto.getDeposit());
        config.setNursingFee(dto.getNursingFee());
        config.setBedFee(dto.getBedFee());
        config.setInsurancePayment(dto.getInsurancePayment());
        config.setGovernmentSubsidy(dto.getGovernmentSubsidy());
        config.setOtherFees(dto.getOtherFees());
        config.setSortOrder(0);
        checkInConfigMapper.insert(config);
    }

    private String generateContractNumber() {
        String contractNumber;
        do {
            contractNumber = "HT" + IdUtils.fastSimpleUUID().substring(0, 16).toUpperCase();
        } while (contractMapper.countByContractNumber(contractNumber) > 0);
        return contractNumber;
    }

    private Integer resolveContractStatus(LocalDateTime startDate) {
        return startDate != null && startDate.isAfter(LocalDateTime.now()) ? 0 : 1;
    }

    private Integer resolveAge(String birthday) {
        if (StringUtils.isEmpty(birthday)) {
            return null;
        }
        try {
            return LocalDate.now().getYear() - LocalDate.parse(birthday).getYear();
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private List<ElderFamilyVo> parseFamily(String remark) {
        if (StringUtils.isEmpty(remark)) {
            return Collections.emptyList();
        }
        try {
            return JSON.getObjectMapper().readValue(remark, new TypeReference<List<ElderFamilyVo>>() {
            });
        } catch (Exception e) {
            throw new ServiceException("入住家属信息格式错误");
        }
    }
}

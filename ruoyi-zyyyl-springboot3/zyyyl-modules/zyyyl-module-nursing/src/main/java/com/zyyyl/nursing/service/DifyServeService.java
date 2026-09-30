package com.zyyyl.nursing.service;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mybatisflex.core.query.QueryWrapper;
import com.zyyyl.common.utils.IdCardUtils;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.domain.CheckIn;
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.domain.HealthAssessment;
import com.zyyyl.nursing.domain.HealthAssessmentDetail;
import com.zyyyl.nursing.domain.Reservation;
import com.zyyyl.nursing.mapper.CheckInMapper;
import com.zyyyl.nursing.mapper.ElderMapper;
import com.zyyyl.nursing.mapper.HealthAssessmentDetailMapper;
import com.zyyyl.nursing.mapper.HealthAssessmentMapper;
import com.zyyyl.nursing.mapper.ReservationMapper;
import com.zyyyl.nursing.vo.ElderBasicInfoVo;
import com.zyyyl.nursing.vo.ElderHealthInfoVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 供 Dify 工具调用的业务查询服务。
 */
@Slf4j
@Service
public class DifyServeService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ElderMapper elderMapper;
    private final CheckInMapper checkInMapper;
    private final HealthAssessmentMapper healthAssessmentMapper;
    private final HealthAssessmentDetailMapper healthAssessmentDetailMapper;
    private final ReservationMapper reservationMapper;

    public DifyServeService(ElderMapper elderMapper, CheckInMapper checkInMapper,
            HealthAssessmentMapper healthAssessmentMapper,
            HealthAssessmentDetailMapper healthAssessmentDetailMapper,
            ReservationMapper reservationMapper) {
        this.elderMapper = elderMapper;
        this.checkInMapper = checkInMapper;
        this.healthAssessmentMapper = healthAssessmentMapper;
        this.healthAssessmentDetailMapper = healthAssessmentDetailMapper;
        this.reservationMapper = reservationMapper;
    }

    public Map<String, Object> getElderBasicInfo(String nameOrId) {
        if (StringUtils.isEmpty(nameOrId)) {
            return message("请提供老人姓名或老人ID");
        }
        Elder elder = findElder(nameOrId.trim());
        if (elder == null) {
            return message("未查询到姓名或ID为【" + nameOrId.trim() + "】的老人");
        }
        ElderBasicInfoVo vo = new ElderBasicInfoVo();
        vo.setElderId(elder.getId());
        vo.setName(elder.getName());
        vo.setSex(Integer.valueOf(0).equals(elder.getSex()) ? "女" : "男");
        try {
            vo.setAge(IdCardUtils.getAge(elder.getIdCardNo()));
            vo.setBirthday(IdCardUtils.getBirthDate(elder.getIdCardNo()).format(DATE_FORMATTER));
        } catch (Exception e) {
            log.debug("老人 {} 身份证号无法解析年龄和生日", elder.getId());
        }
        vo.setIdCardNo(elder.getIdCardNo());
        vo.setPhone(elder.getPhone());
        vo.setStatus(convertStatus(elder.getStatus()));
        vo.setBedNumber(elder.getBedNumber());
        vo.setRoomNumber(extractRoomNumber(elder.getBedNumber()));
        CheckIn checkIn = checkInMapper.selectLatestByElderId(elder.getId());
        if (checkIn != null) {
            if (checkIn.getStartDate() != null) {
                vo.setCheckInTime(checkIn.getStartDate().format(DATE_FORMATTER));
            }
            vo.setNursingLevel(checkIn.getNursingLevelName());
            if (StringUtils.isNotEmpty(checkIn.getBedNumber())) {
                vo.setBedNumber(checkIn.getBedNumber());
                vo.setRoomNumber(extractRoomNumber(checkIn.getBedNumber()));
            }
        }
        return toMap(vo);
    }

    public Map<String, Object> getElderHealthInfo(String nameOrId) {
        if (StringUtils.isEmpty(nameOrId)) {
            return message("请提供老人姓名或老人ID");
        }
        String keyword = nameOrId.trim();
        Elder elder = findElder(keyword);
        HealthAssessment assessment = findLatestAssessment(elder, keyword);
        if (assessment == null) {
            String elderName = elder == null ? keyword : elder.getName();
            return message("未查询到【" + elderName + "】的健康评估记录，请先完成健康评估");
        }
        HealthAssessmentDetail detail = healthAssessmentDetailMapper.selectByAssessmentId(assessment.getId());
        ElderHealthInfoVo vo = new ElderHealthInfoVo();
        vo.setElderName(assessment.getElderName());
        vo.setHealthScore(assessment.getHealthScore());
        vo.setRiskLevel(detail == null ? null : detail.getRiskLevel());
        vo.setReportSummary(detail == null ? null : detail.getReportSummary());
        vo.setTotalCheckDate(assessment.getTotalCheckDate());
        vo.setAssessmentTime(assessment.getAssessmentTime() == null
                ? null
                : assessment.getAssessmentTime().format(DATETIME_FORMATTER));
        return toMap(vo);
    }

    public Map<String, Object> getReservationsByDate(String datetime) {
        java.time.LocalDate date;
        if (StringUtils.isEmpty(datetime)) {
            date = java.time.LocalDate.now();
        } else {
            try {
                date = java.time.LocalDate.parse(datetime, DATE_FORMATTER);
            } catch (Exception e) {
                return message("日期格式应为 yyyy-MM-dd");
            }
        }
        List<Reservation> reservations = reservationMapper.selectByDate(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        List<Map<String, Object>> items = reservations.stream().map(item -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("name", item.getName());
            row.put("mobile", maskPhone(item.getMobile()));
            row.put("time", item.getTime() == null ? null : item.getTime().format(DATETIME_FORMATTER));
            row.put("visitor", item.getVisitor());
            row.put("type", Integer.valueOf(1).equals(item.getType()) ? "探访预约" : "参观预约");
            row.put("status", convertReservationStatus(item.getStatus()));
            return row;
        }).toList();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("date", date.format(DATE_FORMATTER));
        result.put("total", items.size());
        result.put("reservations", items);
        return result;
    }

    private Elder findElder(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        if (keyword.matches("\\d+")) {
            Elder byId = elderMapper.selectOneById(Long.valueOf(keyword));
            if (byId != null) {
                return byId;
            }
        }
        QueryWrapper wrapper = QueryWrapper.create()
                .where(Elder::getName).eq(keyword)
                .orderBy(Elder::getId).desc()
                .limit(1);
        return elderMapper.selectOneByQuery(wrapper);
    }

    private HealthAssessment findLatestAssessment(Elder elder, String keyword) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (elder != null && StringUtils.isNotEmpty(elder.getIdCardNo())) {
            wrapper.where(HealthAssessment::getIdCard).eq(elder.getIdCardNo());
        } else {
            wrapper.where(HealthAssessment::getElderName).eq(elder == null ? keyword : elder.getName());
        }
        wrapper.orderBy(HealthAssessment::getId).desc().limit(1);
        return healthAssessmentMapper.selectOneByQuery(wrapper);
    }

    private String extractRoomNumber(String bedNumber) {
        if (StringUtils.isEmpty(bedNumber)) {
            return null;
        }
        int index = bedNumber.lastIndexOf('-');
        return index > 0 ? bedNumber.substring(0, index) : bedNumber;
    }

    private String convertStatus(Integer status) {
        if (status == null) {
            return null;
        }
        return switch (status) {
            case 0 -> "禁用";
            case 1 -> "启用";
            case 2 -> "请假";
            case 3 -> "退住中";
            case 4 -> "入住中";
            case 5 -> "已退住";
            default -> String.valueOf(status);
        };
    }

    private String convertReservationStatus(Integer status) {
        if (status == null) {
            return null;
        }
        return switch (status) {
            case 0 -> "待报道";
            case 1 -> "已完成";
            case 2 -> "已取消";
            case 3 -> "已过期";
            default -> String.valueOf(status);
        };
    }

    private String maskPhone(String phone) {
        if (StringUtils.isEmpty(phone) || phone.length() < 7) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }

    private Map<String, Object> message(String text) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", text);
        return result;
    }

    private Map<String, Object> toMap(Object vo) {
        return com.zyyyl.common.utils.JSON.getObjectMapper().convertValue(vo, Map.class);
    }
}

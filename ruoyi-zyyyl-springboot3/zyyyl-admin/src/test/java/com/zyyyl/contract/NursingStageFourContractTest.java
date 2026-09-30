package com.zyyyl.contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.zyyyl.nursing.controller.NursingTaskController;
import com.zyyyl.nursing.controller.ReservationController;
import com.zyyyl.nursing.controller.member.MemberReservationController;
import com.zyyyl.nursing.domain.NursingTask;
import com.zyyyl.nursing.domain.Reservation;
import com.zyyyl.nursing.mapper.NursingTaskAssigneeMapper;
import com.zyyyl.nursing.mapper.NursingTaskMapper;
import com.zyyyl.nursing.mapper.ReservationMapper;

class NursingStageFourContractTest {

    @Test
    void stageFourControllersKeepTheirExpectedOperations() {
        assertThat(methodNames(NursingTaskController.class))
                .contains("list", "getInfo", "cancel", "updateTime", "doTask", "add", "edit", "remove");
        assertThat(methodNames(ReservationController.class))
                .contains("list", "getInfo", "edit", "remove");
        assertThat(methodNames(MemberReservationController.class))
                .contains("cancelledCount", "countByTime", "insertReservation", "page", "cancel");
    }

    @Test
    void stageFourMappersExposeTaskAndReservationQueries() throws Exception {
        assertThat(NursingTaskMapper.class.getMethod("selectByPage",
                com.zyyyl.nursing.dto.NursingTaskDto.class)).isNotNull();
        assertThat(NursingTaskAssigneeMapper.class.getMethod("selectNursingIdsByTaskId", Long.class)).isNotNull();
        assertThat(ReservationMapper.class.getMethod("selectReservationList",
                com.zyyyl.nursing.dto.ReservationQueryDto.class)).isNotNull();
        assertThat(NursingTask.class).isNotNull();
        assertThat(Reservation.class).isNotNull();
    }

    private static Set<String> methodNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toSet());
    }
}

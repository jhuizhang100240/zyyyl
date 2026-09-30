package com.zyyyl.contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.zyyyl.nursing.controller.BedController;
import com.zyyyl.nursing.controller.CheckInController;
import com.zyyyl.nursing.controller.ContractController;
import com.zyyyl.nursing.controller.ElderController;
import com.zyyyl.nursing.domain.Bed;
import com.zyyyl.nursing.domain.CheckIn;
import com.zyyyl.nursing.domain.Contract;
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.mapper.BedMapper;
import com.zyyyl.nursing.mapper.CheckInMapper;
import com.zyyyl.nursing.mapper.ContractMapper;
import com.zyyyl.nursing.mapper.ElderMapper;

class NursingStageTwoContractTest {

    @Test
    void chronicleControllersKeepTheirExpectedOperations() {
        assertThat(methodNames(ElderController.class)).contains("list", "export", "getInfo", "add", "edit", "remove");
        assertThat(methodNames(BedController.class)).contains("list", "getInfo", "add", "edit", "remove");
        assertThat(methodNames(CheckInController.class))
                .contains("detail", "apply", "list", "export", "getInfo", "add", "edit", "remove");
        assertThat(methodNames(ContractController.class))
                .contains("list", "export", "getInfo", "add", "edit", "remove");
    }

    @Test
    void coreMappersExposeListAndLookupContracts() throws Exception {
        assertThat(ElderMapper.class.getMethod("selectElderList", Elder.class)).isNotNull();
        assertThat(BedMapper.class.getMethod("selectBedList", Bed.class)).isNotNull();
        assertThat(CheckInMapper.class.getMethod("selectCheckInList", CheckIn.class)).isNotNull();
        assertThat(ContractMapper.class.getMethod("selectContractList", Contract.class)).isNotNull();
    }

    private static Set<String> methodNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toSet());
    }
}

package com.zyyyl.contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.zyyyl.nursing.controller.HealthAssessmentController;
import com.zyyyl.nursing.controller.NursingLevelController;
import com.zyyyl.nursing.controller.NursingPlanController;
import com.zyyyl.nursing.controller.NursingProjectController;
import com.zyyyl.nursing.domain.HealthAssessment;
import com.zyyyl.nursing.domain.NursingLevel;
import com.zyyyl.nursing.domain.NursingPlan;
import com.zyyyl.nursing.domain.NursingProject;
import com.zyyyl.nursing.mapper.HealthAssessmentDetailMapper;
import com.zyyyl.nursing.mapper.HealthAssessmentMapper;
import com.zyyyl.nursing.mapper.NursingLevelMapper;
import com.zyyyl.nursing.mapper.NursingPlanMapper;
import com.zyyyl.nursing.mapper.NursingProjectMapper;
import com.zyyyl.nursing.mapper.NursingProjectPlanMapper;

class NursingStageThreeContractTest {

    @Test
    void stageThreeControllersKeepTheirExpectedOperations() {
        assertThat(methodNames(HealthAssessmentController.class))
                .contains("list", "export", "getInfo", "add", "edit", "remove");
        assertThat(methodNames(NursingLevelController.class))
                .contains("listAll", "list", "getInfo", "add", "edit", "remove");
        assertThat(methodNames(NursingProjectController.class))
                .contains("listAll", "list", "export", "getInfo", "add", "edit", "remove");
        assertThat(methodNames(NursingPlanController.class))
                .contains("listAll", "list", "export", "getInfo", "add", "edit", "remove");
    }

    @Test
    void stageThreeMappersExposeRequiredQueries() throws Exception {
        assertThat(HealthAssessmentMapper.class.getMethod("selectHealthAssessmentList", HealthAssessment.class))
                .isNotNull();
        assertThat(HealthAssessmentDetailMapper.class.getMethod("selectByAssessmentId", Long.class)).isNotNull();
        assertThat(NursingLevelMapper.class.getMethod("selectNursingLevelList", NursingLevel.class)).isNotNull();
        assertThat(NursingProjectMapper.class.getMethod("selectNursingProjectList", NursingProject.class)).isNotNull();
        assertThat(NursingPlanMapper.class.getMethod("selectNursingPlanList", NursingPlan.class)).isNotNull();
        assertThat(NursingProjectPlanMapper.class.getMethod("selectByPlanId", Long.class)).isNotNull();
    }

    private static Set<String> methodNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toSet());
    }
}

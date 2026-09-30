package com.zyyyl.contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zyyyl.nursing.controller.ChatController;
import com.zyyyl.nursing.controller.DifyServeController;
import com.zyyyl.nursing.controller.member.FamilyMemberController;
import com.zyyyl.nursing.controller.member.MemberNursingProjectController;
import com.zyyyl.nursing.controller.member.MemberReservationController;
import com.zyyyl.nursing.controller.member.MemberRoomTypeController;

class NursingStageFiveContractTest {

    @Test
    void memberUserControllerKeepsLegacyAndWechatEndpoints() {
        assertThat(classMapping(FamilyMemberController.class)).isEqualTo("/member/user");
        assertThat(methodNames(FamilyMemberController.class))
                .contains("login", "wxLogin", "wxBind", "add", "my", "listByPage", "deleteById");
        assertThat(mappedPaths(FamilyMemberController.class, PostMapping.class))
                .contains("/login", "/wx-login", "/wx-bind", "/add");
        assertThat(mappedPaths(FamilyMemberController.class, GetMapping.class))
                .contains("/my", "/list-by-page");
        assertThat(mappedPaths(FamilyMemberController.class, DeleteMapping.class))
                .contains("/deleteById");
    }

    @Test
    void memberFeatureControllersExposeDocumentedPaths() {
        assertThat(classMapping(MemberRoomTypeController.class)).isEqualTo("/member/roomTypes");
        assertThat(classMapping(MemberNursingProjectController.class)).isEqualTo("/member/orders");
        assertThat(classMapping(MemberReservationController.class)).isEqualTo("/member/reservation");
        assertThat(mappedPaths(MemberNursingProjectController.class, GetMapping.class))
                .contains("/project/{id}");
    }

    @Test
    void aiAndDifyServeControllersExposeDocumentedPaths() {
        assertThat(classMapping(ChatController.class)).isEqualTo("/ai");
        assertThat(mappedPaths(ChatController.class, PostMapping.class)).contains("/chat");
        assertThat(mappedPaths(ChatController.class, GetMapping.class))
                .contains("/history", "/history/{chatId}");
        assertThat(mappedPaths(ChatController.class, DeleteMapping.class)).contains("/history/{chatId}");

        assertThat(classMapping(DifyServeController.class)).isEqualTo("/dify/serve");
        assertThat(mappedPaths(DifyServeController.class, GetMapping.class))
                .contains("/getElderBasicInfo", "/getElderHealthInfo", "/getReservationByToday");
    }

    private String classMapping(Class<?> type) {
        RequestMapping mapping = type.getAnnotation(RequestMapping.class);
        assertThat(mapping).as("controller mapping").isNotNull();
        return mapping.value()[0];
    }

    private Set<String> methodNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toSet());
    }

    private Set<String> mappedPaths(Class<?> type, Class<? extends java.lang.annotation.Annotation> annotation) {
        return Arrays.stream(type.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .flatMap(method -> {
                    if (annotation == GetMapping.class) {
                        return Arrays.stream(method.getAnnotation(GetMapping.class).value());
                    }
                    if (annotation == PostMapping.class) {
                        return Arrays.stream(method.getAnnotation(PostMapping.class).value());
                    }
                    return Arrays.stream(method.getAnnotation(DeleteMapping.class).value());
                })
                .collect(Collectors.toSet());
    }
}

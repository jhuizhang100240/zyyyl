package com.zyyyl.contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.zyyyl.ZyyylApplication;
import com.zyyyl.nursing.contract.LegacyApiPaths;
import com.zyyyl.nursing.controller.BedController;
import com.zyyyl.nursing.controller.FloorController;
import com.zyyyl.nursing.controller.RoomController;
import com.zyyyl.nursing.controller.RoomTypeController;
import com.zyyyl.nursing.controller.ElderController;
import com.zyyyl.web.controller.system.SysLoginController;

class LegacyApiPathBaselineTest {

    @Test
    void coreSystemRoutesRemainFrozen() {
        Set<String> routes = collectRoutes(SysLoginController.class);

        assertThat(routes).contains("/login", "/getInfo", "/getRouters");
    }

    @Test
    void legacyApiPrefixesRemainFrozen() {
        assertThat(LegacyApiPaths.ALL).isNotEmpty();
        assertThat(LegacyApiPaths.ALL).allMatch(path -> path.startsWith("/"));
        assertThat(LegacyApiPaths.ALL).noneMatch(path -> path.startsWith("/api/") || path.startsWith("/v1"));

        assertThat(LegacyApiPaths.NURSING).allMatch(path -> path.startsWith("/nursing/"));
        assertThat(LegacyApiPaths.MEMBER).allMatch(path -> path.startsWith("/member/"));
        assertThat(LegacyApiPaths.AI).allMatch(path -> path.startsWith("/ai/"));
        assertThat(LegacyApiPaths.DIFY).allMatch(path -> path.startsWith("/dify/serve/"));
        assertThat(LegacyApiPaths.LEGACY_NURSING_ALIASES).allMatch(path -> path.startsWith("/elder/"));

        assertThat(LegacyApiPaths.NURSING).contains(
                "/nursing/elder/list",
                "/nursing/checkIn/apply",
                "/nursing/nursingTask/do");
        assertThat(LegacyApiPaths.MEMBER).contains(
                "/member/user/login",
                "/member/user/wx-login",
                "/member/user/wx-bind");
    }

    @Test
    void nursingModuleIsReachableFromApplicationScanPath() {
        SpringBootApplication application = ZyyylApplication.class
                .getAnnotation(SpringBootApplication.class);

        assertThat(application).isNotNull();
        assertThat(application.scanBasePackages()).contains("com.zyyyl");
        assertThat(LegacyApiPaths.ALL).contains("/nursing/checkIn/apply", "/member/user/wx-login", "/ai/chat");
    }

    @Test
    void legacyFloorRoomAndBedAliasesRemainMapped() {
        assertThat(controllerMappings(FloorController.class)).contains("/nursing/floor", "/elder/floor");
        assertThat(controllerMappings(RoomController.class)).contains("/nursing/room", "/elder/room");
        assertThat(controllerMappings(RoomTypeController.class)).contains("/nursing/roomType", "/elder/roomType");
        assertThat(controllerMappings(BedController.class)).contains("/nursing/bed", "/elder/bed");
        assertThat(controllerMappings(ElderController.class)).contains("/nursing/elder");
    }

    private static Set<String> collectRoutes(Class<?> controller) {
        Set<String> routes = new LinkedHashSet<>();
        for (Method method : controller.getDeclaredMethods()) {
            addPaths(routes, method.getAnnotation(GetMapping.class));
            addPaths(routes, method.getAnnotation(PostMapping.class));
        }
        return routes;
    }

    private static void addPaths(Set<String> routes, GetMapping mapping) {
        if (mapping != null) {
            addPaths(routes, mapping.value());
        }
    }

    private static void addPaths(Set<String> routes, PostMapping mapping) {
        if (mapping != null) {
            addPaths(routes, mapping.value());
        }
    }

    private static void addPaths(Set<String> routes, String[] paths) {
        for (String path : paths) {
            routes.add(path.startsWith("/") ? path : "/" + path);
        }
    }

    private static Set<String> controllerMappings(Class<?> controller) {
        Set<String> mappings = new LinkedHashSet<>();
        org.springframework.web.bind.annotation.RequestMapping mapping =
                controller.getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);
        if (mapping != null) {
            addPaths(mappings, mapping.value());
        }
        return mappings;
    }
}

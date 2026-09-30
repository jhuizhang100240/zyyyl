package com.zyyyl.nursing.contract;

import java.util.List;
import java.util.stream.Stream;

/**
 * Frozen legacy API paths that the nursing migration must preserve.
 */
public final class LegacyApiPaths {

    private LegacyApiPaths() {
    }

    public static final List<String> SYSTEM = List.of(
            "/login",
            "/getInfo",
            "/getRouters",
            "/logout",
            "/system/user/list",
            "/system/user",
            "/system/user/{ids}",
            "/system/role/list",
            "/system/menu/list",
            "/system/dept/list",
            "/system/dict/data/list",
            "/system/config/list",
            "/monitor/logininfor/list",
            "/monitor/operlog/list",
            "/monitor/server");

    public static final List<String> NURSING = List.of(
            "/nursing/elder/list",
            "/nursing/elder/{id}",
            "/nursing/elder",
            "/nursing/elder/{ids}",
            "/nursing/elder/export",
            "/nursing/floor/list",
            "/nursing/floor",
            "/nursing/floor/{ids}",
            "/nursing/room/list",
            "/nursing/roomType/list",
            "/nursing/bed/list",
            "/nursing/bed",
            "/nursing/checkIn/detail/{id}",
            "/nursing/checkIn/apply",
            "/nursing/checkIn/list",
            "/nursing/checkIn/{id}",
            "/nursing/checkIn",
            "/nursing/checkIn/{ids}",
            "/nursing/checkIn/export",
            "/nursing/contract/list",
            "/nursing/contract/{id}",
            "/nursing/contract",
            "/nursing/contract/{ids}",
            "/nursing/healthAssessment/list",
            "/nursing/healthAssessment/{id}",
            "/nursing/healthAssessment",
            "/nursing/healthAssessment/{ids}",
            "/nursing/nursingLevel/list",
            "/nursing/nursingLevel",
            "/nursing/project/list",
            "/nursing/project",
            "/nursing/nursingPlan/list",
            "/nursing/nursingPlan/{id}",
            "/nursing/nursingTask/list",
            "/nursing/nursingTask/{id}",
            "/nursing/nursingTask/do",
            "/nursing/nursingTask/updateTime",
            "/nursing/nursingTask/cancel",
            "/nursing/nursingTask",
            "/nursing/nursingTask/{ids}",
            "/nursing/reservation/list",
            "/nursing/reservation/{id}",
            "/nursing/reservation",
            "/nursing/reservation/{ids}");

    public static final List<String> MEMBER = List.of(
            "/member/user/login",
            "/member/user/wx-login",
            "/member/user/wx-bind",
            "/member/user/add",
            "/member/user/my",
            "/member/user/list-by-page",
            "/member/user/deleteById",
            "/member/roomTypes",
            "/member/orders/project/{id}",
            "/member/orders",
            "/member/reservation",
            "/member/reservation/page",
            "/member/reservation/countByTime",
            "/member/reservation/cancelled-count",
            "/member/reservation/{id}/cancel");

    public static final List<String> AI = List.of(
            "/ai/chat",
            "/ai/history",
            "/ai/history/{chatId}");

    public static final List<String> DIFY = List.of(
            "/dify/serve/getElderBasicInfo",
            "/dify/serve/getElderHealthInfo",
            "/dify/serve/getReservationByToday");

    public static final List<String> ALL = Stream.of(SYSTEM, NURSING, MEMBER, AI, DIFY)
            .flatMap(List::stream)
            .toList();
}

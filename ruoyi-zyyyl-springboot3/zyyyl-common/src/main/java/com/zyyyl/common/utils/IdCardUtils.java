package com.zyyyl.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 身份证号解析工具。
 */
public final class IdCardUtils {

    private static final DateTimeFormatter BIRTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private IdCardUtils() {
    }

    public static LocalDateTime getBirthDate(String idCard) {
        validate(idCard);
        String value = idCard.length() == 18 ? idCard.substring(6, 14) : "19" + idCard.substring(6, 12);
        try {
            return LocalDate.parse(value, BIRTH_FORMATTER).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("无效的出生日期格式: " + value);
        }
    }

    public static Integer getAge(String idCard) {
        return Period.between(getBirthDate(idCard).toLocalDate(), LocalDate.now()).getYears();
    }

    /**
     * @return 0:男, 1:女
     */
    public static Integer getGender(String idCard) {
        validate(idCard);
        char genderCode = idCard.length() == 18 ? idCard.charAt(16) : idCard.charAt(14);
        return Character.getNumericValue(genderCode) % 2 == 1 ? 0 : 1;
    }

    private static void validate(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        int length = idCard.length();
        if (length != 15 && length != 18) {
            throw new IllegalArgumentException("无效的身份证号长度");
        }
        if (length == 18) {
            if (!idCard.substring(0, 17).matches("\\d+")
                    || (!Character.isDigit(idCard.charAt(17)) && idCard.charAt(17) != 'X')) {
                throw new IllegalArgumentException("无效的18位身份证格式");
            }
        } else if (!idCard.matches("\\d+")) {
            throw new IllegalArgumentException("无效的15位身份证格式");
        }
    }
}

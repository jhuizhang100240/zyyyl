package com.zyyyl.nursing.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.zyyyl.common.constant.CacheConstants;
import com.zyyyl.common.utils.CacheUtils;

/**
 * 微信绑定一次性票据存储。抽出独立组件便于单元测试替换。
 */
@Component
public class MemberBindTicketStore {

    public void save(String ticket, String openId, long expireMinutes) {
        CacheUtils.put(CacheConstants.MEMBER_BIND_TICKET_KEY, ticket, openId, expireMinutes, TimeUnit.MINUTES);
    }

    public String getOpenId(String ticket) {
        return CacheUtils.get(CacheConstants.MEMBER_BIND_TICKET_KEY, ticket, String.class);
    }

    public void remove(String ticket) {
        CacheUtils.removeIfPresent(CacheConstants.MEMBER_BIND_TICKET_KEY, ticket);
    }
}

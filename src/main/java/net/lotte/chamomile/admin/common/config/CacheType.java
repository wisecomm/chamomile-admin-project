package net.lotte.chamomile.admin.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {
    COMMON_LEFT_MENU("commonLeftMenu", 24 * 60 * 60,10000),
    COMMON_CODE("commonCode", 24 * 60 * 60, 10000),
    MESSAGE("message", 24 * 60 * 60, 10000);

    private final String cacheName;     // 캐시 이름
    private final int expiredAfterWrite; // 만료시간
    private final int maximumSize;      // 최대 갯수
}

package net.lotte.chamomile.admin.common.cache;

import com.hazelcast.core.HazelcastInstance;

import lombok.RequiredArgsConstructor;

import net.lotte.chamomile.admin.common.config.CacheType;

@RequiredArgsConstructor
public class HazelCastAdminCacheManager implements AdminCacheManager {

    private final HazelcastInstance hazelcastInstance;

    public void deleteAll() {
        hazelcastInstance.getMap(CacheType.COMMON_CODE.getCacheName()).clear();
        hazelcastInstance.getMap(CacheType.MESSAGE.getCacheName()).clear();
        hazelcastInstance.getMap(CacheType.COMMON_LEFT_MENU.getCacheName()).clear();
    }
}

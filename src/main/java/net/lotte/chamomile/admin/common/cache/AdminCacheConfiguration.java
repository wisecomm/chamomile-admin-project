package net.lotte.chamomile.admin.common.cache;

import com.hazelcast.core.HazelcastInstance;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminCacheConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "chmm.cache", name = "instance", havingValue = "hazelcast")
    public AdminCacheManager adminCacheManager(HazelcastInstance hazelcastInstance) {
        return new HazelCastAdminCacheManager(hazelcastInstance);
    }

    // @Bean
    // @ConditionalOnProperty(prefix = "chmm.cache", name = "instance", havingValue = "redis")
    // public AdminCacheManager adminCacheManager() {
    //     return new RedisAdminCacheManager();
    // }
}

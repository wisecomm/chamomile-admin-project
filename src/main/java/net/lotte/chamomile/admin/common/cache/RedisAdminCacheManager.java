// package net.lotte.chamomile.admin.common.cache;// package net.lotte.chamomile.admin.common.cache;
//
// import java.util.Set;
//
// import lombok.RequiredArgsConstructor;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate;
//
// import net.lotte.chamomile.admin.common.config.CacheType;
//
// @RequiredArgsConstructor
// public class RedisAdminCacheManager implements AdminCacheManager {
//
//     @Autowired
//     private RedisTemplate<String, Object> redisTemplate;
//
//     public void deleteAll() {
//
//         Set<String> keys = redisTemplate.keys(CacheType.COMMON_CODE.getCacheName() + "::*");
//         if (keys != null && !keys.isEmpty()) {
//             redisTemplate.delete(keys);
//         }
//
//         keys = redisTemplate.keys(CacheType.MESSAGE.getCacheName() + "::*");
//         if (keys != null && !keys.isEmpty()) {
//             redisTemplate.delete(keys);
//         }
//
//         keys = redisTemplate.keys(CacheType.COMMON_LEFT_MENU.getCacheName() + "::*");
//         if (keys != null && !keys.isEmpty()) {
//             redisTemplate.delete(keys);
//         }
//     }
// }

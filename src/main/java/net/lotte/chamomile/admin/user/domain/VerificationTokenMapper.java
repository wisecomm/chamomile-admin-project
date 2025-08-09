package net.lotte.chamomile.admin.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class VerificationTokenMapper {

    @Autowired
    private CacheManager cacheManager;

    public VerificationToken findByToken(String token) {
        return cacheManager.getCache("verificationTokens").get(token, VerificationToken.class);
    }

    public void save(String token, VerificationToken verificationToken) {
        cacheManager.getCache("verificationTokens").put(token, verificationToken);
    }

    public void delete(String token) {
        cacheManager.getCache("verificationTokens").evict(token);
    }
}

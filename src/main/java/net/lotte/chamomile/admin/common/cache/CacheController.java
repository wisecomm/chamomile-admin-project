package net.lotte.chamomile.admin.common.cache;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.common.cache.AdminCacheManager;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chmm/cache")
public class CacheController {

    private final AdminCacheManager cacheManager;

    @GetMapping("/clear")
    public ChamomileResponse<Void> cacheClear() {
        cacheManager.deleteAll();
        return new ChamomileResponse<>();
    }
}

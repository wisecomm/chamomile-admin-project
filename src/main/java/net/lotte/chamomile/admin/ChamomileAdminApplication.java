package net.lotte.chamomile.admin;

import org.springframework.cache.annotation.EnableCaching;

import net.lotte.chamomile.boot.ChamomileApplication;
import net.lotte.chamomile.boot.ChamomileBootApplication;

@ChamomileBootApplication
@EnableCaching
public class ChamomileAdminApplication {
    public static void main(String[] args) {
        ChamomileApplication.run(ChamomileAdminApplication.class, args);
    }
}

package net.lotte.chamomile.admin.transaction;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping(path = "/test")
    public String test() {
        testService.test();
        return "hello";
    }
}

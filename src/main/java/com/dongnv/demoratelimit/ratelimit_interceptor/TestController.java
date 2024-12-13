package com.dongnv.demoratelimit.ratelimit_interceptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interceptor-strategy")
public class TestController {
    @GetMapping
    String testInterceptorRateLimiting() {
        return "Success Test Interceptor Rate Limiting";
    }
}

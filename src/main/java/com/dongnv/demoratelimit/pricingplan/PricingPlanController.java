package com.dongnv.demoratelimit.pricingplan;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pricing-plan")
public class PricingPlanController {
    @Autowired
    PricingPlanService pricingPlanService;

    @GetMapping
    public ResponseEntity<String> pricingPlanTest(@RequestHeader(value = "X-api-key", defaultValue = "") String apiKey) {
        Bucket bucket = pricingPlanService.resolveBucket(apiKey);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return ResponseEntity.ok().header("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()))
                    .body("Success");
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(probe.getNanosToWaitForRefill() / 1000000000))
                .body("Failed");
    }
}

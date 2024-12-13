package com.dongnv.demoratelimit.naiveratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/naive")
public class NaiveRateLimitController {
    private final Bucket bucket;

    public NaiveRateLimitController() {
        Bandwidth limit = Bandwidth
                .builder()
                .capacity(10)
                .refillIntervally(10, Duration.ofMinutes(1))
                .build();

        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping
    public ResponseEntity<String> naiveTest() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Failed");  // HTTP Code 429
    }


}

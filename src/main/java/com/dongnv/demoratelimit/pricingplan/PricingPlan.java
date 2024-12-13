package com.dongnv.demoratelimit.pricingplan;

import io.github.bucket4j.Bandwidth;

import java.time.Duration;

// Các plan về rate limit api cho các loại người dùng
public enum PricingPlan {
    FREE {
        @Override
        Bandwidth getLimit() {
            return Bandwidth.builder()
                    .capacity(20)
                    .refillIntervally(20, Duration.ofHours(1))
                    .build();
        }
    },
    BASIC {
        @Override
        Bandwidth getLimit() {
            return Bandwidth.builder()
                    .capacity(40)
                    .refillIntervally(40, Duration.ofHours(1))
                    .build();
        }
    },
    PROFESSIONAL {
        @Override
        Bandwidth getLimit() {
            return Bandwidth.builder()
                    .capacity(100)
                    .refillIntervally(100, Duration.ofHours(1))
                    .build();
        }
    };

    static PricingPlan resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return FREE;
        } else if (apiKey.startsWith("PX001-")) {
            return PROFESSIONAL;
        } else if (apiKey.startsWith("BX001-")) {
            return BASIC;
        }
        return FREE;
    }
    abstract Bandwidth getLimit();
}

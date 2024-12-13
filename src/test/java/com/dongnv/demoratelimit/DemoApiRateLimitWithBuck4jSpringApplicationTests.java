package com.dongnv.demoratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DemoApiRateLimitWithBuck4jSpringApplicationTests {

	@Test
	void test1() {
		Bandwidth limit = BandwidthBuilder
				.builder()
				.capacity(10)
				.refillIntervally(10, Duration.ofMinutes(1))
				.build();

		Bucket bucket = Bucket
				.builder()
				.addLimit(limit)
				.build();

		for (int i = 1; i <= 10; i++) {
			assert bucket.tryConsume(1);
		}

		assert !bucket.tryConsume(1);
	}
}

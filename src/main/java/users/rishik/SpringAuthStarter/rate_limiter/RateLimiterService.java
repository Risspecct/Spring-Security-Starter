package users.rishik.SpringAuthStarter.rate_limiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {
    private static final long LOGIN_LIMIT_CAPACITY = 5;
    private static final Duration LOGIN_LIMIT_WINDOW = Duration.ofMinutes(5);

    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key) {
        return bucketCache.computeIfAbsent(key, this::newBucket);
    }

    private Bucket newBucket(String key) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(LOGIN_LIMIT_CAPACITY)
                .refillGreedy(LOGIN_LIMIT_CAPACITY, LOGIN_LIMIT_WINDOW)
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    public ConsumptionProbe consumeLoginAttempt(String key) {
        return resolveBucket(key).tryConsumeAndReturnRemaining(1);
    }

    public long getLoginLimitCapacity() {
        return LOGIN_LIMIT_CAPACITY;
    }

    public long getRemainingAttempts(String key) {
        ConsumptionProbe probe = resolveBucket(key).tryConsumeAndReturnRemaining(0);
        return probe.getRemainingTokens();
    }

    public long getUsedAttempts(String key) {
        return LOGIN_LIMIT_CAPACITY - getRemainingAttempts(key);
    }
}

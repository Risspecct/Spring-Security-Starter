package users.rishik.SpringAuthStarter.rate_limiter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateLimitStatusDto {
    private String endpoint;
    private long limit;
    private long used;
    private long remaining;
}

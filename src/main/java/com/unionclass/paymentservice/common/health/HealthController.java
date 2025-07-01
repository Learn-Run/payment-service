package com.unionclass.paymentservice.common.health;

import com.unionclass.paymentservice.common.response.BaseResponseEntity;
import com.unionclass.paymentservice.common.response.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/actuator")
@Tag(name = "health")
public class HealthController {

    @Operation(
            summary = "서비스 상태 확인",
            description = """
                    결제 서비스의 상태를 확인하는 API 입니다.
                    
                    [응답 필드]
                    - status : 서비스 상태 (UP/DOWN)
                    - timestamp : 응답 시간
                    """
    )
    @GetMapping("/health")
    public BaseResponseEntity<HealthResponse> health() {

        log.info("헬스체크 요청 수신");

        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS.getMessage(),
                HealthResponse.of("UP", System.currentTimeMillis())
        );
    }

    public static class HealthResponse {
        private final String status;
        private final long timestamp;

        public HealthResponse(String status, long timestamp) {
            this.status = status;
            this.timestamp = timestamp;
        }

        public static HealthResponse of(String status, long timestamp) {
            return new HealthResponse(status, timestamp);
        }

        public String getStatus() {
            return status;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
} 
package com.unionclass.paymentservice.common.kafka.util;

import com.unionclass.paymentservice.common.kafka.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topics.payment-created}")
    private String paymentCreatedTopic;

    private final KafkaTemplate<String, PaymentCreatedEvent> paymentCreatedEventKafkaTemplate;

    public void sendPaymentCreatedEvent(PaymentCreatedEvent paymentCreatedEvent) {

        log.info("Kafka 메시지 전송 시작: {}", paymentCreatedEvent);

        CompletableFuture<SendResult<String, PaymentCreatedEvent>> future
                = paymentCreatedEventKafkaTemplate.send(paymentCreatedTopic, paymentCreatedEvent);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Kafka 메시지 전송 실패: {}", paymentCreatedEvent, ex);
            } else {
                log.info("Kafka 메시지 전송 성공: offset={}, topic={}",
                        result.getRecordMetadata().offset(), result.getRecordMetadata().topic());
            }
        });
    }
}

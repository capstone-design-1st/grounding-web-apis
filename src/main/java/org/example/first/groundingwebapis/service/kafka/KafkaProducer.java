package org.example.first.groundingwebapis.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.first.groundingwebapis.dto.TransferToAppDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
/*
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }

}
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void send(String topic, String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }

    public void sendTransferToApp(String topic, TransferToAppDto transferToAppDto) {
        try {
            String payload = objectMapper.writeValueAsString(transferToAppDto);
            log.info("sending TransferToAppDto payload='{}' to topic='{}'", payload, topic);
            kafkaTemplate.send(topic, payload);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert TransferToAppDto to JSON", e);
        }
    }

    public void sendApprove(TransferToAppDto transferToAppDto) {
        // 승인 로직을 처리합니다.
        // 예: 데이터베이스에 승인 상태 업데이트 등

        // 승인 후 카프카 메시지 전송
        sendTransferToApp("create-property", transferToAppDto);
    }
}
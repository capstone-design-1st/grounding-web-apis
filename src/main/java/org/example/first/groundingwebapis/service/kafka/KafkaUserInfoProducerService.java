package org.example.first.groundingwebapis.service.kafka;


import org.example.first.groundingwebapis.dto.KafkaDto;

public interface KafkaUserInfoProducerService {
    void createUser(KafkaDto.UserInfo userInfo);
}

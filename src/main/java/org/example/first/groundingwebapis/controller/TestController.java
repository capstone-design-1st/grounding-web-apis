package org.example.first.groundingwebapis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.first.groundingwebapis.dto.KafkaDto;
import org.example.first.groundingwebapis.service.kafka.KafkaUserInfoProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class TestController {
    private final KafkaUserInfoProducerService kafkaUserInfoProducerService;

    @GetMapping("/test")
    public void test() {
        kafkaUserInfoProducerService.createUser(KafkaDto.UserInfo.builder()
                .name("test")
                .email("ben7232@gmail.com").build());
    }
}

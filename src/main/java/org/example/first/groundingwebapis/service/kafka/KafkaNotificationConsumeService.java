package org.example.first.groundingwebapis.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.first.groundingwebapis.dto.KafkaDto;
import org.example.first.groundingwebapis.entity.Notification;
import org.example.first.groundingwebapis.entity.PieceInvestment;
import org.example.first.groundingwebapis.entity.PieceInvestmentInfo;
import org.example.first.groundingwebapis.repository.NotificationRepository;
import org.example.first.groundingwebapis.repository.PieceInvestmentInfoRepository;
import org.example.first.groundingwebapis.repository.PieceInvestmentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaNotificationConsumeService {
    private final ObjectMapper objectMapper;
    private final NotificationRepository notificationRepository;
    private final PieceInvestmentRepository pieceInvestmentRepository;

    @KafkaListener(topics = "create-notification")
    @Transactional
    public void createNotification(@Payload String payload, Acknowledgment acknowledgment) {
        log.info("received payload='{}'", payload);

        KafkaDto.NotificationConsumeDto notificationConsumeDto = null;
        try {
            notificationConsumeDto = objectMapper.readValue(payload, KafkaDto.NotificationConsumeDto.class);

            String pieceInvestmentName = notificationConsumeDto.getPieceInvestmentName();

            // 투자 상품 이름으로 투자 상품 정보 조회
            PieceInvestment pieceInvestment = pieceInvestmentRepository.findByAssetName(pieceInvestmentName)
                    .orElseThrow(() -> new IllegalArgumentException("Piece investment not found. name: " + pieceInvestmentName));

            Notification notification = Notification.builder()
                    .userName(notificationConsumeDto.getUserName())
                    .quantity(notificationConsumeDto.getQuantity())
                    .progressRate(notificationConsumeDto.getProgressRate())
                    .notificationTime(notificationConsumeDto.getExecutedTime())
                    .build();
            notification.updatePieceInvestment(pieceInvestment);

            notificationRepository.save(notification);

            log.info("notification created. userName: {}, quantity: {}, progressRate: {}, executedTime: {}", notification.getUserName(), notification.getQuantity(), notification.getProgressRate(), notification.getNotificationTime());
        } catch (Exception e) {
            log.error("Error while converting json string to user object", e);
        }

        acknowledgment.acknowledge();
    }

}

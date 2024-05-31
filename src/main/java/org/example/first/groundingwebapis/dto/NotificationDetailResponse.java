package org.example.first.groundingwebapis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDetailResponse {
    private NotificationDetailSubResponse alarm;
}

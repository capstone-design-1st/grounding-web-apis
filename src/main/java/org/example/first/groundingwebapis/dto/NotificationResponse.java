package org.example.first.groundingwebapis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private List<NotificationSubResponse> estate_alarms;
    private List<NotificationSubResponse> land_alarms;
}

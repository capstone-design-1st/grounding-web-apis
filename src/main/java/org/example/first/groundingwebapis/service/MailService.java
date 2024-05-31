package org.example.first.groundingwebapis.service;

import org.example.first.groundingwebapis.dto.MailDto;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(MailDto.MailSendDto mailSendDto);
}

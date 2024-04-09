package com.example.demo.services;

import com.example.demo.dto.EmailDetails;

public interface EmailService {

  void sendMail(EmailDetails details);

  void sendMailWithAttachment(EmailDetails details);


}

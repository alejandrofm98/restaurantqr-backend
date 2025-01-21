package com.example.demo.features.email;

public interface EmailService {

  void sendMail(EmailDetails details);

  void sendMailWithAttachment(EmailDetails details);


}

package com.example.demo.services.impl;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.EmailDetails;
import com.example.demo.exception.Exceptions;
import com.example.demo.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private String sender;

  @Override
  public void sendMail(EmailDetails details) {
    try {

      // Creating a simple mail message
      SimpleMailMessage mailMessage
          = new SimpleMailMessage();

      // Setting up necessary details
      mailMessage.setFrom(sender);
      mailMessage.setTo(details.getRecipient());
      mailMessage.setText(details.getMsgBody());
      mailMessage.setSubject(details.getSubject());

      // Sending the mail
      javaMailSender.send(mailMessage);
      Log4j2Config.logMethod("sendMail()",
          "Mail Sent Succesfully from " + sender + " to " + details.getRecipient()
              + " with Subject: " + details.getSubject());
    }

    // Catch block to handle the exceptions
    catch (Exception e) {
      String message = "Failure sending mail from " + sender + " to " + details.getRecipient()
          + " with Subject: " + details.getSubject();
      Log4j2Config.logMethod("sendMail()", message, e.getMessage());
      throw new Exceptions(message);
    }
  }

  @Override
  public void sendMailWithAttachment(EmailDetails details) {
    // Creating a mime message
    MimeMessage mimeMessage
        = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper;

    try {

      // Setting multipart as true for attachments to
      // be send
      mimeMessageHelper
          = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setFrom(sender);
      mimeMessageHelper.setTo(details.getRecipient());
      mimeMessageHelper.setText(details.getMsgBody());
      mimeMessageHelper.setSubject(
          details.getSubject());

      // Adding the attachment
      FileSystemResource file
          = new FileSystemResource(
          new File(details.getAttachment()));

      mimeMessageHelper.addAttachment(
          Objects.requireNonNull(file.getFilename()), file);

      // Sending the mail
      javaMailSender.send(mimeMessage);

      Log4j2Config.logMethod("sendMailWithAttachment()",
          "Mail Sent Succesfully from " + sender + " to " + details.getRecipient()
              + " with Subject: " + details.getSubject());

    }

    // Catch block to handle MessagingException
    catch (MessagingException e) {
      String message = "Failure sending mail from " + sender + " to " + details.getRecipient()
          + " with Subject: " + details.getSubject();
      Log4j2Config.logMethod("sendMail()", message, e.getMessage());
      throw new Exceptions(message);
    }
  }

}

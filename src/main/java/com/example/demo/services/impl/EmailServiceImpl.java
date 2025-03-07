package com.example.demo.services.impl;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.response.EmailDetails;
import com.example.demo.exception.Exceptions;
import com.example.demo.services.EmailService;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
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

      MimeMessage message = javaMailSender.createMimeMessage();

      // Setting up necessary details
      message.setFrom(sender);
      message.setRecipients(RecipientType.TO,details.getRecipient());
      message.setContent(details.getMsgBody(),"text/html; charset=utf-8");
      message.setSubject(details.getSubject());


      // Sending the mail
      javaMailSender.send(message);
      Log4j2Config.logMethod("sendMail()",
          "Mail Sent Successfully from " + sender + " to " + details.getRecipient()
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
      mimeMessage.setContent(details.getMsgBody(),"text/html; charset=utf-8");
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
      Log4j2Config.logMethod("sendMailWithAttachment()", message, e.getMessage());
      throw new Exceptions(message);
    }
  }

}

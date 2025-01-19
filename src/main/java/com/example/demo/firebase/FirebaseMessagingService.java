package com.example.demo.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class FirebaseMessagingService {

  private final FirebaseMessaging firebaseMessaging;

  public String sendNotificationByToken(NotificationMessage notificationMessage)
      throws FirebaseMessagingException {
    Notification notification = Notification.builder().setTitle(notificationMessage.getTitle())
        .setBody(notificationMessage.getBody()).setImage(notificationMessage.getImage()).build();

    Message message = Message.builder()
        .setToken(notificationMessage.getRecipientToken())
        .setNotification(notification).putAllData(notificationMessage.getData())
        .build();

    try {
      firebaseMessaging.send(message);
      return "Success Sending Notification";
    } catch (FirebaseMessagingException e) {
      log.error("Error sending firebase notification:{}", e.getMessage());
      return "Error Sending Notification";
    }
  }
}

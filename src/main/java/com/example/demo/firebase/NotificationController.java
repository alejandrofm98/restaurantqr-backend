package com.example.demo.Firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.utils.Constants.CONSTANT_PUBLIC_URL;

@RestController
@RequestMapping(CONSTANT_PUBLIC_URL)
public class NotificationController {
    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @PostMapping(value = "notification")
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }
}

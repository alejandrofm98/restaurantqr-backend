package com.example.demo.firebase;

import com.example.demo.dto.ApiResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.utils.Constants.CONSTANT_PUBLIC_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(CONSTANT_PUBLIC_URL)
public class NotificationController {

    private final FirebaseMessagingService firebaseMessagingService;

    @PostMapping(value = "notification")
    public ResponseEntity<ApiResponse> sendNotificationByToken(@RequestBody NotificationMessage notificationMessage) throws FirebaseMessagingException {
        ApiResponse apiResponse = ApiResponse.builder()
            .response(firebaseMessagingService.sendNotificationByToken(notificationMessage))
            .build();
        return ResponseEntity.ok(apiResponse);
    }
}

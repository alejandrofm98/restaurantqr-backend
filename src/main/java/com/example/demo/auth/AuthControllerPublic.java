package com.example.demo.auth;


import static com.example.demo.utils.Constants.CONSTANT_GET;
import static com.example.demo.utils.Constants.CONSTANT_POST;
import static com.example.demo.utils.Constants.CONSTANT_PUBLIC_URL;
import static com.example.demo.utils.Constants.CONSTANT_SECURE_URL;
import static com.example.demo.utils.Constants.EMAIL_REGISTER_TEMPLATE;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.*;
import com.example.demo.entity.Business;
import com.example.demo.entity.Product;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.services.EmailService;
import com.example.demo.services.ProductService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CONSTANT_PUBLIC_URL)
@RequiredArgsConstructor
public class AuthControllerPublic {

  private final AuthService authService;
  private final ProductService productService;
  private final EmailService emailService;
  private final BusinessRepository businessRepository;

  @PostMapping(value = "login")
  public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
    AuthResponse authResponse = authService.login(request);

    String token = authResponse.getToken();

    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/login",
        token,
        request.toString()
    );
    ApiResponse apiResponse = ApiResponse.builder()
        .response(authResponse)
        .build();

    return ResponseEntity.ok(apiResponse);

  }


  @GetMapping("products")
  public ResponseEntity<ApiResponse> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_PUBLIC_URL + "/products",
        "All products displayed correctly",
        products.toString());
    ApiResponse apiResponse = ApiResponse.builder()
        .response(products)
        .build();
    return ResponseEntity.ok(apiResponse);
  }


  @GetMapping("/products/{businessUuid}")
  public ResponseEntity<ApiResponse> getProductsByBusinessUuid(@PathVariable String businessUuid) {
    List<Product> products = productService.getProductsByBusinessUuid(businessUuid);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_PUBLIC_URL + "/products/{businessUuid}",
        "Products for business displayed correctly",
        products.toString());
    ApiResponse apiResponse = ApiResponse.builder()
        .response(products)
        .build();
    return ResponseEntity.ok(apiResponse);
  }


  @PostMapping(value = "register")
  public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
    AuthResponse authResponse;
    authResponse = authService.register(request);

    String token = authResponse.getToken();
    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/register",
        token,
        request.toString()
    );

    // Lee el contenido del archivo HTML
    try {
      String htmlContent = new String(
          Objects.requireNonNull(getClass().getResourceAsStream(EMAIL_REGISTER_TEMPLATE))
              .readAllBytes());
      EmailDetails emailDetails = new EmailDetails(request.getEmail(),
          "Registro realizado correctamente", htmlContent, "");
      emailService.sendMail(emailDetails);

    } catch (IOException e) {
      Log4j2Config.logRequestError("Error finding registration template for email");
    }

    ApiResponse apiResponse = ApiResponse.builder()
        .response(authResponse)
        .build();
    return ResponseEntity.ok(apiResponse);

  }


  //Insert
  @PostMapping("/business")
  public ResponseEntity<ApiResponse> createBusiness(@RequestBody Business business) {
    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_SECURE_URL + "/business",
        "Successfully inserted business",
        business.toString());

    Business businessResult = businessRepository.save(business);

    ApiResponse apiResponse = ApiResponse.builder()
        .response(businessResult)
        .build();
    return ResponseEntity.ok(apiResponse);
  }


}




package com.example.demo.features.auth;


import static com.example.demo.common.utils.Constants.CONSTANT_GET;
import static com.example.demo.common.utils.Constants.CONSTANT_POST;
import static com.example.demo.common.utils.Constants.CONSTANT_PUBLIC_URL;
import static com.example.demo.common.utils.Constants.EMAIL_REGISTER_TEMPLATE;

import com.example.demo.common.config.Log4j2Config;
import com.example.demo.common.dto.ApiResponse;
import com.example.demo.features.email.EmailDetails;
import com.example.demo.features.product.Product;
import com.example.demo.features.business.BusinessRepository;
import com.example.demo.features.email.EmailService;
import com.example.demo.features.product.ProductService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  // TODO MOVER ESTE METODO A 1 URL QUE TENGA SENTIDO PARA QUE ESTEN FUERA DE /AUTH
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

  // TODO MOVER ESTE METODO A 1 URL QUE TENGA SENTIDO PARA QUE ESTEN FUERA DE /AUTH

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

  @PostMapping(value = "registerBusinessAndOwner")
  public ResponseEntity<ApiResponse> registerBusinessAndOwner(
      @RequestBody RegisterBusinessOwnerRequest request) {

    RegisterBusinessOwnerResponse response = authService.registerBusinessAndOwner(request);

    String token = response.getToken();
    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/registerBusinessAndOwner",
        token,
        request.toString()
    );

    // Lee el contenido del archivo HTML
    try {
      String htmlContent = new String(
          Objects.requireNonNull(getClass().getResourceAsStream(EMAIL_REGISTER_TEMPLATE))
              .readAllBytes());
      EmailDetails emailDetails = new EmailDetails(request.getUserRequest().getEmail(),
          "Registro realizado correctamente", htmlContent, "");
      emailService.sendMail(emailDetails);

    } catch (IOException e) {
      Log4j2Config.logRequestError("Error finding registration template for email");
    }

    ApiResponse apiResponse = ApiResponse.builder()
        .response(response)
        .build();
    return ResponseEntity.ok(apiResponse);

  }


}




package com.example.demo.auth;


import com.example.demo.config.Log4j2Config;
import com.example.demo.entity.Product;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping(CONSTANT_PUBLIC_URL)
@RequiredArgsConstructor
public class AuthControllerPublic {

    private final AuthService authService;
    private final ProductService productService;

    @PostMapping(value ="login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
            AuthResponse authResponse = authService.login(request);
            Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/login",
                    authResponse.getToken(),
                    request.toString()
            );
            return ResponseEntity.ok(authResponse);
    }



    @GetMapping("products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_PUBLIC_URL + "/products",
                "All products displayed correctly",
                products.toString());
        return ResponseEntity.ok(products);
    }


    @PostMapping(value ="register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse;
        authResponse = authService.register(request);
        Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/register",
                authResponse.getToken(),
                request.toString()
        );
        return ResponseEntity.ok(authResponse);
    }




}




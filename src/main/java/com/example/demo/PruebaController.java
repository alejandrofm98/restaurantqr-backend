package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {
  @GetMapping("/")
  public String hello(){
    return "funciona";
  }
}

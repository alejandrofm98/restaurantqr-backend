package com.example.demo.controller;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.entity.Business;
import com.example.demo.services.BussinesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping(CONSTANT_SECURE_URL)
@PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
@RequiredArgsConstructor
public class BusinessController {

  private final BussinesService bussinesService;

  @PutMapping("/business/{id}")
  public ResponseEntity<ApiResponse> updateBusiness(@PathVariable String id,
      @RequestBody Business updatedBusiness) {
    Business business = bussinesService.updateBusiness(id, updatedBusiness);
    Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_SECURE_URL + "/business/" + id,
        "Successfully updated business",
        business.toString());
    ApiResponse apiResponse = ApiResponse.builder()
        .response(business)
        .build();
    return ResponseEntity.ok(apiResponse);
  }

  @DeleteMapping("/business/{id}")
  public ResponseEntity<ApiResponse> deleteBusiness(@PathVariable String id) {
    bussinesService.deleteBusiness(id);

    Log4j2Config.logRequestInfo(CONSTANT_DELETE, CONSTANT_SECURE_URL + "/business/" + id,
        "Successfully deleted business", null);

    return ResponseEntity.noContent().build();
  }


}

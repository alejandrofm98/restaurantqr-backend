package com.example.demo.controller;

import com.example.demo.config.Log4j2Config;
import com.example.demo.entity.Business;
import com.example.demo.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping(CONSTANT_SECURE_URL)
@PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessRepository businessRepository;


    //Update
    @PutMapping("/business/{id}")
    public Business updateBusiness(@PathVariable String id, @RequestBody Business updatedBusiness) {

        return businessRepository.findById(id)
                .map(business -> {
                    business.setName(updatedBusiness.getName());
                    business.setAddress(updatedBusiness.getAddress());
                    business.setCountry(updatedBusiness.getCountry());
                    business.setDocumentNumber(updatedBusiness.getDocumentNumber());
                    business.setDocumentType(updatedBusiness.getDocumentType());
                    business.setIsActive(updatedBusiness.isActive());
                    business.setLenguajeIso2(updatedBusiness.getLenguajeIso2());
                    business.setLenguajeIso3(updatedBusiness.getLenguajeIso3());
                    business.setPostalCode(updatedBusiness.getPostalCode());
                    business.setProvince(updatedBusiness.getProvince());
                    business.setState(updatedBusiness.getState());
                    business.setTown(updatedBusiness.getTown());
                    business.setUpdatedAt(LocalDateTime.now());

                    Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_SECURE_URL + "/business/"+id,
                            "Successfully updated business",
                            business.toString());
                    return businessRepository.save(business);
                })
                .orElseThrow(() -> new RuntimeException("Business not found with id " + id));
    }
//    //Delete
    @DeleteMapping("/business/{id}")
    public Business deleteBusiness(@PathVariable String id) {
        Business businessToDelete = businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Business not found with id " + id));

        Log4j2Config.logRequestInfo(CONSTANT_DELETE, CONSTANT_SECURE_URL + "/business/"+id,
                "Successfully deleted business",
                businessToDelete.toString());

        businessRepository.deleteById(id);

        return businessToDelete;
    }


}

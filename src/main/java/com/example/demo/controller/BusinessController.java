package com.example.demo.controller;

import com.example.demo.config.Log4j2Config;
import com.example.demo.entity.Business;
import com.example.demo.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping("/api/v1")
public class BusinessController {

    @Autowired
    private BusinessRepository businessRepository;

    //Insert
    @PostMapping("/business")
    public Business createBusiness(@RequestBody Business business) {
        Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_SECURE_URL + "/business",
                "Successfully inserted business",
                business.toString());
        return businessRepository.save(business);
    }

    //Update
    @PutMapping("/business/{id}")
    public Business updateBusiness(@PathVariable Integer id, @RequestBody Business updatedBusiness) {

        return businessRepository.findById(id)
                .map(business -> {
                    business.setName(updatedBusiness.getName());
                    Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_SECURE_URL + "/business/"+id,
                            "Successfully updated business",
                            business.toString());
                    return businessRepository.save(business);
                })
                .orElseThrow(() -> new RuntimeException("Business not found with id " + id));
    }
    //Delete
    @DeleteMapping("/business/{id}")
    public Business deleteBusiness(@PathVariable Integer id) {
        Business businessToDelete = businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Business not found with id " + id));

        Log4j2Config.logRequestInfo(CONSTANT_DELETE, CONSTANT_SECURE_URL + "/business/"+id,
                "Successfully deleted business",
                businessToDelete.toString());

        businessRepository.deleteById(id);

        return businessToDelete;
    }


}

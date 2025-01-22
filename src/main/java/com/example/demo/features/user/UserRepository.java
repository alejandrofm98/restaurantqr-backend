package com.example.demo.features.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String username);
    List<User> findByBusiness_BusinessUuid(String bussinessUuid);
    Optional<User> findByIdAndBusiness_BusinessUuid(Integer id, String businessUuid);

}

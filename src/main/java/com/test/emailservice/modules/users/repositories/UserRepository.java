package com.test.emailservice.modules.users.repositories;

import com.test.emailservice.modules.users.dao.UserRequestValidationResult;
import com.test.emailservice.modules.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.mobileNumber=?1 or u.email = ?1")
    User findByMobileNumberOrEmail(String mobileNumber);

    @Query("SELECT u FROM User u WHERE u.id=?1")
    User findById(long id);

    @Procedure(name = "user.validate")
    UserRequestValidationResult getValidatedResult(@Param("requestData") String model);
}

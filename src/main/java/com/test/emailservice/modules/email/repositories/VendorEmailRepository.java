package com.test.emailservice.modules.email.repositories;


import com.test.emailservice.modules.email.entities.VendorEmail;
import com.test.emailservice.modules.users.dao.UserRequestValidationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface VendorEmailRepository extends JpaRepository<VendorEmail, Long>, JpaSpecificationExecutor<VendorEmail> {
}

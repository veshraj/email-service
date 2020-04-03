package com.test.emailservice.modules.email.repositories;

import com.test.emailservice.modules.email.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
}

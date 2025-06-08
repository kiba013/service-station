package com.example.servicestation.repository;

import com.example.servicestation.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends
        JpaRepository<Request, Long>,
        JpaSpecificationExecutor<Request> {
}

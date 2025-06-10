package com.example.servicestation.repository;

import com.example.servicestation.domain.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusHistoryRepository extends
        JpaRepository<StatusHistory, Long>,
        JpaSpecificationExecutor<StatusHistory> {
    List<StatusHistory> findAllByRequestId(Long requestId);
}

package com.nexign.billing_real_time_app_service.repository;

import com.nexign.billing_real_time_app_service.entity.Caller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CallerRepository extends JpaRepository<Caller, Long> {

    List<Caller> findCallersByMsisdnIn(Set<String> msisdnSet);

}

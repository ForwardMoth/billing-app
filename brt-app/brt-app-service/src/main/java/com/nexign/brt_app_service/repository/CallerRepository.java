package com.nexign.brt_app_service.repository;

import com.nexign.brt_app_service.entity.Caller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CallerRepository extends JpaRepository<Caller, Long> {

    List<Caller> findCallersByMsisdnIn(Set<String> msisdnSet);

    Optional<Caller> findByUuidAndActualIsTrue(UUID uuid);

    Optional<Caller> findByUuid(UUID uuid);

    Boolean existsByMsisdn(String msisdn);

}

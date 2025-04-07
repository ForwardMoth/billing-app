package com.nexign.cdr_generator_app_service.repository;


import com.nexign.cdr_generator_app_service.entity.Caller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallerRepository extends JpaRepository<Caller, Long> {

}

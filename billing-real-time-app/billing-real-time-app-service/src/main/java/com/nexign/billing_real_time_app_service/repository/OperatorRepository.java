package com.nexign.billing_real_time_app_service.repository;

import com.nexign.billing_real_time_app_service.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    @Query(value = "SELECT * FROM operators op WHERE name != 'Ромашка'", nativeQuery = true)
    List<Operator> findByNameNotRomashka();

}

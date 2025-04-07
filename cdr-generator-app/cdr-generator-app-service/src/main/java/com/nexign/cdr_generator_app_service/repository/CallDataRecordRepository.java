package com.nexign.cdr_generator_app_service.repository;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallDataRecordRepository extends JpaRepository<CallDataRecord, Long> {

}

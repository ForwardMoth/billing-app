package com.nexign.cdr_generator_app_service.repository;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CallDataRecordRepository extends JpaRepository<CallDataRecord, Long> {

    List<CallDataRecord> findFirst10ByIsSentFalseOrderByStartCallTime();

    @Query(value = "SELECT cdr.finish_call_time FROM call_data_records cdr ORDER BY finish_call_time DESC LIMIT 1",
            nativeQuery = true)
    Optional<LocalDateTime> findLastCallDataRecordTime();


}

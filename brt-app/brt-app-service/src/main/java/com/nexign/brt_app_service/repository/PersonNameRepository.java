package com.nexign.brt_app_service.repository;

import com.nexign.brt_app_service.entity.PersonName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonNameRepository extends JpaRepository<PersonName, Long> {

    Optional<PersonName> findBySurnameAndNameAndPatronymic(String surname, String name, String patronymic);

}

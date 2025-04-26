package com.nexign.brt_app_service.service.impl;

import com.nexign.brt_app_service.entity.PersonName;
import com.nexign.brt_app_service.mapper.PersonNameMapper;
import com.nexign.brt_app_service.repository.PersonNameRepository;
import com.nexign.brt_app_service.service.PersonNameService;
import com.nexign.lib_brt_api.dto.PersonNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonNameServiceImpl implements PersonNameService {

    private final PersonNameRepository personNameRepository;
    private final PersonNameMapper personNameMapper;

    @Override
    @Transactional
    public PersonName createOrFindPersonName(PersonNameDto personNameDto) {
        final var surname = personNameDto.getSurname();
        final var name = personNameDto.getName();
        final var patronymic = personNameDto.getPatronymic();
        return personNameRepository.findBySurnameAndNameAndPatronymic(surname, name, patronymic)
                .orElseGet(() -> personNameRepository.saveAndFlush(personNameMapper.map(personNameDto)));
    }

}

package com.nexign.brt_app_service.service;

import com.nexign.brt_app_service.entity.PersonName;
import com.nexign.lib_brt_api.dto.PersonNameDto;

public interface PersonNameService {

    PersonName createOrFindPersonName(PersonNameDto personNameDto);

}

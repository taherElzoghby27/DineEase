package com.spring.boot.resturantbackend.services.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;

import java.util.List;

public interface ContactInfoService {
    List<ContactInfoDto> allContactInfos(String filter);

    ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto);

    ContactInfoDto updateStatus(FilterContactInfo filterContactInfo, Long id);

    ContactInfoDto getContactInfo(Long id);

    ContactInfoDto getContactInfoByIdAndAccountId(Long id, Long accountId);

}

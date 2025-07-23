package com.spring.boot.resturantbackend.services.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;

import java.util.List;

public interface ContactInfoService {
    List<ContactInfoDto> allContactInfos(String filter);
//
//    List<ContactInfoDto> allContactInfosForSpecificAccount(String filter);

    ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto);

}

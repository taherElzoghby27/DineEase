package com.spring.boot.resturantbackend.services;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;

import java.util.List;

public interface ContactInfoService {
    List<ContactInfoDto> allContactInfos(String filter);

    ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto);

}

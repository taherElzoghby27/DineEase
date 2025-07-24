package com.spring.boot.resturantbackend.services.contact_info.strategies;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;

import java.util.List;

public interface ContactInfoRetrievalStrategy {
    List<ContactInfoDto> getContactInfo(String filter);
}

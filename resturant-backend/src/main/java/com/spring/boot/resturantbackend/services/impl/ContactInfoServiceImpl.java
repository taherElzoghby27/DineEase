package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.mappers.ContactInfoMapper;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.repositories.ContactInfoRepo;
import com.spring.boot.resturantbackend.services.ContactInfoService;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    @Autowired
    private ContactInfoRepo contactInfoRepo;

    @Override
    public List<ContactInfoDto> allContactInfos(String filter) {
        try {
            FilterContactInfo filterContactInfo = FilterContactInfo.valueOf(filter);
            Optional<List<ContactInfo>> contactsInfo = contactInfoRepo.findContactInfosByStatus(filterContactInfo);
            if (contactsInfo.isEmpty()) {
                throw new SystemException("empty_contact_info");
            }
            return contactsInfo.get().stream().map(ContactInfoMapper.CONTACT_INFO_MAPPER::toContactInfoDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ContactInfoDto> allContactInfosForSpecificAccount(String filter) {
        try {
            AccountDto accountDto = SecurityUtils.getCurrentAccount();
            FilterContactInfo filterContactInfo = FilterContactInfo.valueOf(filter);
            Optional<List<ContactInfo>> contactsInfo = contactInfoRepo.findContactInfosByIdAndStatus(
                    accountDto.getId(),
                    filterContactInfo
            );
            if (contactsInfo.isEmpty()) {
                throw new SystemException("empty_contact_info");
            }
            return contactsInfo.get().stream().map(ContactInfoMapper.CONTACT_INFO_MAPPER::toContactInfoDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto) {
        try {
            if (Objects.nonNull(contactInfoDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            ContactInfo contactInfo = ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfo(contactInfoDto);
            contactInfo = contactInfoRepo.save(contactInfo);
            return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

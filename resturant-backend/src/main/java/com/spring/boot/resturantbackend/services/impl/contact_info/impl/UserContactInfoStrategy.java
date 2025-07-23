package com.spring.boot.resturantbackend.services.impl.contact_info.impl;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.mappers.ContactInfoMapper;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.repositories.ContactInfoRepo;
import com.spring.boot.resturantbackend.services.contact_info.ContactInfoRetrievalStrategy;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("userServiceContactInfo")
public class UserContactInfoStrategy implements ContactInfoRetrievalStrategy {
    @Autowired
    private ContactInfoRepo contactInfoRepo;

    @Override
    public List<ContactInfoDto> getContactInfo(String filter) {
        try {
            Optional<List<ContactInfo>> contactsInfo;
            AccountDto accountDto = SecurityUtils.getCurrentAccount();
            if (Objects.isNull(filter)) {
                contactsInfo = contactInfoRepo.findContactInfosByAccountId(accountDto.getId());
            } else {
                FilterContactInfo filterContactInfo = FilterContactInfo.valueOf(filter);
                contactsInfo = contactInfoRepo.findContactInfosByAccountIdAndStatus(
                        accountDto.getId(),
                        filterContactInfo
                );
            }
            if (contactsInfo.isEmpty()) {
                throw new SystemException("empty_contact_info");
            }
            return contactsInfo.get().stream().map(ContactInfoMapper.CONTACT_INFO_MAPPER::toContactInfoDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

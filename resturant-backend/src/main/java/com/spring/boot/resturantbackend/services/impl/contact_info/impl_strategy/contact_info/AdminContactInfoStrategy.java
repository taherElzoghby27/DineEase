package com.spring.boot.resturantbackend.services.impl.contact_info.impl_strategy.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.mappers.contact_info.ContactInfoMapper;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.repositories.contact_info.ContactInfoRepo;
import com.spring.boot.resturantbackend.services.contact_info.strategies.ContactInfoRetrievalStrategy;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("adminServiceContactInfo")
public class AdminContactInfoStrategy implements ContactInfoRetrievalStrategy {
    @Autowired
    private ContactInfoRepo contactInfoRepo;

    @Cacheable(value = "contacts",key = "'admin'+(#filter==null?'ALL':#filter)")
    @Override
    public List<ContactInfoDto> getContactInfo(String filter) {
        try {
            Optional<List<ContactInfo>> contactsInfo;
            if (Objects.isNull(filter)) {
                contactsInfo = Optional.of(contactInfoRepo.findAll());
            } else {
                FilterContactInfo filterContactInfo = FilterContactInfo.valueOf(filter);
                contactsInfo = contactInfoRepo.findContactInfosByStatus(filterContactInfo);
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

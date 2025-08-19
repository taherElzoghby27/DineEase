package com.spring.boot.resturantbackend.services.impl.contact_info.impl_strategy.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.contact_info.ContactInfoMapper;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.repositories.contact_info.ContactInfoRepo;
import com.spring.boot.resturantbackend.services.contact_info.strategies.ContactInfoRetrievalStrategy;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("userServiceContactInfo")
@RequiredArgsConstructor
public class UserContactInfoStrategy implements ContactInfoRetrievalStrategy {
    private final ContactInfoRepo contactInfoRepo;

    @Override
    public List<ContactInfoDto> getContactInfo(String filter) {
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
            throw new NotFoundResourceException("empty_contact_info");
        }
        return contactsInfo.get().stream().map(ContactInfoMapper.CONTACT_INFO_MAPPER::toContactInfoDto).toList();
    }
}

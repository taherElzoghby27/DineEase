package com.spring.boot.resturantbackend.services.impl.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.mappers.contact_info.ContactInfoMapper;
import com.spring.boot.resturantbackend.mappers.security.AccountMapper;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.repositories.contact_info.ContactInfoRepo;
import com.spring.boot.resturantbackend.services.contact_info.ContactInfoService;
import com.spring.boot.resturantbackend.services.impl.contact_info.factories.ContactInfoStrategyFactory;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import com.spring.boot.resturantbackend.utils.enums.RoleEnum;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    @Autowired
    private ContactInfoRepo contactInfoRepo;
    @Autowired
    private ContactInfoStrategyFactory contactInfoStrategyFactory;
    @Autowired
    private AccountService accountService;


    @Override
    public List<ContactInfoDto> allContactInfos(String filter) {
        boolean isAdmin = SecurityUtils.hasRole(RoleEnum.ADMIN);
        RoleEnum role = isAdmin ? RoleEnum.ADMIN : RoleEnum.USER;
        return contactInfoStrategyFactory.getStrategy(role.toString()).getContactInfo(filter);
    }

    @Override
    public ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto) {
        try {
            Account account = getAccount();
            if (Objects.nonNull(contactInfoDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            ContactInfo contactInfo = ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfo(contactInfoDto);
            contactInfo.setAccount(account);
            contactInfo.setStatus(FilterContactInfo.NOT_REPLIED);
            contactInfo = contactInfoRepo.save(contactInfo);
            return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Account getAccount() {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        return AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
    }

    @CacheEvict(value = "contacts", allEntries = true)
    @Override
    public ContactInfoDto updateStatus(FilterContactInfo filterContactInfo, Long id) {
        try {
            ContactInfoDto contactInfoDto = getContactInfo(id);
            ContactInfo contactInfo = ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfo(contactInfoDto);
            Account account = getAccount(contactInfoDto);
            contactInfo.setAccount(account);
            contactInfo.setStatus(filterContactInfo);
            contactInfo = contactInfoRepo.save(contactInfo);
            return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Account getAccount(ContactInfoDto contactInfoDto) {
        AccountDto accountDto = accountService.getAccountById(contactInfoDto.getAccountId());
        return AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
    }

    @Cacheable(value = "contacts",key = "#result.id")
    @Override
    public ContactInfoDto getContactInfo(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<ContactInfo> contactInfo = contactInfoRepo.findById(id);
            if (contactInfo.isEmpty()) {
                throw new SystemException("empty_contact_info");
            }
            return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Cacheable(value = "contacts",key = "'id '+#id+' accId'+#accountId")
    @Override
    public ContactInfoDto getContactInfoByIdAndAccountId(Long id, Long accountId) {
        try {
            if (Objects.isNull(accountId)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<ContactInfo> contactInfo = contactInfoRepo.findByIdAndAccountId(id, accountId);
            if (contactInfo.isEmpty()) {
                throw new SystemException("empty_contact_info");
            }
            return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

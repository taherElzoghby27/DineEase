package com.spring.boot.resturantbackend.services.impl.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.exceptions.BadRequestException;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @CacheEvict(value = "contacts", allEntries = true)
    public ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto) {
        Account account = getAccount();
        if (Objects.nonNull(contactInfoDto.getId())) {
            throw new BadRequestException("id.must_be.null");
        }
        ContactInfo contactInfo = ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfo(contactInfoDto);
        contactInfo.setAccount(account);
        contactInfo.setStatus(FilterContactInfo.NOT_REPLIED);
        contactInfo = contactInfoRepo.save(contactInfo);
        return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo);
    }

    private static Account getAccount() {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        return AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
    }

    @CacheEvict(value = "contacts", allEntries = true)
    @Override
    @Transactional
    public ContactInfoDto updateStatus(FilterContactInfo filterContactInfo, Long id) {
        ContactInfoDto contactInfoDto = getContactInfo(id);
        ContactInfo contactInfo = ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfo(contactInfoDto);
        Account account = getAccount(contactInfoDto);
        contactInfo.setAccount(account);
        contactInfo.setStatus(filterContactInfo);
        contactInfo = contactInfoRepo.save(contactInfo);
        return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo);
    }

    private Account getAccount(ContactInfoDto contactInfoDto) {
        AccountDto accountDto = accountService.getAccountById(contactInfoDto.getAccountId());
        return AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
    }

    @Cacheable(value = "contacts", key = "#id")
    @Override
    @Transactional(readOnly = true)
    public ContactInfoDto getContactInfo(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("id.must_be.not_null");
        }
        Optional<ContactInfo> contactInfo = contactInfoRepo.findById(id);
        if (contactInfo.isEmpty()) {
            throw new NotFoundResourceException("empty_contact_info");
        }
        return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo.get());
    }

    @Cacheable(value = "contacts", key = "'id '+#id+' accId'+#accountId")
    @Override
    public ContactInfoDto getContactInfoByIdAndAccountId(Long id, Long accountId) {
        if (Objects.isNull(accountId)) {
            throw new BadRequestException("id.must_be.not_null");
        }
        Optional<ContactInfo> contactInfo = contactInfoRepo.findByIdAndAccountId(id, accountId);
        if (contactInfo.isEmpty()) {
            throw new NotFoundResourceException("empty_contact_info");
        }
        return ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfoDto(contactInfo.get());
    }
}

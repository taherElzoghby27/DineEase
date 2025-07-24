package com.spring.boot.resturantbackend.mappers.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.models.security.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactInfoMapper {
    ContactInfoMapper CONTACT_INFO_MAPPER = Mappers.getMapper(ContactInfoMapper.class);

    @Mapping(source = "account", target = "accountId", qualifiedByName = "accountToAccountId")
    ContactInfoDto toContactInfoDto(ContactInfo contactInfo);

    @Mapping(source = "accountId", target = "account", ignore = true)
    ContactInfo toContactInfo(ContactInfoDto contactInfoDto);

    @Named("accountToAccountId")
    default Long accountToAccountId(Account account) {
        return account == null ? null : account.getId();
    }
}

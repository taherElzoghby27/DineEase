package com.spring.boot.resturantbackend.mappers.security;

import com.spring.boot.resturantbackend.dto.security.AccountDetailsDto;
import com.spring.boot.resturantbackend.models.security.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AccountMapper.class})
public interface AccountDetailsMapper {
    AccountDetailsMapper ACCOUNT_DETAILS_MAPPER = Mappers.getMapper(AccountDetailsMapper.class);

    AccountDetails toAccountDetails(AccountDetailsDto accountDetailsDto);

    @Mapping(source = "account", target = "accountId", qualifiedByName = "accountToId")
    AccountDetailsDto toAccountDetailsDto(AccountDetails accountDetails);
}

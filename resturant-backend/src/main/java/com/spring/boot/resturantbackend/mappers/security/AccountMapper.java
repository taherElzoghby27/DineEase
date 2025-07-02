package com.spring.boot.resturantbackend.mappers.security;

import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthRequestVm;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthResponseVm;
import com.spring.boot.resturantbackend.vm.Security.UpdateProfileVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "accountDetails.account", target = "accountDetails.accountId", qualifiedByName = "accountToId")
    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);

    AccountDto toAccountDto(AccountAuthRequestVm accountAuthRequestVm);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesDtoToListString")
    AccountAuthResponseVm toAccountResponseVm(AccountDto accountDto);

    UpdateProfileVm toProfileResponseVm(AccountDto accountDto);
    @Mapping(source = "accountDetails.account", target = "accountDetails.accountId", qualifiedByName = "accountToId")
    UpdateProfileVm toProfileResponseVm(Account account);

    @Named("accountToId")
    default Long accountToId(Account account) {
        return account != null ? account.getId() : null;
    }

    @Named("rolesDtoToListString")
    default List<String> rolesDtoToListString(List<RoleDto> rolesDto) {
        return rolesDto != null ? rolesDto.stream().map(RoleDto::getRole).toList() : null;
    }
}

package com.spring.boot.resturantbackend.services.impl.security;

import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.exceptions.BadRequestException;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.security.AccountDetailsMapper;
import com.spring.boot.resturantbackend.mappers.security.RoleMapper;
import com.spring.boot.resturantbackend.mappers.security.AccountMapper;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.models.security.AccountDetails;
import com.spring.boot.resturantbackend.models.security.Role;
import com.spring.boot.resturantbackend.repositories.security.AccountRepo;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.services.security.RoleService;
import com.spring.boot.resturantbackend.utils.enums.RoleEnum;
import com.spring.boot.resturantbackend.vm.Security.ChangePasswordRequest;
import com.spring.boot.resturantbackend.vm.Security.UpdateProfileVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public List<AccountDto> getAccounts() {
        List<Account> users = accountRepo.findAll();
        if (users.isEmpty()) {
            throw new NotFoundResourceException("empty.accounts");
        }
        return users.stream().map(AccountMapper.ACCOUNT_MAPPER::toAccountDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        validateCreateAccount(accountDto);
        //enable account
        accountDto.setEnabled("1");
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
        //encode password
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        //
        account.setCreatedBy(account.getUsername());
        account.setUpdatedBy(account.getUsername());
        //make relation between user and role
        initRoleToUser(account);
        account = accountRepo.save(account);
        return AccountMapper.ACCOUNT_MAPPER.toAccountDto(account);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected void initRoleToUser(Account user) {
        Role role = RoleMapper.ROLE_MAPPER.toRole(roleService.findByRole(RoleEnum.USER.toString()));
        List<Role> roles = user.getRoles();
        if (Objects.isNull(roles)) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        user.setRoles(roles);
    }

    private void validateCreateAccount(AccountDto accountDto) {
        if (Objects.nonNull(accountDto.getId())) {
            throw new BadRequestException("id.must_be.null");
        }
        if (Objects.nonNull(getAccountByUsername(accountDto.getUsername()))) {
            throw new BadRequestException("account.exists");
        }
    }

    @Override
    @Transactional
    public UpdateProfileVm updateAccount(UpdateProfileVm updateProfileVm) {
        AccountDto accountDto = validateUpdateAccount(updateProfileVm.getId());
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
        AccountDetails newAccountDetails = AccountDetailsMapper.ACCOUNT_DETAILS_MAPPER.toAccountDetails(
                updateProfileVm.getAccountDetails()
        );
        AccountDetails oldAccountDetails = account.getAccountDetails();
        if (Objects.nonNull(oldAccountDetails)) {
            validateInputsForUpdating(updateProfileVm, oldAccountDetails);
            oldAccountDetails.setAccount(account);
            account.setAccountDetails(oldAccountDetails);
        } else {
            newAccountDetails.setAccount(account);
            account.setAccountDetails(newAccountDetails);
        }
        if (Objects.nonNull(updateProfileVm.getUsername())) {
            account.setUsername(updateProfileVm.getUsername());
        }
        account = accountRepo.save(account);
        return AccountMapper.ACCOUNT_MAPPER.toProfileResponseVm(account);
    }

    private static void validateInputsForUpdating(UpdateProfileVm updateProfileVm, AccountDetails oldAccountDetails) {
        if (Objects.nonNull(updateProfileVm.getAccountDetails().getPhoneNumber())) {
            oldAccountDetails.setPhoneNumber(updateProfileVm.getAccountDetails().getPhoneNumber());
        }
        if (Objects.nonNull(updateProfileVm.getAccountDetails().getAddress())) {
            oldAccountDetails.setAddress(updateProfileVm.getAccountDetails().getAddress());
        }
        if (Objects.nonNull(updateProfileVm.getAccountDetails().getAge())) {
            oldAccountDetails.setAge(updateProfileVm.getAccountDetails().getAge());
        }
    }

    private AccountDto validateUpdateAccount(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("id.must_be.not_null");
        }
        AccountDto accountDto = getAccountById(id);
        if (Objects.isNull(accountDto)) {
            throw new NotFoundResourceException("not_found.account");
        }
        return accountDto;
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        validateUpdateAccount(id);
        accountRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AccountDto getAccountById(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("id.must_be.not_null");
        }
        Optional<Account> result = accountRepo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("not_found.account");
        }
        return AccountMapper.ACCOUNT_MAPPER.toAccountDto(result.get());
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        if (username.isEmpty()) {
            throw new BadRequestException("not_empty.name");
        }
        Optional<Account> result = accountRepo.findByUsername(username);
        return result.map(AccountMapper.ACCOUNT_MAPPER::toAccountDto).orElse(null);
    }

    @Override
    public Account idToAccount(Long id) {
        return id != null ? AccountMapper.ACCOUNT_MAPPER.toAccount(getAccountById(id)) : null;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        //get account by username
        AccountDto accountDto = getAccountByUsername(changePasswordRequest.getUsername());
        Optional<Account> result = accountRepo.findById(accountDto.getId());
        if (result.isEmpty()) {
            throw new NotFoundResourceException("not_found.account");
        }
        Account account = result.get();
        //encode password
        String newHashPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        account.setPassword(newHashPassword);
        accountRepo.save(account);
    }
}

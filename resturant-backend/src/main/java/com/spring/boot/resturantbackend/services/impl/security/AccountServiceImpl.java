package com.spring.boot.resturantbackend.services.impl.security;

import com.spring.boot.resturantbackend.dto.security.AccountDto;
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
import jakarta.transaction.SystemException;
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
        try {
            List<Account> users = accountRepo.findAll();
            if (users.isEmpty()) {
                throw new SystemException("empty.accounts");
            }
            return users.stream().map(AccountMapper.ACCOUNT_MAPPER::toAccountDto).collect(Collectors.toList());
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        try {
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
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
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

    private void validateCreateAccount(AccountDto accountDto) throws SystemException {
        if (Objects.nonNull(accountDto.getId())) {
            throw new SystemException("id.must_be.null");
        }
        if (Objects.nonNull(getAccountByUsername(accountDto.getUsername()))) {
            throw new SystemException("account.exists");
        }
    }

    @Override
    @Transactional
    public UpdateProfileVm updateAccount(UpdateProfileVm updateProfileVm) {
        try {
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
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
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

    private AccountDto validateUpdateAccount(Long id) throws SystemException {
        if (Objects.isNull(id)) {
            throw new SystemException("id.must_be.not_null");
        }
        AccountDto accountDto = getAccountById(id);
        if (Objects.isNull(accountDto)) {
            throw new SystemException("not_found.account");
        }
        return accountDto;
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        try {
            validateUpdateAccount(id);
            accountRepo.deleteById(id);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AccountDto getAccountById(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<Account> result = accountRepo.findById(id);
            if (result.isEmpty()) {
                throw new SystemException("not_found.account");
            }
            return AccountMapper.ACCOUNT_MAPPER.toAccountDto(result.get());
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        try {
            if (username.isEmpty()) {
                throw new SystemException("not_empty.name");
            }
            Optional<Account> result = accountRepo.findByUsername(username);
            return result.map(AccountMapper.ACCOUNT_MAPPER::toAccountDto).orElse(null);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Account idToAccount(Long id) {
        return id != null ? AccountMapper.ACCOUNT_MAPPER.toAccount(getAccountById(id)) : null;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        try {
            //get account by username
            AccountDto accountDto = getAccountByUsername(changePasswordRequest.getUsername());
            Optional<Account> result = accountRepo.findById(accountDto.getId());
            if (result.isEmpty()) {
                throw new SystemException("not_found.account");
            }
            Account account = result.get();
            //encode password
            String newHashPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
            account.setPassword(newHashPassword);
            accountRepo.save(account);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

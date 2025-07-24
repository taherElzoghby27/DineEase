package com.spring.boot.resturantbackend.repositories.contact_info;

import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactInfoRepo extends JpaRepository<ContactInfo, Long> {
    Optional<List<ContactInfo>> findContactInfosByStatus(FilterContactInfo status);

    Optional<List<ContactInfo>> findContactInfosByAccountIdAndStatus(Long accountId, FilterContactInfo status);

    Optional<List<ContactInfo>> findContactInfosByAccountId(Long accountId);

    Optional<ContactInfo> findByIdAndAccountId(Long id, Long accountId);
}

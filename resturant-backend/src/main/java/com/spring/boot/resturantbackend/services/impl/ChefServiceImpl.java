package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.dto.ChefDto;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.ChefMapper;
import com.spring.boot.resturantbackend.models.Chef;
import com.spring.boot.resturantbackend.repositories.ChefRepo;
import com.spring.boot.resturantbackend.services.ChefService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChefServiceImpl implements ChefService {
    private final ChefRepo chefRepo;

    @Override
    public List<ChefDto> getAllChefs() {
        List<Chef> chefsList = chefRepo.findAllByOrderByIdAsc();
        if (chefsList.isEmpty()) {
            throw new NotFoundResourceException("chefs.not.found");
        }
        return chefsList.stream().map(ChefMapper.CHEF_MAPPER::toChefDto).toList();
    }
}

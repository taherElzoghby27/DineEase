package com.spring.boot.resturantbackend.mappers;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.mappers.security.AccountMapper;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AccountMapper.class})
public interface OrderMapper {
    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "account", target = "accountId", qualifiedByName = "accountToId")
    ResponseOrderVm toResponseOrderVm(Order order);

    @Mapping(source = "accountId", target = "account", ignore = true)
    @Mapping(source = "productsIds", target = "products", ignore = true)
    Order toOrder(RequestOrderVm requestOrderVm);

    @Mapping(source = "account", target = "accountId", qualifiedByName = "accountToId")
    OrderDto toOrderDto(Order order);
}

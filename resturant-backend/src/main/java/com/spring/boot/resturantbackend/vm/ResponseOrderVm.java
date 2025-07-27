package com.spring.boot.resturantbackend.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrderVm implements Serializable {
    private Long id;
    private Long accountId;
    private String code;
    private double totalPrice;
    private Long totalNumber;
    private String status;
}

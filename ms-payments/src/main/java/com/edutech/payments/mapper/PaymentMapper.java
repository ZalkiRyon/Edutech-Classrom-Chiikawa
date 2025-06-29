package com.edutech.payments.mapper;

import com.edutech.common.dto.PaymentDTO;
import com.edutech.payments.entity.Payment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDTO(Payment entity);
    Payment toEntity(PaymentDTO dto);
}

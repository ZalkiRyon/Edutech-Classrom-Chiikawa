package com.edutech.payments.mapper;

import com.edutech.common.dto.PaymentDTO;
import com.edutech.payments.entity.Payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    
    public PaymentDTO toDTO(Payment entity) {
        if (entity == null) {
            return null;
        }
        
        PaymentDTO dto = new PaymentDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setAmount(entity.getAmount());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentInstitution(entity.getPaymentInstitution());
        dto.setTransactionId(entity.getTransactionId());
        dto.setStatus(entity.getStatus());
        
        return dto;
    }
    
    public Payment toEntity(PaymentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Payment entity = new Payment();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setAmount(dto.getAmount());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPaymentInstitution(dto.getPaymentInstitution());
        entity.setTransactionId(dto.getTransactionId());
        entity.setStatus(dto.getStatus());
        
        return entity;
    }
}

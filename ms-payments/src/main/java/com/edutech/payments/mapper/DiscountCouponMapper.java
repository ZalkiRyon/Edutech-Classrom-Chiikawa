package com.edutech.payments.mapper;

import com.edutech.common.dto.DiscountCouponDTO;
import com.edutech.payments.entity.DiscountCoupon;

import org.springframework.stereotype.Component;

@Component
public class DiscountCouponMapper {
    
    public DiscountCouponDTO toDTO(DiscountCoupon entity) {
        if (entity == null) {
            return null;
        }
        
        DiscountCouponDTO dto = new DiscountCouponDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setDiscountPercentage(entity.getDiscountPercentage());
        dto.setValidFrom(entity.getValidFrom());
        dto.setValidUntil(entity.getValidUntil());
        dto.setIsActive(entity.getIsActive());
        
        return dto;
    }
    
    public DiscountCoupon toEntity(DiscountCouponDTO dto) {
        if (dto == null) {
            return null;
        }
        
        DiscountCoupon entity = new DiscountCoupon();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setDiscountPercentage(dto.getDiscountPercentage());
        entity.setValidFrom(dto.getValidFrom());
        entity.setValidUntil(dto.getValidUntil());
        entity.setIsActive(dto.getIsActive());
        
        return entity;
    }
}

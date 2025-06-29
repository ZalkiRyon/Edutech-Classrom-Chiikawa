package com.edutech.payments.mapper;

import com.edutech.common.dto.DiscountCouponDTO;
import com.edutech.payments.entity.DiscountCoupon;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountCouponMapper {
    DiscountCouponDTO toDTO(DiscountCoupon entity);
    DiscountCoupon toEntity(DiscountCouponDTO dto);
}

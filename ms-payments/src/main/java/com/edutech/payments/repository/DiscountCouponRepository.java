package com.edutech.payments.repository;

import com.edutech.payments.entity.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Integer> {
    Optional<DiscountCoupon> findByCode(String code);
    List<DiscountCoupon> findByIsActive(Boolean isActive);
    List<DiscountCoupon> findByValidFromLessThanEqualAndValidUntilGreaterThanEqual(LocalDate validFrom, LocalDate validUntil);
}

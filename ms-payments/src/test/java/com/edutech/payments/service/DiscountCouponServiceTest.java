package com.edutech.payments.service;

import com.edutech.common.dto.DiscountCouponDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.payments.entity.DiscountCoupon;
import com.edutech.payments.mapper.DiscountCouponMapper;
import com.edutech.payments.repository.DiscountCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountCouponServiceTest {

    @Mock
    private DiscountCouponRepository discountCouponRepository;

    @Mock
    private DiscountCouponMapper discountCouponMapper;

    @InjectMocks
    private DiscountCouponService discountCouponService;

    private DiscountCoupon discountCoupon;
    private DiscountCouponDTO discountCouponDTO;

    @BeforeEach
    void setUp() {
        discountCoupon = new DiscountCoupon();
        discountCoupon.setId(1);
        discountCoupon.setCode("DESCUENTO20");
        discountCoupon.setDescription("Descuento del 20% en todos los cursos");
        discountCoupon.setDiscountPercentage(new BigDecimal("20.00"));
        discountCoupon.setValidFrom(LocalDate.now().minusDays(5));
        discountCoupon.setValidUntil(LocalDate.now().plusDays(30));
        discountCoupon.setIsActive(true);

        discountCouponDTO = new DiscountCouponDTO();
        discountCouponDTO.setId(1);
        discountCouponDTO.setCode("DESCUENTO20");
        discountCouponDTO.setDescription("Descuento del 20% en todos los cursos");
        discountCouponDTO.setDiscountPercentage(new BigDecimal("20.00"));
        discountCouponDTO.setValidFrom(LocalDate.now().minusDays(5));
        discountCouponDTO.setValidUntil(LocalDate.now().plusDays(30));
        discountCouponDTO.setIsActive(true);
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<DiscountCoupon> coupons = Arrays.asList(discountCoupon);
        when(discountCouponRepository.findAll()).thenReturn(coupons);
        when(discountCouponMapper.toDTO(discountCoupon)).thenReturn(discountCouponDTO);

        // Act
        List<DiscountCouponDTO> result = discountCouponService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(discountCouponDTO.getId(), result.get(0).getId());
        assertEquals(discountCouponDTO.getCode(), result.get(0).getCode());
        verify(discountCouponRepository).findAll();
        verify(discountCouponMapper).toDTO(discountCoupon);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(discountCouponRepository.findById(1)).thenReturn(Optional.of(discountCoupon));
        when(discountCouponMapper.toDTO(discountCoupon)).thenReturn(discountCouponDTO);

        // Act
        DiscountCouponDTO result = discountCouponService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(discountCouponDTO.getId(), result.getId());
        assertEquals(discountCouponDTO.getCode(), result.getCode());
        assertEquals(discountCouponDTO.getDiscountPercentage(), result.getDiscountPercentage());
        verify(discountCouponRepository).findById(1);
        verify(discountCouponMapper).toDTO(discountCoupon);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(discountCouponRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> discountCouponService.findById(999));
        verify(discountCouponRepository).findById(999);
        verify(discountCouponMapper, never()).toDTO(any());
    }

    @Test
    void testFindByCode_Success() {
        // Arrange
        when(discountCouponRepository.findByCode("DESCUENTO20")).thenReturn(Optional.of(discountCoupon));
        when(discountCouponMapper.toDTO(discountCoupon)).thenReturn(discountCouponDTO);

        // Act
        DiscountCouponDTO result = discountCouponService.findByCode("DESCUENTO20");

        // Assert
        assertNotNull(result);
        assertEquals(discountCouponDTO.getCode(), result.getCode());
        assertEquals(discountCouponDTO.getDescription(), result.getDescription());
        verify(discountCouponRepository).findByCode("DESCUENTO20");
        verify(discountCouponMapper).toDTO(discountCoupon);
    }

    @Test
    void testFindByCode_NotFound() {
        // Arrange
        when(discountCouponRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> discountCouponService.findByCode("INVALID"));
        verify(discountCouponRepository).findByCode("INVALID");
        verify(discountCouponMapper, never()).toDTO(any());
    }

    @Test
    void testFindActiveForDate_Success() {
        // Arrange
        LocalDate testDate = LocalDate.now();
        List<DiscountCoupon> activeCoupons = Arrays.asList(discountCoupon);
        when(discountCouponRepository.findByValidFromLessThanEqualAndValidUntilGreaterThanEqual(testDate, testDate))
                .thenReturn(activeCoupons);
        when(discountCouponMapper.toDTO(discountCoupon)).thenReturn(discountCouponDTO);

        // Act
        List<DiscountCouponDTO> result = discountCouponService.findActiveForDate(testDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(discountCouponDTO.getId(), result.get(0).getId());
        assertTrue(result.get(0).getIsActive());
        verify(discountCouponRepository).findByValidFromLessThanEqualAndValidUntilGreaterThanEqual(testDate, testDate);
        verify(discountCouponMapper).toDTO(discountCoupon);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(discountCouponMapper.toEntity(discountCouponDTO)).thenReturn(discountCoupon);
        when(discountCouponRepository.save(any(DiscountCoupon.class))).thenReturn(discountCoupon);
        when(discountCouponMapper.toDTO(discountCoupon)).thenReturn(discountCouponDTO);

        // Act
        DiscountCouponDTO result = discountCouponService.create(discountCouponDTO);

        // Assert
        assertNotNull(result);
        assertEquals(discountCouponDTO.getId(), result.getId());
        assertEquals(discountCouponDTO.getCode(), result.getCode());
        verify(discountCouponRepository).save(any(DiscountCoupon.class));
        verify(discountCouponMapper).toEntity(discountCouponDTO);
        verify(discountCouponMapper).toDTO(discountCoupon);
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        when(discountCouponRepository.findById(1)).thenReturn(Optional.of(discountCoupon));
        when(discountCouponMapper.toEntity(discountCouponDTO)).thenReturn(discountCoupon);
        when(discountCouponRepository.save(any(DiscountCoupon.class))).thenReturn(discountCoupon);
        when(discountCouponMapper.toDTO(discountCoupon)).thenReturn(discountCouponDTO);

        // Act
        DiscountCouponDTO result = discountCouponService.update(1, discountCouponDTO);

        // Assert
        assertNotNull(result);
        assertEquals(discountCouponDTO.getId(), result.getId());
        assertEquals(discountCouponDTO.getCode(), result.getCode());
        verify(discountCouponRepository).findById(1);
        verify(discountCouponRepository).save(any(DiscountCoupon.class));
        verify(discountCouponMapper).toEntity(discountCouponDTO);
        verify(discountCouponMapper).toDTO(discountCoupon);
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        when(discountCouponRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> discountCouponService.update(999, discountCouponDTO));
        verify(discountCouponRepository).findById(999);
        verify(discountCouponRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(discountCouponRepository.findById(1)).thenReturn(Optional.of(discountCoupon));

        // Act
        discountCouponService.delete(1);

        // Assert
        verify(discountCouponRepository).findById(1);
        verify(discountCouponRepository).delete(discountCoupon);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(discountCouponRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> discountCouponService.delete(999));
        verify(discountCouponRepository).findById(999);
        verify(discountCouponRepository, never()).delete(any());
    }
}

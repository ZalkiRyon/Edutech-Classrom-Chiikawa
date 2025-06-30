package com.edutech.payments.controller;

import com.edutech.common.dto.DiscountCouponDTO;
import com.edutech.payments.service.DiscountCouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiscountCouponController.class)
class DiscountCouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountCouponService discountCouponService;

    private ObjectMapper objectMapper;
    private DiscountCouponDTO discountCouponDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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
    void testFindAll() throws Exception {
        // Arrange
        List<DiscountCouponDTO> coupons = Arrays.asList(discountCouponDTO);
        when(discountCouponService.findAll()).thenReturn(coupons);

        // Act & Assert
        mockMvc.perform(get("/api/discount-coupons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.discountCouponDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.discountCouponDTOList[0].code").value("DESCUENTO20"))
                .andExpect(jsonPath("$._embedded.discountCouponDTOList[0].discountPercentage").value(20.00))
                .andExpect(jsonPath("$._embedded.discountCouponDTOList[0].isActive").value(true))
                .andExpect(jsonPath("$._embedded.discountCouponDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(discountCouponService).findAll();
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        when(discountCouponService.findById(1)).thenReturn(discountCouponDTO);

        // Act & Assert
        mockMvc.perform(get("/api/discount-coupons/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("DESCUENTO20"))
                .andExpect(jsonPath("$.discountPercentage").value(20.00))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-coupons.href").exists());

        verify(discountCouponService).findById(1);
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        when(discountCouponService.create(any(DiscountCouponDTO.class))).thenReturn(discountCouponDTO);

        // Act & Assert
        mockMvc.perform(post("/api/discount-coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discountCouponDTO)))
                .andExpect(status().isOk()) // El controlador devuelve 200, no 201
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("DESCUENTO20"))
                .andExpect(jsonPath("$.discountPercentage").value(20.00))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-coupons.href").exists());

        verify(discountCouponService).create(any(DiscountCouponDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        doNothing().when(discountCouponService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/discount-coupons/1"))
                .andExpect(status().isNoContent());

        verify(discountCouponService).delete(1);
    }
}


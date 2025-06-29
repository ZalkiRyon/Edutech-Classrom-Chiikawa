package com.edutech.payments.service;

import com.edutech.common.dto.PaymentDTO;
import com.edutech.payments.client.UserClient;
import com.edutech.payments.entity.Payment;
import com.edutech.payments.mapper.PaymentMapper;
import com.edutech.payments.repository.PaymentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;
import static com.edutech.common.exception.ExceptionUtils.orThrowFeign;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserClient userClient;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, UserClient userClient) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userClient = userClient;
    }

    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll().stream().map(paymentMapper::toDTO).toList();
    }

    public PaymentDTO findById(Integer id) {
        return paymentMapper.toDTO(orThrow(paymentRepository.findById(id), "Pago"));
    }

    public List<PaymentDTO> findByUserId(Integer userId) {
        return paymentRepository.findByUserId(userId).stream().map(paymentMapper::toDTO).toList();
    }

    public PaymentDTO create(PaymentDTO dto) {
        // Validar que el usuario exista
        orThrowFeign(dto.getUserId(), userClient::findById, "Usuario");

        // Crear nuevo pago
        return saveDTO(dto, null);
    }

    public PaymentDTO update(Integer id, PaymentDTO dto) {
        orThrow(paymentRepository.findById(id), "Pago");
        
        // Validar que el usuario exista
        orThrowFeign(dto.getUserId(), userClient::findById, "Usuario");
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        paymentRepository.delete(orThrow(paymentRepository.findById(id), "Pago"));
    }

    private PaymentDTO saveDTO(PaymentDTO dto, Integer id) {
        Payment entity = paymentMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return paymentMapper.toDTO(paymentRepository.save(entity));
    }
}

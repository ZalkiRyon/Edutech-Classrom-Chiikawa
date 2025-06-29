package com.edutech.support.service;

import com.edutech.common.dto.SupportTicketDTO;
import com.edutech.support.client.UserClient;
import com.edutech.support.entity.SupportTicket;
import com.edutech.support.mapper.SupportTicketMapper;
import com.edutech.support.repository.SupportTicketRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;
import static com.edutech.common.exception.ExceptionUtils.orThrowFeign;

@Service
public class SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final SupportTicketMapper supportTicketMapper;
    private final UserClient userClient;

    public SupportTicketService(SupportTicketRepository supportTicketRepository, 
                               SupportTicketMapper supportTicketMapper, UserClient userClient) {
        this.supportTicketRepository = supportTicketRepository;
        this.supportTicketMapper = supportTicketMapper;
        this.userClient = userClient;
    }

    public List<SupportTicketDTO> findAll() {
        return supportTicketRepository.findAll().stream().map(supportTicketMapper::toDTO).toList();
    }

    public SupportTicketDTO findById(Integer id) {
        return supportTicketMapper.toDTO(orThrow(supportTicketRepository.findById(id), "Ticket de soporte"));
    }

    public List<SupportTicketDTO> findByUserId(Integer userId) {
        return supportTicketRepository.findByUserId(userId).stream().map(supportTicketMapper::toDTO).toList();
    }

    public List<SupportTicketDTO> findBySupportUserId(Integer supportUserId) {
        return supportTicketRepository.findBySupportUserId(supportUserId).stream().map(supportTicketMapper::toDTO).toList();
    }

    public SupportTicketDTO create(SupportTicketDTO dto) {
        // Validar que el usuario que reporta exista
        orThrowFeign(dto.getUserId(), userClient::findById, "Usuario");

        // Si hay un usuario de soporte asignado, validar que exista
        if (dto.getSupportUserId() != null) {
            orThrowFeign(dto.getSupportUserId(), userClient::findById, "Usuario de soporte");
        }

        // Crear nuevo ticket
        return saveDTO(dto, null);
    }

    public SupportTicketDTO update(Integer id, SupportTicketDTO dto) {
        orThrow(supportTicketRepository.findById(id), "Ticket de soporte");
        
        // Validar que el usuario que reporta exista
        orThrowFeign(dto.getUserId(), userClient::findById, "Usuario");

        // Si hay un usuario de soporte asignado, validar que exista
        if (dto.getSupportUserId() != null) {
            orThrowFeign(dto.getSupportUserId(), userClient::findById, "Usuario de soporte");
        }
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        supportTicketRepository.delete(orThrow(supportTicketRepository.findById(id), "Ticket de soporte"));
    }

    private SupportTicketDTO saveDTO(SupportTicketDTO dto, Integer id) {
        SupportTicket entity = supportTicketMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return supportTicketMapper.toDTO(supportTicketRepository.save(entity));
    }
}

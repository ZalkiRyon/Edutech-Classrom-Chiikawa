package com.edutech.support.mapper;

import com.edutech.common.dto.SupportTicketDTO;
import com.edutech.support.entity.SupportTicket;

import org.springframework.stereotype.Component;

@Component
public class SupportTicketMapper {
    
    public SupportTicketDTO toDTO(SupportTicket entity) {
        if (entity == null) {
            return null;
        }
        
        SupportTicketDTO dto = new SupportTicketDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setSupportUserId(entity.getSupportUserId());
        dto.setSubject(entity.getSubject());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setClosedAt(entity.getClosedAt());
        
        return dto;
    }
    
    public SupportTicket toEntity(SupportTicketDTO dto) {
        if (dto == null) {
            return null;
        }
        
        SupportTicket entity = new SupportTicket();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setSupportUserId(dto.getSupportUserId());
        entity.setSubject(dto.getSubject());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setClosedAt(dto.getClosedAt());
        
        return entity;
    }
}

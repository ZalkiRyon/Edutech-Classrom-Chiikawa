package com.edutech.support.mapper;

import com.edutech.common.dto.SupportTicketDTO;
import com.edutech.support.entity.SupportTicket;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupportTicketMapper {
    SupportTicketDTO toDTO(SupportTicket entity);
    SupportTicket toEntity(SupportTicketDTO dto);
}

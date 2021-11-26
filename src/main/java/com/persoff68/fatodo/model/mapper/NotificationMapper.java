package com.persoff68.fatodo.model.mapper;

import com.persoff68.fatodo.model.Notification;
import com.persoff68.fatodo.model.dto.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    Notification dtoToPojo(NotificationDTO dto);

}

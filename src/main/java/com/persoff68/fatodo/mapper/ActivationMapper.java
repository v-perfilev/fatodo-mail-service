package com.persoff68.fatodo.mapper;

import com.persoff68.fatodo.model.Activation;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActivationMapper {

    Activation dtoToPojo(ActivationDTO dto);

}

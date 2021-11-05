package com.persoff68.fatodo.model.mapper;

import com.persoff68.fatodo.model.ResetPassword;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResetPasswordMapper {

    ResetPassword dtoToPojo(ResetPasswordDTO dto);

}

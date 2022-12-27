package com.persoff68.fatodo.mapper;

import com.persoff68.fatodo.model.Feedback;
import com.persoff68.fatodo.model.dto.FeedbackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackMapper {

    Feedback dtoToPojo(FeedbackDTO dto);

}

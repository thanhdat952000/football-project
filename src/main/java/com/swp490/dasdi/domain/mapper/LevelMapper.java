package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.LevelResponse;
import com.swp490.dasdi.infrastructure.entity.Level;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelMapper {

    LevelResponse toLevelResponse(Level level);
}

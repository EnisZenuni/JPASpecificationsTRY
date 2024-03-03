package com.example.movietask.api.mapper;

import com.example.movietask.api.dto.RatingDTO;
import com.example.movietask.domain.model.Rating;

public interface RatingMapper {
    RatingDTO toDTO(Rating rating);
}

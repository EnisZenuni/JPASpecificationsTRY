package com.example.movietask.api.mapper;

import com.example.movietask.api.dto.RatingDTO;
import com.example.movietask.domain.model.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapperImpl implements RatingMapper {
    @Override
    public RatingDTO toDTO(Rating rating) {
        return RatingDTO.builder()
                .description(rating.getDescription())
                .rating(rating.getRating())
                .build();
    }
}

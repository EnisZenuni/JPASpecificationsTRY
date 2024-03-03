package com.example.movietask.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RatingDTO {
    Integer rating;
    String description;
}

package com.example.demo.dto.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseGetDto {

    @JsonProperty
    String message;
}

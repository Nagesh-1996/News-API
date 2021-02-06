package com.robosoftin.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author nagesh
 * inside the package - com.robosoftin.evaluation.constants
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericServerResponse {

    private String code;
    private String message;
    @JsonProperty("data")
    private Object data;
}

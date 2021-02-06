package com.robosoftin.evaluation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author nagesh
 * inside the package - com.robosoftin.evaluation.constants
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultInfo {

  private String code;
  private String message;
}

package com.robosoftin.evaluation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.dto.response
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageListResponseDto {

	private String languageName;
	private String languageCode;

}

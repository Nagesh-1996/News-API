package com.robosoftin.evaluation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.dto.response
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsSourcesDataResponseDto {

	private List<NewsSourcesResponseDto> sources = null;
}

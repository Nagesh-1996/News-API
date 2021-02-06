package com.robosoftin.evaluation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robosoftin.evaluation.dto.response.service.Articles;
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
public class ArticlesDataResponseDto {

	private String source;
	private String sortBy;
	private List<Articles> articles = null;
}

package com.robosoftin.evaluation.dto.response.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.dto.response.service
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticlesData {

	@JsonProperty("status")
	private String status;
	@JsonProperty("source")
	private String source;
	@JsonProperty("sortBy")
	private String sortBy;
	@JsonProperty("articles")
	private List<Articles> articles = null;

}

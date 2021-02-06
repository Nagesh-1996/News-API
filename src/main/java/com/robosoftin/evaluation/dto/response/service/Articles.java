package com.robosoftin.evaluation.dto.response.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Articles {

	@JsonProperty("author")
	private String author;
	@JsonProperty("title")
	private String title;
	@JsonProperty("description")
	private String description;
	@JsonProperty("url")
	private String url;
	@JsonProperty("urlToImage")
	private String urlToImage;
	@JsonProperty("publishedAt")
	private String publishedAt;
}

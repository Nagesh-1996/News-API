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
public class NewsSources {

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("url")
	private String url;
	@JsonProperty("category")
	private String category;
	@JsonProperty("language")
	private String language;
	@JsonProperty("country")
	private String country;
	@JsonProperty("urlsToLogos")
	private UrlsForNewsSourceLogos urlsToLogos;
	@JsonProperty("sortBysAvailable")
	private List<String> sortBysAvailable = null;

}

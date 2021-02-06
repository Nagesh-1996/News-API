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
public class NewsSourcesResponseDto {

	private String id;
	private String name;
	private String description;
	private String url;
	private String category;
	private String language;
	private String country;
	private UrlsForNewsSourceLogosResponseDto urlsToLogos;
	private List<String> sortBysAvailable = null;
}

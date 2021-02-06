package com.robosoftin.evaluation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.dto.response
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesResponseDto {

	private String author;
	private String title;
	private String description;
	private String url;
	private String urlToImage;
	private String publishedAt;
}
